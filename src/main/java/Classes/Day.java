package Classes;

import Classes.Parser.Measures;
import Classes.Parser.Money;
import Classes.Parser.Slot;
import Classes.Parser.Time;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedList;

import static java.util.Objects.isNull;

@Data
public class Day {

	public static Day day;

	public LocalDate dateOfDay;

	private Measures measures;

	private Money money;

	private Time time;

	public static Day getDay(){
		if (isNull(day)) {
			day = new Day();
		}
		return day;
	}

}
