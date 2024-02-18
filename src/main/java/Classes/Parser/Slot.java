package Classes.Parser;

public record Slot(Slot last, double from, double to, String action) {

	public static Slot copy(Slot toCopy){
		return new Slot(toCopy.last(), toCopy.to(), toCopy.from(), toCopy.action());
	}

}
