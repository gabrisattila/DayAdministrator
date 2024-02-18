package Classes;

import Classes.Parser.Measures;
import Classes.Parser.Money;
import Classes.Parser.Slot;
import lombok.Data;

import java.util.LinkedList;

import static java.util.Objects.isNull;

@Data
public class Day {

	public static Day day;

	private Measures measures;

	private Money money;

	private LinkedList<Slot> timeLine;

	public static Day getDay(){
		if (isNull(day)) {
			day = new Day();
		}
		return day;
	}

}
