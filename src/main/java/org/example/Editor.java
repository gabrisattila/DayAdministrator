package org.example;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Editor {

    private final String dayText;

    private final Parser textParser;

    private ArrayList<XSSFWorkbook> workbooksToBeChanged;

    private PatternRecognizer patternRecognizer;

    public Editor(String dayText){
        this.dayText = dayText;
        textParser = new Parser(dayText);
        workbooksToBeChanged = new ArrayList<>();
        patternRecognizer = new PatternRecognizer();
    }
}
