package Classes.Parser;

/**
 * This is a subclass which used by the Slot class. Which represents plus activity(ies) during the Slot.
 * For example: 13-14 ut. - 30 olv.; Here the 30 olv. is the durationWithActivity
 * @param amountInMinutes the duration's length in minutes.
 * @param activity the plus activity under the actual Slot.
 */
public record DurationWithActivity(int amountInMinutes, String activity) {}
