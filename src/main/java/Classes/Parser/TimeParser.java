package Classes.Parser;

public class TimeParser extends PartParser{

	protected TimeParser(Parser parser) {
		super(parser);
	}

	@Override
	void parse() {
		part = originParser.getTime();
	}
}
