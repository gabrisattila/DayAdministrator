package Classes.Parser;

import lombok.Getter;

import java.util.LinkedList;

import static Classes.Day.getDay;
import static Classes.Parser.Slot.createSlot;

@Getter
public class TimeParser extends PartParser{

	LinkedList<Slot> timeLine;

	protected TimeParser(Parser parser) {
		super(parser);
		parse();
	}

	@Override
	public void parse() {
		part = originParser.getTime();
		String[] preListOfSlots = part.split(";");
		timeLine = makeSlots(preListOfSlots);
	}

	private LinkedList<Slot> makeSlots(String[] slotsInString){
		LinkedList<Slot> slots = new LinkedList<>();
		for (int i = 0; i < slotsInString.length; i++) {
			if (i < slotsInString.length - 1){
				slots.add(createSlot(slotsInString[i], slotsInString[i + 1]));
			}else {
				slots.add(createSlot(slotsInString[i], null));
			}
		}
		return slots;
	}
}
