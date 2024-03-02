package Classes.I18N;

public class AdministratorException extends NoSuchFieldException {

	public AdministratorException(Object where, Object what){
		System.err.println("A " + what.toString() + " nem található " + where.toString() + "(-n).");
	}

}
