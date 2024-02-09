package org.example;

import lombok.Getter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

@Getter
public class Editor {

    //region Fields

    private final String dayText;

    private final Parser textParser;

    private final ArrayList<String> modifiableWorkbookNames;

    private final ArrayList<XSSFWorkbook> workbooksToBeChanged;

    private final PatternRecognizer patternRecognizer;

    //endregion

    //region Constructor

    public Editor(String dayText){
        this.dayText = dayText;
        textParser = new Parser(dayText);
        modifiableWorkbookNames = new ArrayList<>();
        workbooksToBeChanged = new ArrayList<>();
        patternRecognizer = new PatternRecognizer();
    }

    //endregion

    //region Methods

    public void modifyWorkbooks() {

    }

    private ArrayList<String> collectWorkbookPaths(){
        ArrayList<String> paths = new ArrayList<>();



        return paths;
    }

    //endregion
}
