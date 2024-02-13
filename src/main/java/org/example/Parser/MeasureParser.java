package org.example.Parser;

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

		String[] ms = part.split(" ");

		for (int i = 0; i < ms.length; i++) {
			if (ms[i].contains(",")){ // Súly és telefon idő
				double d = Double.parseDouble(ms[i]);
				//Telefon idő
				if (d < 24){
					measures.telefonIdő = d;
				}else { // Súly
					measures.súly = d;
				}
			}else { // Cigi és JO
				int x = Integer.parseInt(ms[i]);
				// Cigi
				if (i < ms.length - 1 && Parser.isCigi(ms[i + 1])){
					measures.cigi = x;
					i++;
				} else { // J O
					measures.JO = x;
				}
			}
		}

	}
}
