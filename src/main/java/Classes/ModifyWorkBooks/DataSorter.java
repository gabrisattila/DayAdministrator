package Classes.ModifyWorkBooks;

import Classes.I18N.NoSuchExcelException;
import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import Classes.Parser.Measures;
import Classes.Parser.Money;
import Classes.Parser.Time;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

import static Classes.I18N.I18N.*;

public class DataSorter extends ParentDataSorter {

	public DataSorter(Object o, ExcelModifier modifier){
		super(o, modifier);
	}
	
	@Override
	protected Excel getExcel() throws NoSuchExcelException {
		for (Excel e : getModifier().getExcelFiles()){
			if (getMeasureOrMoneyOrTime() instanceof Measures &&
				e.getPath().contains(MeasureExcelFileName))
				return e;
			if (getMeasureOrMoneyOrTime() instanceof Money &&
				e.getPath().contains(MoneyExcelFileName))
				return e;
			if (getMeasureOrMoneyOrTime() instanceof Time &&
				e.getPath().contains(TimeExcelFileName))
				return e;
		}
		throw new NoSuchExcelException(getMeasureOrMoneyOrTime());
	}

	@Override
	Sheet getSheet(Object insert) throws NoSuchExcelException {
		List<Sheet> sheets = new ArrayList<>();
		Excel excel = getExcel();
		return null;
	}

	@Override
	Column getColOfInsertion(Object insert) {
		return null;
	}

	@Override
	Row getRowOfInsertion(Object insert) {
		return null;
	}

	@Override
	Cell getCellOfInsertion(Object insert) throws NoSuchExcelException {
		Sheet sheetOfInsertion = getSheet(insert);
		return null;
	}


}
