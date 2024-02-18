package Classes;

import lombok.Getter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Classes.Parser.Parser;

import java.io.IOException;
import java.util.ArrayList;

import static Classes.Day.getDay;
import static java.util.Objects.isNull;
import static Classes.I18N.*;

@Getter
public class Editor {

    //region Fields

    private final String dayText;

    private Parser textParser;

    private ArrayList<String> modifiableWorkbookNames;

    private ArrayList<XSSFWorkbook> workbooksToBeChanged;

    private PatternRecognizer patternRecognizer;

    //endregion

    //region Constructor

    public Editor(String dayText) throws IOException {
        this.dayText = dayText;
        setUpVars();
        createDay();
        collectWorkbooksToChange();
    }

    //endregion

    //region Methods

    public void modifyWorkbooks() {

    }

    private void setUpVars(){
        textParser = new Parser(dayText);
        modifiableWorkbookNames = new ArrayList<>();
        workbooksToBeChanged = new ArrayList<>();
        patternRecognizer = new PatternRecognizer();
    }

    private void createDay(){
        getDay().setMeasures(textParser.getMeasureParser().getMeasures());
        getDay().setMoney(textParser.getMoneyParser().getMoney());
//        getDay().setTimeLine(textParser.getTimeParser().);
    }

    private void collectWorkbooksToChange() throws IOException {
        String[] properWorkbookNames = new String[MAX_NUMBER_OF_WORKBOOKS];

        if (!isNull(textParser.getMeasureParser()))
            properWorkbookNames[0] = dataExcelsPath + MeasureExcelFileName;

        if (!isNull(textParser.getMoneyParser()))
            properWorkbookNames[1] = dataExcelsPath + MoneyExcelFileName;

        if (!isNull(textParser.getTimeParser()))
            properWorkbookNames[2] = dataExcelsPath + TimeExcelFileName;

        for (String workbookName : properWorkbookNames){
            if (!workbookName.isBlank()){
                workbooksToBeChanged.add(new XSSFWorkbook(workbookName));
            }
        }
    }

    //endregion
}
