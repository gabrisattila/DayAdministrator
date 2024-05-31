package Classes.Parser;

import Classes.I18N.NoSuchCellException;
import lombok.Data;

import java.io.IOException;
import java.util.Comparator;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.delimiterBetweenTextParts;
import static Classes.I18N.I18N.isDate;
import static Classes.Parser.Measures.*;

@Data
public class Parser {

    //region Fields

    private String text;

    private String measures = "";

    private String money = "";

    private String time = "";

    private Comparator<String> textPartsComparator;

    private MeasureParser measureParser;

    private MoneyParser moneyParser;

    private TimeParser timeParser;

    //endregion


    //region Constructor

    public Parser(String dayText) throws IOException {
        text = dayText;
//        defineComparator();
        sortPartsAndSetUpMeasureMoneyTimeVars();
    }

    //endregion


    //region Methods

    private void defineComparator(){
        textPartsComparator = (s1, s2) -> {
            if (isTime(s1) && !isTime(s2)) {
                return 1;
            } else if (isTime(s2) && !isTime(s1)) {
                return -1;
            } else {
                if (isMeasure(s1) && !isMeasure(s2)){
                    return -1;
                }
                if (isMeasure(s2) && !isMeasure(s1)){
                    return 1;
                }
                return s1.compareTo(s2);
            }
        };
    }

    private void sortPartsAndSetUpMeasureMoneyTimeVars(){
        String[] parts = splitToParts();
        StringBuilder ms = new StringBuilder(), mo = new StringBuilder(), t = new StringBuilder();
        for (String part : parts){
            part = part.trim();
            if (part.isBlank())
                continue;
            if (isDate(part)) {
                getDay(part);
            }else if (isMeasure(part)) {
                ms.append(part).append(";");
            }else if (isMoney(part)) {
                mo.append(part).append(";");
            }else if (isTime(part)) {
                t.append(part).append(";");
            }
        }
        measures = ms.toString();
        money = mo.toString();
        time = t.toString();
    }

    private String[] splitToParts(){
        return text.split(delimiterBetweenTextParts);
    }

    //Sub Parsing

    public void parseWithSubParsers() throws IOException, NoSuchCellException {
        if (!measures.isBlank()) {
            measureParser = new MeasureParser(this);
            measureParser.parse();
        }

        if (!money.isBlank()) {
            moneyParser = new MoneyParser(this);
            moneyParser.parse();
        }

        if (!time.isBlank()) {
            timeParser = new TimeParser(this);
            timeParser.parse();
        }
    }

    //endregion
}
