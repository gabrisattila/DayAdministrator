package Classes.ModifyWorkBooks;

import Classes.I18N.NoSuchExcelException;
import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import jdk.jshell.spi.ExecutionControl;
import lombok.Getter;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

@Getter
public abstract class ParentDataSorter {

	private final Object measureOrMoneyOrTime;

	private final ExcelModifier modifier;

	public ParentDataSorter(Object measureOrMoneyOrTime, ExcelModifier modifier){
		this.measureOrMoneyOrTime = measureOrMoneyOrTime;
		this.modifier = modifier;
	};

	abstract Excel getExcel() throws IllegalArgumentException, NoSuchExcelException;

	abstract Sheet getSheet(Object insert) throws NoSuchExcelException, ExecutionControl.NotImplementedException;

	abstract Column getColOfInsertion(Object insert);

	abstract Row getRowOfInsertion(Object insert);

	abstract Cell getCellOfInsertion(Object insert) throws NoSuchExcelException, ExecutionControl.NotImplementedException;

}
