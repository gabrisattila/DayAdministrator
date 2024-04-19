package Classes;

import Classes.I18N.NoSuchCellException;
import Classes.ModifyWorkBooks.ExcelModifier;
import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import lombok.Getter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Classes.Parser.Parser;

import java.io.IOException;
import java.util.ArrayList;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.notNull;

@Getter
public class Editor {

    //region Fields

    private final String dayText;

    private Parser textParser;

    private ArrayList<String> modifiableWorkbookNames;

    private ArrayList<XSSFWorkbook> workbooksToBeChanged;

    private ExcelModifier excelModifier;

    //endregion

    //region Constructor

    public Editor(String dayText) {
        this.dayText = dayText;
        setUpVars();
        createDay();
    }

    //endregion

    //region Methods

    public void modify() throws IOException, NoSuchCellException, InvalidFormatException {
        excelModifier = new ExcelModifier();
        excelModifier.modify();
    }

    private void setUpVars(){
        textParser = new Parser(dayText);
        modifiableWorkbookNames = new ArrayList<>();
        workbooksToBeChanged = new ArrayList<>();
    }

    private void createDay(){
        getDay(textParser.getDateOfToday());
        if (notNull(textParser.getMeasureParser()))
            getDay().setMeasures(textParser.getMeasureParser().getMeasures());
        if (notNull(textParser.getMoneyParser()))
            getDay().setMoney(textParser.getMoneyParser().getMoney());
        if (notNull(textParser.getTimeParser()))
            getDay().setTime(textParser.getTimeParser().getTime());
    }

    //endregion
}
