package Classes.OwnFileTypes;

import Classes.I18N.FailedSearch;
import Classes.I18N.NoSuchCellException;
import lombok.Getter;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.*;
import static java.util.Objects.checkIndex;
import static java.util.Objects.isNull;

@Getter
public class Excel extends XSSFWorkbook {

	private String path;

	private final List<String> sheetNames = new ArrayList<>();

	private final Map<String, List<String>> titlesPerSheets = new HashMap<>();

	public Excel(InputStream excelFile) throws IOException {
		super(excelFile);
		exploreFile();
	}

	public String toString(){
		return path;
	}

	public static Excel openExcel(String path) throws IOException {
		path = dataExcelsPath + path;
		Excel excel = new Excel(new FileInputStream(path));
		excel.path = path;
		return excel;
	}

	public void save(){
		try {
			FileOutputStream out = new FileOutputStream(path);
			this.write(out);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
			Cell cell;
			rowTitles = new ArrayList<>();
			for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				if (isNull(cell))
					cell = row.createCell(i);
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

	public void setDayRowsStyleToPrevious() throws NoSuchCellException {
		LocalDate prev = getDay().dateOfDay.minusDays(1);
		for (Sheet sheet : this){
			setRowStyle(getRowByDateOnASheet(getDay().dateOfDay, sheet));
		}
	}

	private void setRowStyle(Row current) {
		boolean isNapCell;
		for (int i = 0; i < rowLength(current); i++) {
			isNapCell = "Nap".equals(getTitleOfACell(current.getCell(i)));
			if (isNull(current.getCell(i)) && !isNapCell)
					current.createCell(i);
			if (!isNapCell) {
				CellStyle cellStyle = current.getCell(i).getCellStyle();
				cellStyle.setWrapText(true);
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				current.setRowStyle(cellStyle);
			}
		}
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
		} catch (RuntimeException ex){
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
		if (notNull(cell)) {
			List<String> titleListsOfSheet = titlesPerSheets.get(cell.getSheet().getSheetName());
			return cell.getColumnIndex() == titleListsOfSheet.size() ?
					titleListsOfSheet.get(cell.getColumnIndex() - 1) :
					titleListsOfSheet.get(cell.getColumnIndex());
		}
		return "";
	}

	public Cell getCellByTitle(String title) throws NoSuchCellException {
		for (Sheet sheet : this){
			if (titlesPerSheets.get(sheet.getSheetName()).stream()
					.anyMatch(s -> textContainsString(s, title))){
				Row todayRow = getTodayRowOnASheet(sheet);
				for (int i = 0; i < todayRow.getLastCellNum(); i++) {
					if (isNull(todayRow.getCell(i)))
						todayRow.createCell(i);
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
		for (int i = 0; i < fromRow.getLastCellNum(); i++) {
			if (isNull(fromRow.getCell(i)))
				fromRow.createCell(i);
			if (textContainsString(getTitleOfACell(fromRow.getCell(i)), title)){
				return fromRow.getCell(i);
			}
		}
		return null;
	}


	public static int rowLength(Row row){
		return row.getLastCellNum() - row.getFirstCellNum();
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
