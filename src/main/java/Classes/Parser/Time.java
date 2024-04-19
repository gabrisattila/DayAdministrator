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

	public String toString(){
		StringBuilder s = new StringBuilder();
		for (Slot slot : timeLine){
			s.append(slot.toString()).append(" ");
		}
		return s.toString();
	}
}
