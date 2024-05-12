package Classes.Parser;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static Classes.I18N.AskTheUserForInformation.getStringAnswer;
import static Classes.I18N.I18N.notNull;
import static Classes.I18N.I18N.textContainsString;
import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;

@Getter
@Setter
public class Slot {

	private LocalTime from;

	private LocalTime to;

	private double timeAmount;

	private String action;

	private DurationWithActivity[] durations;

	public Slot(LocalTime from, LocalTime to, String action){
		this.from = from;
		this.to = to;
		this.action = action;
		timeAmount = getTimeAmountFromTo();
	}

	public double getTimeAmountFromTo() {
		long minutes = ChronoUnit.MINUTES.between(from, to);
		int hours = (int) (minutes / 60.0);
		double fraction = (minutes % 60) / 60.0;
		return hours + fraction;
	}

	public String toString(){
        getTimeAmountFromTo();
        return (isNull(from) ? "" : from) +
                "-" +
                (isNull(to) ? "" : to) +
                " -> " +
                getTimeAmountFromTo() +
                " h, " +
                action +
		        "\n";
	}

	public static Slot copy(Slot toCopy){
		return new Slot(toCopy.getFrom(), toCopy.getTo(), toCopy.getAction());
	}

	public static Slot createSlot(String slotInText, String next) throws IOException {
		List<String> slotParts = splitSlotStringToItsParts(slotInText);

		LocalTime from = convertFirstTimeStrToTime(slotParts.get(0));

		LocalTime to = convertSecondTimeStrToTime(slotParts.get(1), from, next);

		String action = slotParts.get(2);
		DurationWithActivity[] _durations = null;

		if (slotParts.size() > 3){
			_durations = new DurationWithActivity[(slotParts.size() - 3) / 2];
			int amount = 0; String activity = "";
			int dCounter = 0;
			for (int i = 3; i < slotParts.size(); i++) {
				if (i % 2 == 0){
					activity = slotParts.get(i);
				}else {
					amount = parseInt(slotParts.get(i));
				}
				if (amount != 0 && !activity.isBlank()) {
					_durations[dCounter] = new DurationWithActivity(amount, activity);
					dCounter++;
					activity = "";
					amount = 0;
				}
			}
		}

		Slot slot = new Slot(from, to, action);
		modifyTimeAmountInsteadOfAfterMidnightTime(slot);

		if (notNull(_durations))
			slot.durations = _durations;

		return slot;
	}

	private static List<String> splitSlotStringToItsParts(String slotString){
		List<String> parts = new ArrayList<>();
		String[] firstSplit = slotString.split("-");
		String firstTime = firstSplit[0].trim();
		String[] secondSplit = firstSplit[1].split(" ");
		String secondTime = secondSplit[0].trim();
		String action = secondSplit[1].trim();
		parts.add(firstTime);
		parts.add(secondTime);
		parts.add(action);
		if (firstSplit.length > 2){
			String[] dur;
			String durationTime;
			String durationAction;
			for (int i = 2; i < firstSplit.length; i++) {
				firstSplit[i] = firstSplit[i].trim();
				dur = firstSplit[i].split(" ");
				durationTime = dur[0];
				durationAction = dur[1];
				parts.add(durationTime);
				parts.add(durationAction);
			}
		}
		return parts;
	}

	private static LocalTime convertFirstTimeStrToTime(String t){
		int hours, minutes;
		if (t.length() == 1 || t.length() == 2){
			hours = parseInt(t);
			minutes = 0;
		} else if (t.length() == 3) {
			hours = parseInt(t.substring(0, 1));
			minutes = parseInt(t.substring(1, 3));
		} else if (t.length() == 4) {
			hours = parseInt(t.substring(0, 2));
			minutes = parseInt(t.substring(2, 4));
		} else {
			throw new RuntimeException(t + "-t nem lehet Time objektummá castolni.");
		}
		return LocalTime.of(hours, minutes);
	}

	private static LocalTime convertSecondTimeStrToTime(String t, LocalTime currTimeFirstPart, String nextTimeInString) throws IOException {
		int hours, minutes;
		//Pl.: 7
		if (t.length() == 1){
			hours = parseInt(t);
			minutes = 0;
		}
		//Pl.: 10
		else if (t.length() == 2) {
			int currTimeSecondPart = parseInt(t);

			if (currTimeSecondPart % 5 == 0){

				if (notNull(nextTimeInString)){
					String nextTime = nextTimeInString.split("-")[0];
					int nextTimeFirstPartHour = nextTime.length() <= 2 ? parseInt(nextTime) : parseInt(nextTime.substring(0, 2));
					int nextTimeSecondPartMinute = nextTime.length() <= 2 ? 0 : parseInt(nextTime.substring(2));

					if (currTimeSecondPart >= currTimeFirstPart.getMinute()){
						// Edge case
						// Amikor egyenlő  x és y
						// pl.: 1205 - (12)15; 15-1515;
						// 		1205 -     15; 15-1550;

						// Megnézzük, hogy kisebb-e mint a feltételezett időpont ->
						// a fenti példában tehát a 1205 - (12)15; 15 - 1515
						// megnézzük, hogy a 15(00) (a második időpont kezdete) kisebb-e mint a 1215
						if (LocalTime.of(nextTimeFirstPartHour, nextTimeSecondPartMinute).isBefore(currTimeFirstPart)){
							//Ha igen akkor percről beszélünk
							hours = currTimeFirstPart.getHour();
							minutes = currTimeSecondPart;
						}else {
							//Ha nem, akkor rohadtul nem tudjuk. Meg kell kérdezni!!
							System.out.println("\nEmlítettél egy tevékenységet a következő intervallumban:\n" +
									currTimeFirstPart + " - " + t);
							System.out.println("Kérlek mond el, hogy a második helyen szereplő " + t + " időpont, az percet, vagy órát jelöl?");
							String válasz = getStringAnswer();
							válasz = válasz.trim();
							if (textContainsString(válasz, "perc") || textContainsString(válasz, "p")){
								hours = currTimeFirstPart.getHour();
								minutes = currTimeSecondPart;
							}else {
								hours = currTimeSecondPart;
								minutes = 0;
							}
						}
					}else{
						minutes = 0;
						hours = currTimeSecondPart;
					}
				}else {
					//Ha nem, akkor rohadtul nem tudjuk. Meg kell kérdezni!!
					System.out.println("\nEmlítettél egy tevékenységet a következő intervallumban:\n" +
							currTimeFirstPart + " - " + t);
					System.out.println("Kérlek mond el, hogy a második helyen szereplő " + t + " időpont, az percet, vagy órát jelöl?");
					String válasz = getStringAnswer();
					válasz = válasz.trim();
					if (textContainsString(válasz, "perc") || textContainsString(válasz, "p")){
						hours = currTimeFirstPart.getHour();
						minutes = currTimeSecondPart;
					}else {
						hours = currTimeSecondPart;
						minutes = 0;
					}
				}
			}else{
				hours = currTimeSecondPart;
				minutes = 0;
			}
		}
		//Pl.: 905
		else if (t.length() == 3) {
			hours = parseInt(t.substring(0, 1));
			minutes = parseInt(t.substring(1, 3));
		}
		//Pl.: 1005
		else if (t.length() == 4) {
			hours = parseInt(t.substring(0, 2));
			minutes = parseInt(t.substring(2, 4));
		} else {
			throw new RuntimeException(t + "-t nem lehet Time objektummá castolni.");
		}
		hours = handleToInsteadOfAfterMidnightTime(hours);
		return LocalTime.of(hours, minutes);
	}

	private static int handleToInsteadOfAfterMidnightTime(int to){
		if (to >= 24)
			to -= 24;
		return to;
	}

	private static void modifyTimeAmountInsteadOfAfterMidnightTime(Slot slot){

		int h = 0, m = 0;
		LocalTime tmp;
		//Ha a slot kezdete este tíz és éjfél között, a vége pedig éjfél után van
		if (timeIsBetween(slot.from, LocalTime.of(22, 0), LocalTime.of(23,59)) &&
			timeIsBetween(slot.to, LocalTime.MIDNIGHT, LocalTime.of(5, 30))){

			tmp = LocalTime.MIDNIGHT.minusHours(slot.from.getHour()).minusMinutes(slot.from.getMinute());
			tmp = tmp.plusHours(slot.to.getHour()).plusMinutes(slot.to.getMinute());
			h = tmp.getHour(); m = tmp.getMinute();
		}
		slot.setTimeAmount(h + (double) m / 60);
	}

	private static boolean timeIsBetween(LocalTime time, LocalTime betw1, LocalTime betw2){
		return (betw1.isBefore(time) || betw1.equals(time)) && (betw2.isAfter(time) || betw2.equals(time));
	}

	public static class SlotComparator implements Comparator<Slot> {

		@Override
		public int compare(Slot slot1, Slot slot2) {
			// Összehasonlítjuk az action adattagokat
			return slot1.getAction().compareTo(slot2.getAction());
		}
	}

}
