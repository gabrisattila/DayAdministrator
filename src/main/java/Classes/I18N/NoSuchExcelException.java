package Classes.I18N;

public class NoSuchExcelException extends AdministratorException{

	public NoSuchExcelException(Object what) {
		super("lehetőségek között", what);
	}

}
