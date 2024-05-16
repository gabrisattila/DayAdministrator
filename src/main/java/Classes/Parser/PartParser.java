package Classes.Parser;

import Classes.I18N.NoSuchCellException;

import java.io.IOException;

public abstract class PartParser {

	protected Parser originParser;

	protected String part;

	protected PartParser(Parser parser) {
		originParser = parser;
	}

	abstract void parse() throws IOException, NoSuchCellException;

}
