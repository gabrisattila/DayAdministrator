package Classes.Parser;

import Classes.I18N.NoSuchCellException;

import java.io.IOException;
import java.time.LocalTime;

import static Classes.Parser.Action.createAction;

public class Utazás extends Slot{
	public Utazás(LocalTime from, LocalTime to) throws NoSuchCellException, IOException {
		super(from, to, createAction(from, to, "utazás"));
	}
}
