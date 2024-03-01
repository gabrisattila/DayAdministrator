package Classes.ModifyWorkBooks;

import Classes.Parser.Measures;
import Classes.Parser.Money;
import Classes.Parser.Slot;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.*;

import static Classes.Day.getDay;
import static Classes.I18N.*;

@Getter
@Setter
public class WorkBookModifier {

	private ArrayList<XSSFWorkbook> excelFiles;
	
	private List<String> sheetNames = new ArrayList<>();

	/**
	 * When listing out the titles per sheet the next thing is important:
	 * Egyesített cellák esetén az x cellából az elsőbe kerül a szöveg, a második egy üres stringet ad vissza.
	 */
	private Map<String, List<String>> titleListPerSheet = new HashMap<>();


	public WorkBookModifier() throws IOException {
		collectExcels();
		exploreFile();
	}

	private void collectExcels() throws IOException {
		excelFiles = new ArrayList<>();

		if (notNull(getDay().getMeasures())){
			excelFiles.add(new XSSFWorkbook(dataExcelsPath + MeasureExcelFileName));
		}
		if (notNull(getDay().getMoney())){
			excelFiles.add(new XSSFWorkbook(dataExcelsPath + MoneyExcelFileName));
		}
		if (notNull(getDay().getTimeLine())){
			excelFiles.add(new XSSFWorkbook(dataExcelsPath + TimeExcelFileName));
		}

	}

	private void exploreFile(){
		iterateTroughSheets();
		collectTitlesPerSheet();
	}

	private void iterateTroughSheets(){
		for (int i = 0; i < excelFiles.getNumberOfSheets(); i++) {
			sheetNames.add(excelFiles.getSheetName(i));
		}
	}
	
	private void collectTitlesPerSheet(){
		Sheet sheet;
		List<String> rowTitles;
		for (int i = 0; i < excelFiles.getNumberOfSheets(); i++) {
			sheet = excelFiles.getSheet(sheetNames.get(i));
			rowTitles = new ArrayList<>();
			Row row = sheet.getRow(0);
			for (Cell cell : row){
				rowTitles.add(cell.getStringCellValue());
			}
			titleListPerSheet.put(sheetNames.get(i), rowTitles);
		}
	}

	private int getWorkingRowNumOnASheet(Sheet sheet){
		Cell firstCellInRow;
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			firstCellInRow = sheet.getRow(i).getCell(0);
			if (firstCellInRow.getLocalDateTimeCellValue().toLocalDate() == getDay().dateOfDay){
				return i;
			}
		}
		throw new NoSuchCellException(sheet, getDay().dateOfDay.toString());
	}

	private String getTitleOfACell(Cell cell){
		return titleListPerSheet.get(cell.getSheet().getSheetName()).get(cell.getColumnIndex());
	}

	private void placeValuesInCells(){
		placeMeasuresIfTheresAny(getDay().getMeasures());
		placeMoneyIfTheresAny(getDay().getMoney());
		placeTimeIfTheresAny(getDay().getTimeLine());
	}

	private void placeMeasuresIfTheresAny(Measures measures) {

	}

	private void placeMoneyIfTheresAny(Money money) {
		if (MoneyExcelFileName.equals(getFileNameFromPath(path))){

		}
	}

	private void placeTimeIfTheresAny(LinkedList<Slot> timeLine) {
		if (TimeExcelFileName.equals(getFileNameFromPath(path))){

		}
	}

	public void modify() {

	}
}
