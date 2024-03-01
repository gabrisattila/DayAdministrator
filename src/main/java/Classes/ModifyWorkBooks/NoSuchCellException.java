package Classes.ModifyWorkBooks;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NoSuchCellException extends RuntimeException{

	public NoSuchCellException(Sheet sheet, String searchedCellStringValue){
		System.err.println(
				"A keresett cella (" + searchedCellStringValue + ") nem található a " +
				sheet.getSheetName() + " táblán."
		);
	}

}
