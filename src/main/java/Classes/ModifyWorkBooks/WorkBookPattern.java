package Classes.ModifyWorkBooks;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Classes.I18N.*;
import static Classes.ModifyWorkBooks.ThreeMainType.*;

@Getter
@Setter
public class WorkBookPattern {

	private XSSFWorkbook excelFile;
	
	private List<String> sheetNames = new ArrayList<>();


	/**
	 * When listing out the titles per sheet the next thing is important:
	 * Egyesített cellák esetén az x cellából az elsőbe kerül a szöveg, a második egy üres stringet ad vissza.
	 */
	private List<List<String>> titleListPerSheet = new ArrayList<>();

	private ThreeMainType type;

	public WorkBookPattern(){}

	public WorkBookPattern(String path) throws IOException {
		excelFile = new XSSFWorkbook(path);
		type = calcType(path);
		exploreFile();
	}

	private ThreeMainType calcType(String path) {
		return 
				getFileNameFromPath(path).equals(TimeExcelFileName) ? Time : 
				getFileNameFromPath(path).equals(MoneyExcelFileName) ? Money : 
				Measures;
	}

	private void exploreFile(){
		iterateTroughSheets();
		collectTitlesPerSheet();
	}

	private void iterateTroughSheets(){
		for (int i = 0; i < excelFile.getNumberOfSheets(); i++) {
			sheetNames.add(excelFile.getSheetName(i));
		}
	}
	
	private void collectTitlesPerSheet(){
		Sheet sheet;
		List<String> rowTitles;
		for (int i = 0; i < excelFile.getNumberOfSheets(); i++) {
			sheet = excelFile.getSheet(sheetNames.get(i));
			rowTitles = new ArrayList<>();
			Row row = sheet.getRow(0);
			for (Cell cell : row){
				rowTitles.add(cell.getStringCellValue());
			}
			titleListPerSheet.add(rowTitles);
		}
	}

}
