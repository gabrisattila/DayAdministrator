package Classes.Parser;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static Classes.I18N.notNull;

@Getter
@Setter
public class Slot {

	private String next;

	private LocalTime from;

	private LocalTime to;

	private String action;

	private DurationWithActivity duration;

	public Slot(String next, LocalTime from, LocalTime to, String action){
		this.next = next;
		this.from = from;
		this.to = to;
		this.action = action;
	}

	public static Slot copy(Slot toCopy){
		return new Slot(toCopy.getNext(), toCopy.getFrom(), toCopy.getTo(), toCopy.getAction());
	}

	public static Slot createSlot(String slotInText, String next){
		List<String> slotParts = splitSlotStringToItsParts(slotInText, next);
		LocalTime from = convertFirstTimeStrToTime(slotParts.get(0));
		LocalTime to = convertSecondTimeStrToTime(slotParts.get(1), from, next);
		String action = slotParts.get(2);
		DurationWithActivity _duration = null;
		if (slotParts.size() > 3){
			_duration = new DurationWithActivity(Integer.parseInt(slotParts.get(3)), slotParts.get(4));
		}
		Slot slot = new Slot(next, from, to, action);
		if (notNull(_duration))
			slot.duration = _duration;
		return slot;
	}

	private static List<String> splitSlotStringToItsParts(String slotString, String next){
		List<String> parts = new ArrayList<>();
		String[] firstSplit = slotString.split("-");
		String firstTime = firstSplit[0];
		String[] secondSplit = firstSplit[1].split(" ");
		String secondTime = secondSplit[0];
		String action = secondSplit[1];
		parts.add(firstTime);
		parts.add(secondTime);
		parts.add(action);
		if (firstSplit.length == 3){
			String[] dur = firstSplit[2].split(" ");
			String durationTime = dur[0];
			String durationAction = dur[1];
			parts.add(firstTime);
			parts.add(secondTime);
			parts.add(action);
			parts.add(durationTime);
			parts.add(durationAction);
		}
		return parts;
	}

	private static LocalTime convertFirstTimeStrToTime(String t){
		int hours, minutes;
		if (t.length() == 1 || t.length() == 2){
			hours = Integer.parseInt(t);
			minutes = 0;
		} else if (t.length() == 3) {
			hours = Integer.parseInt(t.substring(0, 0));
			minutes = Integer.parseInt(t.substring(1, 2));
		} else if (t.length() == 4) {
			hours = Integer.parseInt(t.substring(0, 1));
			minutes = Integer.parseInt(t.substring(2, 3));
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
			hours = Integer.parseInt(t.substring(0, 0));
			minutes = Integer.parseInt(t.substring(1, 2));
		}
		//Pl.: 1005
		else if (t.length() == 4) {
			hours = Integer.parseInt(t.substring(0, 1));
			minutes = Integer.parseInt(t.substring(2, 3));
		} else {
			throw new RuntimeException(t + "-t nem lehet Time objektummá castolni.");
		}
		return LocalTime.of(hours, minutes);
	}

}
