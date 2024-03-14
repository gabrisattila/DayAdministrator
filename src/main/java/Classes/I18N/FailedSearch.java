package Classes.I18N;

public class FailedSearch extends AdministratorException {

	public FailedSearch(Object where, Object what) {
		super(where, what);
	}

	public FailedSearch(String what) {
		super(what);
	}
}
