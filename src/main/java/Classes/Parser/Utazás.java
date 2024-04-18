package Classes.Parser;

import java.time.LocalTime;

public class Utazás extends Slot{
	public Utazás(LocalTime from, LocalTime to) {
		super(from, to, "utazás");
	}
}
