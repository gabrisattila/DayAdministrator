package Classes.ModifyWorkBooks;

import Classes.I18N.NoSuchCellException;
import Classes.OwnFileTypes.Excel;
import Classes.Parser.Measures;
import Classes.Parser.Money;
import Classes.Parser.Time;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.*;
import static Classes.OwnFileTypes.Excel.openExcel;

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
	}


	private void collectExcels() throws IOException {
		excelFiles = new ArrayList<>();

		if (notNull(getDay().getMeasures())){
			excelFiles.add(openExcel(MeasureExcelFileName));
		}
		if (notNull(getDay().getMoney())){
			excelFiles.add(openExcel(MoneyExcelFileName));
		}
		if (notNull(getDay().getTime())){
			excelFiles.add(openExcel(TimeExcelFileName));
		}
	}

	//region Modify
	
	public void modifyAndSave() throws NoSuchCellException, IOException {
//		placeMeasuresIfTheresAny(getDay().getMeasures());
//		placeMoneyIfTheresAny(getDay().getMoney());
		placeTimeIfTheresAny(getDay().getTime());
		safeSaveExcels();
	}

	public void safeSaveExcels() throws IOException {
		for (Excel excel : excelFiles){
			Desktop.getDesktop().open(new File(excel.getPath()));
		}
	}

	private void placeMeasuresIfTheresAny(Measures measures) throws IOException, NoSuchCellException {
		if (notNull(measures)) {
			Excel excel = getExcel(MeasureExcelFileName);
			new ModifyMeasures(measures, excel);
			excel.setDayRowsStyle();
			excel.save();
		}
	}

	private void placeMoneyIfTheresAny(Money money) throws IOException, NoSuchCellException {
		if (notNull(money)) {
			Excel excel = getExcel(MoneyExcelFileName);
			new ModifyMoney(money, excel);
			excel.setDayRowsStyle();
			excel.save();
		}
	}

	private void placeTimeIfTheresAny(Time time) throws IOException, NoSuchCellException {
		if (notNull(time)) {
			Excel excel = getExcel(TimeExcelFileName);
			new ModifyTime(time, excel);
			excel.setDayRowsStyle();
			excel.save();
		}
	}
	
	//Helpers
	private Excel getExcel(String fileName) throws FileNotFoundException {
		for (Excel excel : excelFiles){
			if (fileName.equals(getFileNameFromPath(excel.getPath())))
				return excel;
		}
		throw new FileNotFoundException("Nem szerepel ez az elnevezésű excel a most módosítandók között.");
	}

	//endregion

}
