package Classes.Parser;

import lombok.Getter;

@Getter
public class MeasureParser extends PartParser {

	private Measures measures;

	protected MeasureParser(Parser parser) {
		super(parser);
	}

	@Override
	void parse() {
		part = originParser.getMeasures();

		String[] ms = part.split(";");

		double súly = 0, telefonIdő = 0;
		int cigi = 0, JO = 0;
		for (int i = 0; i < ms.length; i++) {
			if (ms[i].contains(",")){ // Súly
				//Súly
				súly = Double.parseDouble(ms[i]);
			} else if (ms[i].contains(":")) { // Telefon idő
				String[] hourAndMinute = ms[i].split(":");
				int hourInMinutes = Integer.parseInt(hourAndMinute[0]) * 60;
				int minutes = Integer.parseInt(hourAndMinute[1]);
				telefonIdő = hourInMinutes + minutes;
			} else { // Cigi és JO
				int x = Integer.parseInt(ms[i]);
				// Cigi
				if (i < ms.length - 1 && Parser.isCigi(ms[i + 1])){
					cigi = x;
					i++;
				} else { // J O
					JO = x;
				}
			}
		}
		//TODO Parse out kávé
		measures = new Measures(0, cigi, súly, telefonIdő, JO);

	}
}
