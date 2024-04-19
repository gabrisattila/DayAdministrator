package Classes.ModifyWorkBooks;

import Classes.I18N.NoSuchCellException;
import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import Classes.Parser.Measures;
import Classes.Parser.Money;
import Classes.Parser.Time;
import lombok.Getter;
import lombok.Setter;
import java.io.FileNotFoundException;
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
	}


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

	//region Modify
	
	public void modify() throws NoSuchCellException, IOException {
		placeValuesInCells();
	}

	private void placeValuesInCells() throws NoSuchCellException, IOException {
		placeMeasuresIfTheresAny(getDay().getMeasures());
		placeMoneyIfTheresAny(getDay().getMoney());
		placeTimeIfTheresAny(getDay().getTime());
	}

	private void placeMeasuresIfTheresAny(Measures measures) throws FileNotFoundException, NoSuchCellException {
		if (notNull(measures))
			new ModifyMeasures(measures, getExcel(MeasureExcelFileName));
	}

	private void placeMoneyIfTheresAny(Money money) throws FileNotFoundException {
		if (notNull(money))
			new ModifyMoney(money, getExcel(MoneyExcelFileName));
	}

	private void placeTimeIfTheresAny(Time time) throws IOException, NoSuchCellException {
		if (notNull(time))
			new ModifyTime(time, getExcel(TimeExcelFileName));
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
