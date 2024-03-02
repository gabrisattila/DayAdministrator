package Classes.Parser;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Time {

	private final List<Slot> timeLine;

	public Time(List<Slot> tL){
		timeLine = tL;
	}

}
