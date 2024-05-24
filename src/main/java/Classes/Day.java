package Classes;

import Classes.Parser.Measures;
import Classes.Parser.Money;
import Classes.Parser.Slot;
import Classes.Parser.Time;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedList;

import static Classes.I18N.I18N.toDate;
import static java.util.Objects.isNull;

@Data
public class Day {

	public static Day day;

	public LocalDate dateOfDay;

	private Measures measures;

	private Money money;

	private Time time;

	public static Day getDay(String dateOfToday){
		getDay();
		day.dateOfDay = toDate(dateOfToday);
		return day;
	}

	public static Day getDay(){
		if (isNull(day)) {
			day = new Day();
		}
		if (isNull(day.dateOfDay))
			day.dateOfDay = LocalDate.now();
		return day;
	}

}
