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
			ms[i] = ms[i].trim();

			if (ms[i].contains(",")){ // Súly
				String s = ms[i].replace(',', '.');
				s = s.split(" ")[0];
				súly = Double.parseDouble(s);
			}
			else if (ms[i].contains(":")) { // Telefon idő
				String[] hourAndMinute = ms[i].split(":");
				int hour = Integer.parseInt(hourAndMinute[0]);
				double minutes = (double) Integer.parseInt(hourAndMinute[1]) / 60;
				telefonIdő = hour + minutes;
			}
			else if (isKávé(ms[i])) { // Kávé
				ms[i] = ms[i].split(" ")[0];
				kávé += Integer.parseInt(ms[i]);
			} else { // Cigi és JO
				// Cigi
				if (isCigi(ms[i])){
					cigi = Integer.parseInt(ms[i].split(" ")[0]);
				} else { // J O
					JO = Integer.parseInt(ms[i]);
				}
				//TODO hetiTervezett és teljesített mennyiség bevezetése
			}
		}
		measures = new Measures(kávé, cigi, súly, telefonIdő, JO, hetiTervezettTeendőMennyiség, hetiTeljesítettTeendőMennyiség);
	}
}
