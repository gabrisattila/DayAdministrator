package Classes.Parser;

import Classes.I18N.I18N.ActionTerms;
import Classes.I18N.NoSuchCellException;
import Classes.OwnFileTypes.Excel;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Objects;

import static Classes.Day.getDay;
import static Classes.I18N.AskTheUserForInformation.getStringAnswer;
import static Classes.I18N.I18N.*;
import static Classes.I18N.I18N.ActionTerms.actionGroup.getActionGroup;
import static Classes.I18N.I18N.ActionTerms.actionType.getActionType;
import static Classes.I18N.I18N.notNull;
import static Classes.OwnFileTypes.Excel.*;
import static java.util.Objects.isNull;

@Getter
@Setter
public class Action {

    private ActionTerms.actionType type;

    private ActionTerms.actionGroup group;

    private String action;

    public Action(String action) {
        group = getActionGroup(action);
        type = getActionType(action);
        this.action = action;
    }

    public Action(String action, ActionTerms.actionType type) {
        this.type = type;
        group = getActionGroup(action);
        this.action = action;
    }

    public Action(String action, ActionTerms.actionGroup group, ActionTerms.actionType type){
        this.type = type;
        this.group = group;
        this.action = action;
    }

    public static Action createAction(String action, Object... objects) throws IOException, NoSuchCellException {
        String[] actionParts = action.split(" ");
        ActionTerms.actionGroup group = getActionGroup(actionParts[0]);
        ActionTerms.actionType type = null;
        StringBuilder actualAction = new StringBuilder();
        if (notNull(group)){
            type = getActionType(group);
            if (actionParts.length > 1){
                for (int i = 1; i < actionParts.length; i++) {
                    actualAction.append(" ").append(actionParts[i]);
                }
            }else {
                actualAction.append(
                        whatWasTheActualAction(
                                group,
                                objects[0],
                                objects.length > 1 ? objects[1] : null)
                );
            }
        }else{
            if (actionParts.length > 1){
                group = getActionGroup(actionParts[0] + " " + actionParts[1]);
                if (actionParts.length > 2 && notNull(group)){
                    actualAction = new StringBuilder(Objects.requireNonNull(
                            whatWasTheActualAction(
                                    group,
                                    objects[0],
                                    objects.length > 1 ? objects[1] : null)
                    ));
                }
            }
            else
            {
                group = tryToFindEarlier(action, openExcel(TimeExcelFileName));
                if (isNull(group))
                    group = whatWasTheActionGroupOfThat(action);
                type = getActionType(group);
                actualAction = new StringBuilder().append(action);
            }
        }
        return new Action(actualAction.toString(), group, type);
    }

    public String toString(){
        return (notNull(group) ? group.toString() : "?") + " -> " + action;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Action))
            return false;

        return ((Action) obj).getAction().equals(action);
    }

    public boolean isBlank(){
        return action.isBlank() && isNull(type) && isNull(group);
    }

    public static Comparator<Action> ActionComparator(){
        return Comparator
                .comparing(Action::getType)
                .thenComparing(Action::getGroup)
                .thenComparing(Action::getAction);
    }

    public int compareTo(Action action) {
        return ActionComparator().compare(this, action);
    }

    public static String whatWasTheActualAction(ActionTerms.actionGroup actionGroup, Object... objects) throws IOException {
        if (objects[0] instanceof Integer){
            return whatWasTheActualActionUnder((Integer) objects[0], actionGroup);
        } else if (objects[0] instanceof LocalTime) {
            return whatWasTheActualActionBetween((LocalTime) objects[0], (LocalTime) objects[1], actionGroup);
        }
        return null;
    }

    public static String whatWasTheActualActionUnder(int amount, ActionTerms.actionGroup actionGroup) throws IOException {
        System.out.println("\n" + amount + " percnyi " +
            actionGroup + "-t végeztél.\nEgész konkrétan mit?");
        return getStringAnswer();
    }

    public static String whatWasTheActualActionBetween(LocalTime from,
                                                LocalTime to,
                                                ActionTerms.actionGroup actionGroup) throws IOException {
        System.out.println("\nEközött ("+ from +") és ("+ to +") eközött az időpont között\n" +
                            actionGroup + "-t végeztél.\nEgész konkrétan mit?");
	    return getStringAnswer();
    }

    public static ActionTerms.actionGroup whatWasTheActionGroupOfThat(String action) throws IOException {
        System.out.println("\nEz a tevékenység ("+ action +") milyen tevékenység csoportba tartozik?");
        String answer = getStringAnswer();
        return getActionGroup(answer);
    }

    public static ActionTerms.actionGroup tryToFindEarlier(String action, Excel excel) throws NoSuchCellException {
        LocalDate oneAndAHalfMonthEarlier = getDay().dateOfDay.minusDays(45);
        Row startSearchFromHere;
        Row endSearchHere;
        for (Sheet sheet : excel){

            startSearchFromHere = getRowByDateOnASheet(oneAndAHalfMonthEarlier, sheet);
            endSearchHere = getTodayRowOnASheet(sheet);

            for (int i = startSearchFromHere.getRowNum(); i < endSearchHere.getRowNum(); i++) {
                for (Cell cell : sheet.getRow(i)){
                    if (textContainsString(cell.toString(), action)){
                        String title = excel.getTitleOfACell(cell);
                        return getActionGroup(title.substring(0, title.indexOf(" ")));
                    }
                }
            }
        }
        return null;
    }

    /**
     * Megnézzük, bármelyik tábla tartalmazza - e az adott file-ban.
     */
    public static boolean previouslyContains(String action, Excel excel) throws NoSuchCellException {
        return notNull(tryToFindEarlier(action, excel));
    }

    /**
     * @param sheet Konkrét excel táblában nézzük meg.
     */
    public static boolean previouslyContains(String action, Sheet sheet) throws NoSuchCellException {
        LocalDate oneAndAHalfMonthEarlier = getDay().dateOfDay.minusDays(45);
        Row startSearchFromHere = getRowByDateOnASheet(oneAndAHalfMonthEarlier, sheet);
        Row endSearchHere = getTodayRowOnASheet(sheet);

        for (int i = startSearchFromHere.getRowNum(); i < endSearchHere.getRowNum(); i++) {
            for (Cell cell : sheet.getRow(i)){
                if (textContainsString(cell.toString(), action)){
                    return true;
                }
            }
        }
        return false;
    }


}
