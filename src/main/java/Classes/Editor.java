package Classes;

import Classes.I18N.NoSuchCellException;
import Classes.ModifyWorkBooks.ExcelModifier;
import lombok.Getter;
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

    /**
     * @param dayText The text which will be parsed to a day. It contains all the information
     * This constructor parse the text with time, measure and money parser to actual, usable variables.
     * After it creates the day object which stores all that information.
     */
    public Editor(String dayText) throws IOException, NoSuchCellException {
        this.dayText = dayText;
        setUpVars();
        createDay();
    }

    //endregion

    //region Methods

    public void modifyAndSaveAfterModification() throws IOException, NoSuchCellException {
        excelModifier = new ExcelModifier();
        excelModifier.modifyAndSave();
    }

    private void setUpVars() throws IOException, NoSuchCellException {
        textParser = new Parser(dayText);
        textParser.parseWithSubParsers();
    }

    private void createDay(){
        if (notNull(textParser.getMeasureParser()))
            getDay().setMeasures(textParser.getMeasureParser().getMeasures());
        if (notNull(textParser.getMoneyParser()))
            getDay().setMoney(textParser.getMoneyParser().getMoney());
        if (notNull(textParser.getTimeParser()))
            getDay().setTime(textParser.getTimeParser().getTime());
    }

    //endregion
}
