package Classes.ModifyWorkBooks.OwnFileTypes;

import Classes.I18N.FailedSearch;
import Classes.I18N.NoSuchCellException;
import lombok.Getter;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.*;

@Getter
public class Excel extends XSSFWorkbook {

	private final String path;

	private final List<String> sheetNames = getSheetNames();

	private static Map<String, List<String>> titlesPerSheets;

	public Excel(String path) throws IOException {
		super(path);
		this.path = path;
		exploreFile();
	}


	//region Set Up

	private void exploreFile(){
		collectTitlesPerSheets();
	}

	private void collectTitlesPerSheets() {
		List<String> rowTitles;
		Sheet sheet;
		String currentSheetName;
		for (int i = 0; i < getNumberOfSheets(); i++) {
			currentSheetName = sheetNames.get(i);
			sheet = getSheet(currentSheetName);
			rowTitles = new ArrayList<>();
			Row row = sheet.getRow(0);
			for (Cell cell : row){
				rowTitles.add(cell.getStringCellValue());
			}
			titlesPerSheets.put(currentSheetName, rowTitles);
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
		return cell.getLocalDateTimeCellValue().toLocalDate()
				==
				date;
	}

	public static List<Cell> getColumn(Sheet sheet, int index){
		List<Cell> cells = new ArrayList<>();
		for (Row row : sheet){
			cells.add(row.getCell(index));
		}
		return cells;
	}

	public static String getTitleOfACell(Cell cell){
		return titlesPerSheets.get(cell.getSheet().getSheetName()).get(cell.getColumnIndex());
	}

	public Cell getCellByTitle(String title) throws NoSuchCellException {
		for (Sheet sheet : this){
			if (titlesPerSheets.get(sheet.getSheetName()).stream()
					.anyMatch(s -> KMPSearch(title, s))){
				Row todayRow = getTodayRowOnASheet(sheet);
				for (Cell cell : todayRow){
					if (KMPSearch(title, getTitleOfACell(cell))){
						return cell;
					}
				}
			}
		}
		throw new NoSuchCellException();
	}
}
