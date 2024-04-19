package Classes.ModifyWorkBooks;

import Classes.I18N.NoSuchCellException;
import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import Classes.Parser.Measures;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;

@Getter
@Setter
public class ModifyMeasures {

	private Measures measures;

	private Excel excel;

	public ModifyMeasures(Measures measures, Excel excel) throws NoSuchCellException {
		this.measures = measures;
		this.excel = excel;
		modifyAllInMeasures();
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
