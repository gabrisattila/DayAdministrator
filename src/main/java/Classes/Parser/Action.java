package Classes.Parser;

import Classes.I18N.AskTheUserForInformation;
import Classes.I18N.I18N.ActionTerms;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

import static Classes.I18N.I18N.notNull;
import static java.util.Objects.isNull;

@Getter
@Setter
public class Action {

    private ActionTerms.actionType actionType;

    private ActionTerms.actionGroup actionGroup;

    private String action;

    public Action(String action) throws AskTheUserForInformation {
        actionGroup = ActionTerms.getActionGroup(action);
        actionType = ActionTerms.getActionType(action);
        this.action = action;
    }

    public Action(String action, ActionTerms.actionType type) {
        actionType = type;
        actionGroup = ActionTerms.getActionGroup(action);
        this.action = action;
    }

    public Action(String action, ActionTerms.actionGroup group, ActionTerms.actionType type){
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
