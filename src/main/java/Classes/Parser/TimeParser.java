package Classes.Parser;

import Classes.I18N.AskTheUserForInformation;
import lombok.Getter;

import java.io.IOException;
import java.util.LinkedList;

import static Classes.Day.getDay;
import static Classes.Parser.Slot.createSlot;

@Getter
public class TimeParser extends PartParser{

	Time time;

	protected TimeParser(Parser parser) {
		super(parser);
	}

	@Override
	public void parse() throws IOException {
		part = originParser.getTime();
		String[] preListOfSlots = part.split(";");
		time = new Time(makeSlots(preListOfSlots));
	}

	private LinkedList<Slot> makeSlots(String[] slotsInString) throws IOException, AskTheUserForInformation {
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
