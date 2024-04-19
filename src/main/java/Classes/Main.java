package Classes;

import Classes.I18N.NoSuchCellException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

import static Classes.Front.AdministrateDay;
import static Classes.I18N.I18N.MeasureExcelFileName;
import static Classes.I18N.I18N.dataExcelsPath;

public class Main {

	public static void main(String[] args) throws IOException, NoSuchCellException, InvalidFormatException {
        AdministrateDay();
    }
}