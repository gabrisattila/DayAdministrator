package Classes.OwnFileTypes;

import junit.framework.TestCase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static Classes.I18N.I18N.*;
import static Classes.OwnFileTypes.Excel.openExcel;
import static java.util.Objects.isNull;
import static org.junit.Assert.assertThrows;

public class ExcelTest extends TestCase {

    public void testExcelOpeningAndWriting() throws IOException {
        Excel excel = openExcel(dataExcelsPath + MeasureExcelFileName);
        placeValuesInCellsProba(excel);
        excel.save();
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
}