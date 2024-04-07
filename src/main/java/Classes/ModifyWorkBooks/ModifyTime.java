package Classes.ModifyWorkBooks;

import Classes.I18N.NoSuchCellException;
import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import Classes.Parser.Slot;
import Classes.Parser.Time;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.*;
import static Classes.ModifyWorkBooks.OwnFileTypes.Excel.getRowByDateOnASheet;
import static Classes.ModifyWorkBooks.OwnFileTypes.Excel.getTodayRowOnASheet;

public class ModifyTime {

	private final Time time;

	private List<Slot> értékes;

	private List<Slot> szükséges;

	private List<Slot> szabadidő;

	private final Excel excel;

	public ModifyTime(Time time) throws IOException {
		this.time = time;
		excel = new Excel(dataExcelsPath + TimeExcelFileName);
	}

	public void modifyAllInTime() throws NoSuchCellException {
		collectSlots();
		modifyÉrtékes();
		modifySzükséges();
		modifySzabadidő();
	}

	private void modifyÉrtékes() throws NoSuchCellException {
		List<Slot> értékesSlots = értékes;
		Row todayRowInÉrtékes = getTodayRowOnASheet(excel.getSheet("Értékes"));
		Row todayRowInÉrtékes_Mi = getTodayRowOnASheet(excel.getSheet("Értékes_Mi"));
		for (Slot slot : értékes){
			//TODO Categorize the action then it's possible to take it in a cell.
			excel.getCellByTitle("");
			//TODO Érdemes lenne úgy megcsinálni, hogy nem a Slotokon megyünk végig, hanem az adott nap során.
			// Adott cellához kérjük le, melyik slot-ok tartozhatnak bele.
		}
	}

	public void modifySzükséges(){
		List<Slot> szükségesSlots = szükséges;
	}

	public void modifySzabadidő(){
		List<Slot> szabadSlots = szabadidő;
	}

	private void collectSlots() throws NoSuchCellException {
		List<Slot> wholeDay = time.getTimeLine();
		for (Slot slot : wholeDay){
			if (isÉrtékes(slot)){
				értékes.add(slot);
			} else if (isSzükséges(slot)) {
				szükséges.add(slot);
			} else if (isSzabadidő(slot)) {
				szabadidő.add(slot);
			}else {
				//TODO Megkérdezni a fhasz.-tól, hogy mizu, ez micsoda?
			}
		}
	}

	private boolean isÉrtékes(Slot slot) throws NoSuchCellException {
		String action = slot.getAction();
		return usualÉrtékesContains(action) || previusÉrtékesContains(action);
	}


	private boolean isSzükséges(Slot slot) throws NoSuchCellException {
		String action = slot.getAction();
		return usualSzükségesContains(action) || previousSzükségesContains(action);
	}

	private boolean isSzabadidő(Slot slot) throws NoSuchCellException {
		String action = slot.getAction();
		return usualSzabadidőContains(action) || previousSzabadidőContains(action);
	}

	private boolean usualÉrtékesContains(String action) {
		return usuals.get("Értékes").contains(action);
	}

	private boolean usualSzükségesContains(String action) {
		return usuals.get("Szükséges").contains(action);
	}

	private boolean usualSzabadidőContains(String action) {
		return usuals.get("Szabadidő").contains(action);
	}

	private boolean previusÉrtékesContains(String action) throws NoSuchCellException {
		return previouslyContains(action, excel.getSheet("Értékes_Mi"));
	}

	private boolean previousSzükségesContains(String action) throws NoSuchCellException {
		return previouslyContains(action, excel.getSheet("Szükséges_Mi"));
	}

	private boolean previousSzabadidőContains(String action) throws NoSuchCellException {
		return previouslyContains(action, excel.getSheet("Szabadidő_Mi"));
	}

	private boolean previouslyContains(String action, Sheet sheet) throws NoSuchCellException {
		LocalDate oneAndAHalfMonthEarlier = getDay().dateOfDay.minusDays(45);
		Row startSearchFromHere = getRowByDateOnASheet(oneAndAHalfMonthEarlier, sheet);
		Row endSearchHere = getTodayRowOnASheet(sheet);
		for (int i = startSearchFromHere.getRowNum(); i < endSearchHere.getRowNum(); i++) {
			for (Cell cell : sheet.getRow(i)){
				if (KMPSearch(action, cell.toString())){
					return true;
				}
			}
		}
		return false;
	}

}
