package Classes.OwnFileTypes;

import junit.framework.TestCase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static Classes.I18N.I18N.*;
import static java.util.Objects.isNull;
import static org.junit.Assert.assertThrows;

public class ExcelTest extends TestCase {

    public void testExcelOpeningAndWriting() throws IOException, InvalidFormatException {
        Excel excel = openFile(dataExcelsPath + MeasureExcelFileName);
        placeValuesInCellsProba(excel);
        saveExcel(excel);
    }

    public Excel openFile(String path) throws IOException, InvalidFormatException {
        return new Excel(new File(path));
    }

    private void placeValuesInCellsProba(Excel excel){
        Sheet sheet = excel.getSheetAt(0);
        Row row;
        Cell cell;
        for (int i = 0; i < 10; i++) {
            row = sheet.getRow(i);
            if (isNull(row))
                sheet.createRow(i);
            cell = sheet.getRow(i).getCell(i);
            if (isNull(cell))
                sheet.getRow(i).createCell(i);
            sheet.getRow(i).getCell(i).setCellValue(i);
        }


    }

    private void saveExcel(Excel excel){
        try {
            FileOutputStream out = new FileOutputStream(excel.getPath());
            excel.write(out);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}