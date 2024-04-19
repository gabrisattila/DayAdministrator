package Classes.ModifyWorkBooks.OwnFileTypes;

import Classes.I18N.FailedSearch;
import Classes.I18N.NoSuchCellException;
import Classes.Parser.Slot;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.*;

@Getter
public class Excel extends XSSFWorkbook {

	private final String path;

	private final List<String> sheetNames = new ArrayList<>();

	private final Map<String, List<String>> titlesPerSheets = new HashMap<>();

	public Excel(File excelFile) throws IOException, InvalidFormatException {
		super(excelFile);
		this.path = excelFile.getPath();
		exploreFile();
	}

	public String toString(){
		return path;
	}

	//region Set Up

	private void exploreFile(){
		collectSheetNames();
		collectTitlesPerSheets();
	}

	private void collectSheetNames(){
		for (int i = 0; i < getNumberOfSheets(); i++) {
			sheetNames.add(getSheetName(i));
		}
	}

	private void collectTitlesPerSheets() {
		List<String> rowTitles;
		for (Sheet sheet : this){
			Row row = sheet.getRow(0);
			rowTitles = new ArrayList<>();
			for (Cell cell : row){
				rowTitles.add(cell.getStringCellValue());
			}
			titlesPerSheets.put(sheet.getSheetName(), rowTitles);
		}
	}

	//endregion

	public static Row getTodayRowOnASheet(Sheet sheet) throws NoSuchCellException {
		return getRowByDateOnASheet(getDay().dateOfDay, sheet);
	}

	public static Row getRowByDateOnASheet(LocalDate date, Sheet sheet) throws NoSuchCellException {
		int dayColIndex = 0;
		for (Cell cell : sheet.getRow(0)){
			if ("Nap".equals(cell.toString())){
				dayColIndex = cell.getColumnIndex();
				break;
			}
		}
		for (Cell cell : getColumn(sheet, dayColIndex)){
			if (containsDate(cell, date)){
				return sheet.getRow(cell.getRowIndex());
			}
		}
		throw new NoSuchCellException(sheet, date.toString());

	}

	public List<Cell> getColumnByTitle(String _title) throws FailedSearch {
		for (String sheetName : titlesPerSheets.keySet()){
			for (String title : titlesPerSheets.get(sheetName)){
				if (title.equals(_title)){
					return getColumn(getSheet(sheetName), titlesPerSheets.get(sheetName).indexOf(title));
				}
			}
		}
		throw new FailedSearch(_title);
	}

	public static boolean containsTodayDate(Cell cell){
		return cell.getLocalDateTimeCellValue().toLocalDate()
				==
				getDay().dateOfDay;
	}

	public static boolean containsDate(Cell cell, LocalDate date){
		try {
			return cell.getLocalDateTimeCellValue().toLocalDate().equals(date);
		}catch (IllegalStateException e){
			return false;
		}
	}

	public static List<Cell> getColumn(Sheet sheet, int index){
		List<Cell> cells = new ArrayList<>();
		for (Row row : sheet){
			cells.add(row.getCell(index));
		}
		return cells;
	}

	public String getTitleOfACell(Cell cell){
		if (notNull(cell))
			return titlesPerSheets.get(cell.getSheet().getSheetName()).get(cell.getColumnIndex());
		return "";
	}

	public Cell getCellByTitle(String title) throws NoSuchCellException {
		for (Sheet sheet : this){
			if (titlesPerSheets.get(sheet.getSheetName()).stream()
					.anyMatch(s -> textContainsString(s, title))){
				Row todayRow = getTodayRowOnASheet(sheet);
				for (int i = 0; i < todayRow.getLastCellNum(); i++) {
					//TODO Cigi esetének tökéletesítése
					if (!"Cigi".equals(title) && textContainsString(getTitleOfACell(todayRow.getCell(i)), title) ||
							("Cigi".equals(title) && getTitleOfACell(todayRow.getCell(i)).equals(title))){
						return todayRow.getCell(i);
					}
				}
			}
		}
		throw new NoSuchCellException();
	}

	public Cell getCellFromRowByTitle(String title, Row fromRow){
		for (Cell cell : fromRow){
			if (title.equals(getTitleOfACell(cell))){
				return cell;
			}
		}
		return null;
	}


	public static int rowLength(Row row){
		return row.getLastCellNum() - row.getLastCellNum();
	}

	public static void writeActionToACell(Cell whereMi, String action){
		if (notNull(whereMi)) {
			if (whereMi.getStringCellValue().isEmpty()) {
				writeToCell(whereMi, action);
			} else if (!textContainsString(whereMi.getStringCellValue(), action)) {
				String cellValue = whereMi.getStringCellValue();
				cellValue += ", " + action;
				writeToCell(whereMi, cellValue);
			}
		}
	}

	private static void writeToCell(Cell where, String what){
		where.setCellValue(what);
	}

}
