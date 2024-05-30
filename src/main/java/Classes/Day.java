package Classes;

import Classes.Parser.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static Classes.I18N.I18N.toDate;
import static java.util.Objects.isNull;

@Data
public class Day {

	public static Day day;

	public LocalDate dateOfDay;

	private Measures measures;

	private Money money;

	private Time time;

	private List<Utazás> napiUtazások;

	public static Day getDay(String dateOfToday){
		getDay();
		day.dateOfDay = toDate(dateOfToday);
		return day;
	}

	public static Day getDay(){
		if (isNull(day)) {
			day = new Day();
			day.setNapiUtazások(new ArrayList<>());
		}
		if (isNull(day.dateOfDay))
			day.dateOfDay = LocalDate.now();
		return day;
	}

}
