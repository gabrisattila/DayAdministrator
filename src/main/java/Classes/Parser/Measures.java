package Classes.Parser;

import static Classes.I18N.I18N.textContainsString;

public record Measures(int kávé, int cigi, double súly, double telefonIdő, int JO, int hetiTerv, int hetiTeljesített) {

	static Measures copy(Measures toCopy){
		return new Measures(toCopy.kávé, toCopy.cigi, toCopy.súly, toCopy.telefonIdő, toCopy.JO, toCopy.hetiTerv, toCopy.hetiTeljesített);
	}

	static boolean isCigi(String m){
		return "cigi".equals(m) || "Cigi".equals(m) || "CIGI".equals(m) || "c".equals(m) || "C".equals(m) ||
				"cig".equals(m) || "ci".equals(m) || "Cig".equals(m) || "Ci".equals(m);
	}

	static boolean isKávé(String part){
		return textContainsString(part.toLowerCase(), "kávé");
	}

	static boolean isMeasure(String part){
		return !isMoney(part) && !isTime(part);
	}

	static boolean isMoney(String part){
		return part.contains("Ft") || part.contains("FT") ||
				part.contains("KP") || part.contains("Kp") || part.contains("kp") ||
				part.contains("Kártya");
	}

	static boolean isTime(String part){
		return part.contains("-");
	}

}
