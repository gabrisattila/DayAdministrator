package Classes.ModifyWorkBooks;

import Classes.I18N.AskTheUserForInformation;
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
import static Classes.I18N.I18N.ActionTerms.actionType.*;
import static Classes.I18N.I18N.ActionTerms.getTypeOfAction;
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
			try {
				if (isÉrtékes(slot)){
					értékes.add(slot);
				} else if (isSzükséges(slot)) {
					szükséges.add(slot);
				} else if (isSzabadidő(slot)) {
					szabadidő.add(slot);
				}
			}catch (AskTheUserForInformation e){
				String válasz = e.handlingAndGetAnswer();
				switch (válasz){
					case "Értékes" : {
						értékes.add(slot);
					}
					case "Szükséges" : {
						szükséges.add(slot);
					}
					case "Szabadidő" : {
						szabadidő.add(slot);
					}
				}
			}
		}
	}

	private boolean isÉrtékes(Slot slot) throws NoSuchCellException, AskTheUserForInformation {
		String action = slot.getAction();
		return usualÉrtékesContains(action) || previusÉrtékesContains(action);
	}


	private boolean isSzükséges(Slot slot) throws NoSuchCellException, AskTheUserForInformation {
		String action = slot.getAction();
		return usualSzükségesContains(action) || previousSzükségesContains(action);
	}

	private boolean isSzabadidő(Slot slot) throws NoSuchCellException, AskTheUserForInformation {
		String action = slot.getAction();
		return usualSzabadidőContains(action) || previousSzabadidőContains(action);
	}

	private boolean usualÉrtékesContains(String action) throws AskTheUserForInformation {
		return Értékes == getTypeOfAction(action);
	}

	private boolean usualSzükségesContains(String action) throws AskTheUserForInformation {
		return Szükséges == getTypeOfAction(action);
	}

	private boolean usualSzabadidőContains(String action) throws AskTheUserForInformation {
		return Szabadidő == getTypeOfAction(action);
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
