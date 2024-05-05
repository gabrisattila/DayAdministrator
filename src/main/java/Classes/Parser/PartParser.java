package Classes.Parser;

import java.io.IOException;

public abstract class PartParser {

	protected Parser originParser;

	protected String part;

	protected PartParser(Parser parser) {
		originParser = parser;
	}

	abstract void parse() throws IOException;

}
