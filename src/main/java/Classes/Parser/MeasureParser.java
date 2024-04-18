package Classes.Parser;

import lombok.Getter;

import static Classes.I18N.I18N.delimiterBetweenTextParts;
import static Classes.Parser.Measures.isCigi;
import static Classes.Parser.Measures.isKávé;

@Getter
public class MeasureParser extends PartParser {

	private Measures measures;

	protected MeasureParser(Parser parser) {
		super(parser);
	}

	@Override
	void parse() {
		part = originParser.getMeasures();

		String[] ms = part.split(delimiterBetweenTextParts);

		double súly = 0, telefonIdő = 0;
		int cigi = 0, JO = 0, kávé = 0, hetiTervezettTeendőMennyiség = 0, hetiTeljesítettTeendőMennyiség = 0;
		for (int i = 0; i < ms.length; i++) {
			if (ms[i].contains(",")){ // Súly
				súly = Double.parseDouble(ms[i]);
			}
			else if (ms[i].contains(":")) { // Telefon idő
				String[] hourAndMinute = ms[i].split(":");
				int hourInMinutes = Integer.parseInt(hourAndMinute[0]) * 60;
				int minutes = Integer.parseInt(hourAndMinute[1]);
				telefonIdő = hourInMinutes + minutes;
			}
			else if (isKávé(ms[i])) { // Kávé
				kávé = Integer.parseInt(ms[i]);
			} else { // Cigi és JO
				int x = Integer.parseInt(ms[i]);
				// Cigi
				if (i < ms.length - 1 && isCigi(ms[i + 1])){
					cigi = x;
					i++;
				} else { // J O
					//TODO hetiTervezett és teljesített mennyiség bevezetése
					JO = x;
				}
			}
		}
		measures = new Measures(kávé, cigi, súly, telefonIdő, JO, hetiTervezettTeendőMennyiség, hetiTeljesítettTeendőMennyiség);
	}
}
