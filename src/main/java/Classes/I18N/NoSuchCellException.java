package Classes.I18N;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NoSuchCellException extends AdministratorException{

	public NoSuchCellException(Sheet sheet, String cellValue){
		super(sheet, cellValue);
	}

}
