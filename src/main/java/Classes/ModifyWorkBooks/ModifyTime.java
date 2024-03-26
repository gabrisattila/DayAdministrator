package Classes.ModifyWorkBooks;

import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import Classes.Parser.Slot;
import Classes.Parser.Time;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.*;

public class ModifyTime {

	private Time time;

	private List<Slot> értékes;

	private List<Slot> szükséges;

	private List<Slot> szabadidő;

	private Excel excel;

	public ModifyTime(Time time) throws IOException {
		this.time = time;
		excel = new Excel(dataExcelsPath + TimeExcelFileName);
	}

	public void modifyAllInTime(){
		collectSlots();
		modifyÉrtékes();
		modifySzükséges();
		modifySzabadidő();
	}

	private void modifyÉrtékes(){
		List<Slot> értékesSlots = collectSlots("Értékes");
	}

	public void modifySzükséges(){
		List<Slot> szükségesSlots = collectSlots("Szükséges");
	}

	public void modifySzabadidő(){
		List<Slot> szabadSlots = collectSlots("Szabadidő");
	}


	private List<Slot> collectSlots(String type) {
		switch (type){
			case "Értékes" -> {
				return értékes;
			}
			case "Szükséges" -> {
				return szükséges;
			}
			case "Szabadidő" -> {
				return szabadidő;
			}
			default -> {
				return null;
			}
		}
	}

	private void collectSlots(){
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

	private boolean isÉrtékes(Slot slot) {
		String action = slot.getAction();
		return usualÉrtékesContains(action) || previusÉrtékesContains(action);
	}


	private boolean isSzükséges(Slot slot) {
		String action = slot.getAction();
		return usualSzükségesContains(action) || previousSzükségesContains(action);
	}

	private boolean isSzabadidő(Slot slot) {
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

	private boolean previusÉrtékesContains(String action) {
		LocalDate oneAndAHalfMonthEarlier = getDay().dateOfDay.minusDays(45);
		Sheet szükségesMi = excel.getSheet("Szükséges_Mi");
		Row row = szükségesMi.getRow();
	}

	private boolean previousSzükségesContains(String action) {
	}

	private boolean previousSzabadidőContains(String action) {
	}

}
