package Classes.Parser;

import Classes.I18N.AskTheUserForInformation;
import Classes.I18N.I18N;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

import static Classes.I18N.I18N.ActionTerms.getActionGroupOfAnAction;
import static Classes.I18N.I18N.ActionTerms.getTypeOfAnAction;
import static Classes.I18N.I18N.notNull;
import static java.util.Objects.isNull;

@Getter
@Setter
public class Action {

    private I18N.ActionTerms.actionType actionType;

    private I18N.ActionTerms.actionGroup actionGroup;

    private String action;

    public Action(String action) throws AskTheUserForInformation {
        actionGroup = getActionGroupOfAnAction(action);
        actionType = getTypeOfAnAction(action);
        this.action = action;
    }

    public Action(String action, I18N.ActionTerms.actionType type) throws AskTheUserForInformation {
        actionType = type;
        actionGroup = getActionGroupOfAnAction(action);
        this.action = action;
    }

    public Action(String action, I18N.ActionTerms.actionGroup group, I18N.ActionTerms.actionType type){
        actionType = type;
        actionGroup = group;
        this.action = action;
    }

    public String toString(){
        return (notNull(actionGroup) ? actionGroup.toString() : "?") + " -> " + action;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Action))
            return false;

        return ((Action) obj).getAction().equals(action);
    }

    public boolean isBlank(){
        return action.isBlank() && isNull(actionType) && isNull(actionGroup);
    }

    public static Comparator<Action> ActionComparator(){
        return Comparator
                .comparing(Action::getActionType)
                .thenComparing(Action::getActionGroup)
                .thenComparing(Action::getAction);
    }

    public int compareTo(Action action) {
        return ActionComparator().compare(this, action);
    }
}
