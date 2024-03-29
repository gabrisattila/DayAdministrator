package Classes.ModifyWorkBooks;

import Classes.I18N.NoSuchCellException;
import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import Classes.Parser.Measures;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;

import static Classes.I18N.I18N.MeasureExcelFileName;
import static Classes.I18N.I18N.dataExcelsPath;

@Getter
@Setter
public class ModifyMeasures {

	private Measures measures;

	private Excel excel;

	public ModifyMeasures(Measures measures) throws IOException {
		this.measures = measures;
		excel = new Excel(dataExcelsPath + MeasureExcelFileName);
	}

	public void modifyAllInMeasures() throws NoSuchCellException {
		modifyKávé();
		modifySúly();
		modifyCigi();
		modifyJO();
	}

	private void modifyKávé() throws NoSuchCellException {
		Cell cell = excel.getCellByTitle("Kávé");
		cell.setCellValue(measures.kávé());
	}

	private void modifySúly() throws NoSuchCellException {
		Cell cell = excel.getCellByTitle("Súly");
		cell.setCellValue(measures.súly());
	}

	private void modifyCigi() throws NoSuchCellException {
		Cell cell = excel.getCellByTitle("Telefon idő");
		cell.setCellValue(measures.telefonIdő());
	}

	private void modifyJO() throws NoSuchCellException {
		Cell cell = excel.getCellByTitle("Cigi");
		cell.setCellValue(measures.súly());
	}

}
