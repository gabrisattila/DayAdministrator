package Classes.Parser;

import Classes.I18N.I18N;
import Classes.I18N.NoSuchCellException;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.ActionTerms.SzükségesActionTerms;
import static Classes.I18N.I18N.ActionTerms.actionGroup.Utazás_és_készülődés;
import static Classes.I18N.I18N.textContainsString;
import static Classes.Parser.Action.createAction;

@Getter
public class Utazás extends Slot{

	private final int[] minusTimes;

	private double travellTime;

	private double travellTimeAfterActivities;

	public Utazás(LocalTime from, LocalTime to, int... minusTimes) throws NoSuchCellException, IOException {
		super(from, to, createAction("utazás", from, to));
		this.minusTimes = minusTimes;
		setTravellTimeVars();
	}

	private void setTravellTimeVars(){
		double duration = super.getTimeAmount();
		travellTime = duration;
		for (int minus : minusTimes){
			duration -= ((double) minus / 60);
		}
		travellTimeAfterActivities = duration;
	}

	public static Slot setToUtazásIfItIs(Slot slot) throws NoSuchCellException, IOException {
		if (textContainsString(slot.getActionDescriptor(), "utazás")){
			int minus = slot.getDurations().length;
			if (minus > 0) {
				int[] minusTimes = new int[minus];
				int i = 0;
				for (DurationWithActivity duration : slot.getDurations()){
					minusTimes[i] = duration.amountInMinutes();
					i++;
				}
				Utazás utazás = new Utazás(slot.getFrom(), slot.getTo(), minusTimes);
				getDay().getNapiUtazások().add(utazás);
				return utazás;
			}
        }
        return slot;
    }

}

