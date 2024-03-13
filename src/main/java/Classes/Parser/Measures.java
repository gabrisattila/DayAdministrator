package Classes.Parser;

public record Measures(int kávé, int cigi, double súly, double telefonIdő, int JO) {

	public static Measures copy(Measures toCopy){
		return new Measures(toCopy.kávé, toCopy.cigi, toCopy.súly, toCopy.telefonIdő, toCopy.JO);
	}

}
