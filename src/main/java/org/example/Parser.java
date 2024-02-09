package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.example.I18N.swap;

public class Parser {

    //region Fields

    private String text;

    private String measures;

    private String money;

    private String time;

    //endregion


    //region Constructor

    public Parser(String dayText) {
    }

    //endregion


    //region Methods

    private void parseTextIntoM_M_T() {
        String[] parts = text.split("/");

        parts = makeMeasuresMoneyTimeOrderFromParts(parts);

        if (parts.length == 1){ // Only time
            setUpTime();
        } else if (parts.length == 2) { // Time and -> Money or Measures
            setUpMoney();
            setUpMeasures();
        }else { // All three
            setUpTime();
            setUpMoney();
            setUpMeasures();
        }
    }

    private String[] makeMeasuresMoneyTimeOrderFromParts(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            if (isMeasure(parts[i])){
                if (i == 2)
	                swap(parts, 2, 0);
                else if (i == 1)
                    swap(parts, 1, 0);
            } else if (isMoney(parts[i])) {
                if (i == 0)
                    swap(parts, 0, 1);
                else if (i == 2)
                    swap(parts, 2, 1);
            }else {
                if (i == 0)
                    swap(parts, 0, 2);
                else if (i == 1)
            }
        }
        return parts;
    }

    private void setUpMeasures() {
    }

    private void setUpMoney() {
    }

    private void setUpTime() {
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

    //endregion
}
