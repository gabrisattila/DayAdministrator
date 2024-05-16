package Classes.ModifyWorkBooks;

import Classes.Editor;
import Classes.I18N.NoSuchCellException;
import Classes.Parser.Slot;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.ActionTerms.actionType.Értékes;

public class ModifyTimeTest extends TestCase {

    String dayText;

    public void testSeparatedTimePartsOnÉrtékes() throws IOException, NoSuchCellException {
        dayText =
                "2024.04.19.; 5:03;\n" +
//				"610-710 reggel; 86,5 kg; 1 kávé;\n" +
                "710-810 ut. - 30 olv.; 810-1205 meló;\n" +
//				"1205-25 ebéd; 1 kávé; 1235-16 meló;\n"
				"16-17 ut. - 25 olv. - 10 olv.;\n" +
//				"17-1730 vacsi; 1 kávé; 18-1930 DayAdmin; 20-2130 Videó;\n" +
//				"2130-23 írás; 23-2330 saláta és ebéd elrakni; 2330-25 fekvés;\n" +
				"10 cigi;\n";

        Editor editor = new Editor(dayText);
        ModifyTime modifier = new ModifyTime(getDay().getTime());
        modifier.collectSlots(true);

        Map<String, List<Slot>> activities = modifier.sortSlotsByActionType(modifier.getÉrtékes(), Értékes);

        Map<String, String> activitiesInSpearatedTimeStrings = modifier.makeSeparatedTimeParts(activities);

        System.out.println(activitiesInSpearatedTimeStrings);
    }

}