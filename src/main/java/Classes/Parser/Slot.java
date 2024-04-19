package Classes.Parser;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static Classes.I18N.I18N.notNull;

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
	}

	public double getTimeAmountFromTo() {
		long minutes = ChronoUnit.MINUTES.between(from, to);
		double hours = minutes / 60.0;
		double fraction = (minutes % 60) / 60.0;
		return hours + fraction;
	}

	public String toString(){
		return from + "-" + to + action;
	}

	public static Slot copy(Slot toCopy){
		return new Slot(toCopy.getFrom(), toCopy.getTo(), toCopy.getAction());
	}

	public static Slot createSlot(String slotInText, String next){
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
					amount = Integer.parseInt(slotParts.get(i));
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
			hours = Integer.parseInt(t);
			minutes = 0;
		} else if (t.length() == 3) {
			hours = Integer.parseInt(t.substring(0, 1));
			minutes = Integer.parseInt(t.substring(1, 3));
		} else if (t.length() == 4) {
			hours = Integer.parseInt(t.substring(0, 2));
			minutes = Integer.parseInt(t.substring(2, 4));
		} else {
			throw new RuntimeException(t + "-t nem lehet Time objektummá castolni.");
		}
		return LocalTime.of(hours, minutes);
	}

	private static LocalTime convertSecondTimeStrToTime(String t, LocalTime firstPart, String nextTimeInString){
		int hours, minutes;
		//Pl.: 7
		if (t.length() == 1){
			hours = Integer.parseInt(t);
			minutes = 0;
		}
		//Pl.: 10
		else if (t.length() == 2) {
			int time = Integer.parseInt(t);
			if (time == 5 || time == 10 || time == 15 || time == 20){
				String nextTime = nextTimeInString.split("-")[0];
				int nextHour = nextTime.length() == 3 ? Integer.parseInt(nextTime.substring(0, 0)) : Integer.parseInt(nextTime.substring(0, 1));
				if (firstPart.getHour() == nextHour || nextHour - firstPart.getHour() < time - firstPart.getHour() || nextHour == time){
					//TODO Edge case pl.: 905-(9)15; 15-1515;
					hours = time;
					minutes = 0;
				}else {
//					if (time > firstPart.getHour()){
//
//					}
					hours = firstPart.getHour();
					minutes = time;
				}
			} else {
				hours = firstPart.getHour();
				minutes = time;
			}
		}
		//Pl.: 905
		else if (t.length() == 3) {
			hours = Integer.parseInt(t.substring(0, 1));
			minutes = Integer.parseInt(t.substring(1, 3));
		}
		//Pl.: 1005
		else if (t.length() == 4) {
			hours = Integer.parseInt(t.substring(0, 2));
			minutes = Integer.parseInt(t.substring(2, 4));
		} else {
			throw new RuntimeException(t + "-t nem lehet Time objektummá castolni.");
		}
		return LocalTime.of(hours, minutes);
	}

	public static class SlotComparator implements Comparator<Slot> {

		@Override
		public int compare(Slot slot1, Slot slot2) {
			// Összehasonlítjuk az action adattagokat
			return slot1.getAction().compareTo(slot2.getAction());
		}
	}

}
