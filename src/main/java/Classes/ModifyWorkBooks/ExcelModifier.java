package Classes.ModifyWorkBooks;

import Classes.I18N.NoSuchCellException;
import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import Classes.Parser.Measures;
import Classes.Parser.Money;
import Classes.Parser.Time;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.*;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.*;

@Getter
@Setter
public class ExcelModifier {

	private ArrayList<Excel> excelFiles;
	
	private Map<Excel, List<String>> sheetNames = new HashMap<>();

	/**
	 * When listing out the titles per sheet the next thing is important:
	 * Egyesített cellák esetén az x cellából az elsőbe kerül a szöveg, a második egy üres stringet ad vissza.
	 */
	private Map<String, List<String>> titleListPerSheet = new HashMap<>();


	public ExcelModifier() throws IOException {
		collectExcels();
		exploreFile();
	}

	//region Set Up
	
	private void collectExcels() throws IOException {
		excelFiles = new ArrayList<>();

		if (notNull(getDay().getMeasures())){
			excelFiles.add(new Excel(dataExcelsPath + MeasureExcelFileName));
		}
		if (notNull(getDay().getMoney())){
			excelFiles.add(new Excel(dataExcelsPath + MoneyExcelFileName));
		}
		if (notNull(getDay().getTime())){
			excelFiles.add(new Excel(dataExcelsPath + TimeExcelFileName));
		}
	}

	private void exploreFile(){
		iterateTroughSheets();
		collectTitlesPerSheet();
	}

	private void iterateTroughSheets(){
		for (Excel excelFile : excelFiles){
			for (int i = 0; i < excelFile.getNumberOfSheets(); i++) {
				if (notNull(sheetNames.get(excelFile))) {
					sheetNames.get(excelFile).add(excelFile.getSheetName(i));
				} else {
					int finalI = i;
					sheetNames.put(excelFile, new ArrayList<>(){{add(excelFile.getSheetName(finalI));}});
				}
			}
		}
	}
	
	private void collectTitlesPerSheet(){
		Sheet sheet;
		List<String> rowTitles;
		String currentSheetName;
		for (Excel excelFile : excelFiles){
			for (int i = 0; i < excelFile.getNumberOfSheets(); i++) {
				currentSheetName = sheetNames.get(excelFile).get(i);
				sheet = excelFile.getSheet(currentSheetName);
				rowTitles = new ArrayList<>();
				Row row = sheet.getRow(0);
				for (Cell cell : row){
					rowTitles.add(cell.getStringCellValue());
				}
				titleListPerSheet.put(currentSheetName, rowTitles);
			}
		}
	}
	
	//endregion

	//region Modify
	
	public void modify() {
		placeValuesInCells();
	}

	private void placeValuesInCells(){
		placeMeasuresIfTheresAny(getDay().getMeasures());
		placeMoneyIfTheresAny(getDay().getMoney());
		placeTimeIfTheresAny(getDay().getTime());
	}

	private void placeMeasuresIfTheresAny(Measures measures) {
		if (notNull(measures)){
			
		}
	}

	private void placeMoneyIfTheresAny(Money money) {
		if (notNull(money)){
			
		}
	}

	private void placeTimeIfTheresAny(Time time) {
		if (notNull(time)){
			
		}
	}
	
	//Helpers
	public static Row getWorkingRowOnASheet(Sheet sheet) throws NoSuchCellException {
		int dayColIndex = 0;
		for (Cell cell : sheet.getRow(0)){
			if ("Nap".equals(cell.toString())){
				dayColIndex = cell.getColumnIndex();
				break;
			}
		}
		for (Cell cell : getColumn(sheet, dayColIndex)){
//			if (cell.getDateCellValue() == getDay().dateOfDay){
//			//TODO Megírni az excel.java-ba a következőket:
			//TODO getColumn(), getColumnByTitle, getTitleOfACell, getCellByTitle
			//TODO Conversation cell.getDateCellValue() == getDay().dateOfDay Date = LocalDate
//			}
			if (cell.toString().equals(getDay().dateOfDay.toString())){
				return sheet.getRow(cell.getRowIndex());
			}
		}
		throw new NoSuchCellException(sheet, getDay().dateOfDay.toString());
	}

	public static List<Cell> getColumn(Sheet sheet, int index){
		List<Cell> cells = new ArrayList<>();
		for (Row row : sheet){
			cells.add(row.getCell(index));
		}
		return cells;
	}

	private String getTitleOfACell(Cell cell){
		return titleListPerSheet.get(cell.getSheet().getSheetName()).get(cell.getColumnIndex());
	}

	//endregion

}
