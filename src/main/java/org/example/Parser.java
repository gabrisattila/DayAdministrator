package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import static org.example.I18N.delimiterBetweenTextParts;
import static org.example.I18N.swap;

public class Parser {

    //region Fields

    private String text;

    private String measures;

    private String money;

    private String time;

    private Comparator<String> textPartsComparator;

    //endregion


    //region Constructor

    public Parser(String dayText) {
        text = dayText;
        defineComparator();
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

    private boolean isMeasure(String part){

        //TODO Szexi mintaillesztés algoritmussal.

        String[] ms = part.split(" ");
        for (String m : ms){
            if ("cigi".equals(m) || "Cigi".equals(m) || "CIGI".equals(m) || "c".equals(m) || "C".equals(m) ||
                "cig".equals(m) || "ci".equals(m) || "Cig".equals(m) || "Ci".equals(m)){
                return true;
            }
        }
        return false;
    }

    private boolean isMoney(String part){
        return !isTime(part) && !isMeasure(part);
    }

    private boolean isTime(String part){
        return part.contains("-");
    }

    private void sortPartsAndSetUpMeasureMoneyTimeVars(){
        String[] parts = splitToParts();
        Arrays.sort(parts, textPartsComparator);

        for (String part : parts){
            if (isMeasure(part))
                measures = part;
            if (isMoney(part))
                money = part;
            if (isTime(part))
                time = part;
        }
    }

    private String[] splitToParts(){
        return text.split(delimiterBetweenTextParts);
    }

    //endregion
}
