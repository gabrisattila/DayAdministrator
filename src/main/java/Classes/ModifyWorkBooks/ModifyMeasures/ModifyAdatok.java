package Classes.ModifyWorkBooks.ModifyMeasures;

import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import Classes.Parser.Measures;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;

import static Classes.I18N.I18N.MeasureExcelFileName;

@Getter
@Setter
public class ModifyAdatok {

	private Measures measures;

	private Excel excel;

	private Sheet sheet;

	public ModifyAdatok(Measures measures) throws IOException {
		this.measures = measures;
		excel = new Excel("C:\\Users\\asus\\Desktop\\Minden\\Adat\\");
		sheet = excel.getSheet(MeasureExcelFileName);
	}

	public void modifyAll(){
		modifyKávé();
		modifySúly();
		modifyCigi();
		modifyJO();
	}

	private void modifyKávé() {
		Cell cell = sheet.getCellByTitle("Kávé");
		cell.setCellValue(measures.kávé());
	}

	private void modifySúly() {
		Cell cell = sheet.getCellByTitle("Súly");
		cell.setCellValue(measures.súly());
	}

	private void modifyCigi() {
		Cell cell = sheet.getCellByTitle("Telefon idő");
		cell.setCellValue(measures.telefonIdő());
	}

	private void modifyJO() {
		Cell cell = sheet.getCellByTitle("Cigi");
		cell.setCellValue(measures.súly());
	}

}
