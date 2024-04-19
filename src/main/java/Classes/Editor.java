package Classes;

import Classes.I18N.NoSuchCellException;
import Classes.ModifyWorkBooks.ExcelModifier;
import lombok.Getter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Classes.Parser.Parser;

import java.io.IOException;
import java.util.ArrayList;

import static Classes.Day.getDay;

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

    /*
    * Day Text exp.:
    * 8-9 reggel/85,8/910-10 tanulás/9 cigi/4500 Ft kp Étel/
    *
    *
    * */
    public Editor(String dayText) {
        this.dayText = dayText;
        setUpVars();
        createDay();
    }

    //endregion

    //region Methods

    public void modifyWorkbooks() throws IOException, NoSuchCellException {
        excelModifier = new ExcelModifier();
        excelModifier.modify();
    }

    private void setUpVars(){
        textParser = new Parser(dayText);
        modifiableWorkbookNames = new ArrayList<>();
        workbooksToBeChanged = new ArrayList<>();
    }

    private void createDay(){
        getDay().setMeasures(textParser.getMeasureParser().getMeasures());
        getDay().setMoney(textParser.getMoneyParser().getMoney());
        getDay().setTime(textParser.getTimeParser().getTime());
    }

    //endregion
}
