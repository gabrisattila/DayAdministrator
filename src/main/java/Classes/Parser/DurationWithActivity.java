package Classes.Parser;

import static Classes.I18N.I18N.roundTo2;

/**
 * This is a subclass which used by the Slot class. Which represents plus activity(ies) during the Slot.
 * For example: 13-14 ut. - 30 olv.; Here the 30 olv. is the durationWithActivity
 * @param amountInMinutes the duration's length in minutes.
 * @param activity the plus activity under the actual Slot.
 */
public record DurationWithActivity(int amountInMinutes, Action activity) {
	public double getTimeAmount(){
		int hours = (int) (amountInMinutes / 60.0);
		double minutes = (amountInMinutes % 60) / 60.0;
		return roundTo2(hours + minutes);
	}

	public String toString(){
		return getTimeAmount() + " " + activity;
	}
}
