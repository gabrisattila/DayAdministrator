package org.example.Parser;

public abstract class PartParser {

	protected Parser originParser;

	protected String part;

	protected PartParser(Parser parser) {
		originParser = parser;
	}

	abstract void parse();

}
