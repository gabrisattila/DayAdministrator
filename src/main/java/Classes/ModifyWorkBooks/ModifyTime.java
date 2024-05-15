package Classes.ModifyWorkBooks;

import Classes.I18N.AskTheUserForInformation;
import Classes.I18N.NoSuchCellException;
import Classes.OwnFileTypes.Excel;
import Classes.Parser.DurationWithActivity;
import Classes.Parser.Slot;
import Classes.Parser.Time;
import lombok.Getter;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static Classes.Day.getDay;
import static Classes.I18N.I18N.*;
import static Classes.I18N.I18N.ActionTerms.actionType.*;
import static Classes.I18N.I18N.ActionTerms.getTitleOfAnAction;
import static Classes.I18N.I18N.ActionTerms.getActionType;
import static Classes.OwnFileTypes.Excel.*;
import static java.util.Objects.requireNonNull;

@Getter
public class ModifyTime {

	private final Time time;

	private final List<Slot> értékes;

	private final List<Slot> szükséges;

	private final List<Slot> szabadidő;

	private Excel excel;

	/**
	 * Used for the test cases
	 */
	public ModifyTime(Time time) {
		this.time = time;
		értékes = new ArrayList<>(); szükséges = new ArrayList<>(); szabadidő = new ArrayList<>();
	}

	public ModifyTime(Time time, Excel excel) throws NoSuchCellException, IOException {
		this.time = time;
		this.excel = excel;
		értékes = new ArrayList<>(); szükséges = new ArrayList<>(); szabadidő = new ArrayList<>();
		modifyTime();
	}

	private void modifyTime() throws NoSuchCellException, IOException {
		collectSlots(false);
		modifyÉrtékes();
		modifySzükséges();
		modifySzabadidő();
	}

	protected void collectSlots(boolean isTest) throws IOException {
		List<Slot> wholeDay = time.getTimeLine();
		List<Slot> extras = new ArrayList<>();
		Slot extraSlot;
		for (Slot slot : wholeDay){
			if (notNull(slot.getDurations())){
				for (DurationWithActivity duration : slot.getDurations()){
					extraSlot = new Slot(null, null, duration.activity());
					extraSlot.setTimeAmount(duration.getTimeAmount());
					extras.add(extraSlot);
				}
			}else {
				slot.setTimeAmount(slot.getTimeAmountFromTo());
			}
			addSlotToProperList(slot, isTest);
		}
		for (Slot slot : extras){
			addSlotToProperList(slot, isTest);
		}
	}

	/**
	 * @param slot the slot what about want to decide which list to it's belongs
	 * @param isTest if we use the test case (in test functions) then we use is_ForTest functions in decideing because it didn't look through the entire workbook
	 */
	private void addSlotToProperList(Slot slot, boolean isTest) throws IOException {
		try {
			if (isTest ? isÉrtékesForTest(slot) : isÉrtékes(slot)){
				értékes.add(slot);
			} else if (isTest ? isSzükségesForTest(slot) : isSzükséges(slot)) {
				szükséges.add(slot);
			} else if (isTest ? isSzabadidőForTest(slot) : isSzabadidő(slot)) {
				szabadidő.add(slot);
			}
		} catch (AskTheUserForInformation e){
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
		} catch (NoSuchCellException e) {
			throw new RuntimeException(e);
		}
	}


	protected void modifyÉrtékes() throws NoSuchCellException {
		modifyTimeExcelSheetsOfAnActionType(értékes, Értékes);
	}

	protected void modifySzükséges() throws NoSuchCellException {
		modifyTimeExcelSheetsOfAnActionType(szükséges, Szükséges);
	}

	protected void modifySzabadidő() throws NoSuchCellException {
		modifyTimeExcelSheetsOfAnActionType(szabadidő, Szabadidő);
	}

	private void modifyTimeExcelSheetsOfAnActionType(List<Slot> slots, ActionTerms.actionType actionType) throws NoSuchCellException {
		Row todayRowConcreteTimeSheet = getTodayRowOnASheet(excel.getSheet(actionType.toString()));
		Row todayRowActionStringSheet = getTodayRowOnASheet(excel.getSheet(actionType + "_Mi"));

		Map<String, List<Slot>> activities = sortSlotsByActionType(slots, actionType);

		writeActionStringsToRow(slots, actionType, todayRowActionStringSheet);

		Map<String, String> activitiesInSpearatedTimeStrings = makeSeparatedTimeParts(activities);

		writeTimesToRow(activitiesInSpearatedTimeStrings, todayRowConcreteTimeSheet);
	}

	protected Map<String, List<Slot>> sortSlotsByActionType(List<Slot> slots, ActionTerms.actionType typeOfSlots){
		Map<String, List<Slot>> activities = new HashMap<>();
		String titleOfAction;
		for (Slot slot : slots){
			titleOfAction = getTitleOfAnAction(slot.getAction(), typeOfSlots);
			if (notNull(activities.get(titleOfAction))){
				activities.get(titleOfAction).add(slot);
			}else {
				activities.put(titleOfAction, new ArrayList<>(){{add(slot);}});
			}
		}
		return activities;
	}

	private void writeActionStringsToRow(List<Slot> slots, ActionTerms.actionType typeOfSlots, Row todayMiRow){
		for (Slot slot : slots){
			writeActionToACell(
					excel.getCellFromRowByTitle(
							getTitleOfAnAction(slot.getAction(), typeOfSlots),
							todayMiRow
					),
					slot.getAction()
			);
		}
	}

	private void writeTimesToRow(Map<String, String> activitiesInTimeStrings, Row rowToWrite) throws NoSuchCellException {
		String titleOfIthCell;
		String titleOfIPlus1thCell;
		String separatedTimeString;
		for (int i = 0; i < rowLength(rowToWrite); i++) {
			titleOfIthCell = excel.getTitleOfACell(rowToWrite.getCell(i));
			titleOfIPlus1thCell = excel.getTitleOfACell(rowToWrite.getCell(i + 1));
			separatedTimeString = activitiesInTimeStrings.get(titleOfIthCell);

			if (notNull(separatedTimeString)){

				//Amennyiben egy olyan cellában állunk melynek van címe és a rákövekező cellának ugyanúgy -> tehát nincs felsorolva szeparálva az idő
				if (!"".equals(titleOfIPlus1thCell))
				{
					rowToWrite.getCell(i).setCellValue(evaluateExpression(separatedTimeString));
				}
				//Itt az utazással kapcsolatos adatokat írjuk a cellákba
				else if (textContainsString(titleOfIthCell, "utazás")) {
					//TODO Megcsinálni az utazás esetén, hogy írom az cellákat.
					throw new NotImplementedException("Utazás cella szerkesztése kimaradt, a beleírni kívánt érték: " + separatedTimeString);
				}
				//Ez esetben pedig a következő cellába olyan érték kerül ami szeparált time formátumot tartalmaz.
				else {
					//Teljes idő
					rowToWrite.getCell(i).setCellValue(evaluateExpression(separatedTimeString));
					//Részletekre bontott formátum
					rowToWrite.getCell(i + 1).setCellValue(activitiesInTimeStrings.get(titleOfIthCell));
				}
			}
		}
	}


	private boolean isÉrtékes(Slot slot) throws NoSuchCellException, AskTheUserForInformation {
		String action = slot.getAction();
		return usualÉrtékesContains(action) || previusÉrtékesContains(action);
	}

	protected boolean isÉrtékesForTest(Slot slot) throws AskTheUserForInformation {
		return usualÉrtékesContains(slot.getAction());
	}

	private boolean isSzükséges(Slot slot) throws NoSuchCellException, AskTheUserForInformation {
		String action = slot.getAction();
		return usualSzükségesContains(action) || previousSzükségesContains(action);
	}

	protected boolean isSzükségesForTest(Slot slot) throws AskTheUserForInformation {
		return usualSzükségesContains(slot.getAction());
	}

	private boolean isSzabadidő(Slot slot) throws NoSuchCellException, AskTheUserForInformation {
		String action = slot.getAction();
		return usualSzabadidőContains(action) || previousSzabadidőContains(action);
	}

	protected boolean isSzabadidőForTest(Slot slot) throws AskTheUserForInformation {
		return usualSzabadidőContains(slot.getAction());
	}

	private boolean usualÉrtékesContains(String action) throws AskTheUserForInformation {
		return Értékes == getActionType(action);
	}

	private boolean usualSzükségesContains(String action) throws AskTheUserForInformation {
		return Szükséges == getActionType(action);
	}

	private boolean usualSzabadidőContains(String action) throws AskTheUserForInformation {
		return Szabadidő == getActionType(action);
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
				if (textContainsString(cell.toString(), action)){
					return true;
				}
			}
		}
		return false;
	}

	protected Map<String, String> makeSeparatedTimeParts(Map<String, List<Slot>> activities){
		Map<String, String> mapOfActivityTypesAndTimeStrings = new HashMap<>();
		StringBuilder finalTimeStringForActivityType;
		//Végigmegyek a tevékenység fajtákon az előre megadott típusból. Pl.: értékesbe tartozik olv., meló, stb...
		for (String activityType : activities.keySet()){
			//A végső (x + y + ... ) string lesz, magyarul a darabolt idő
			finalTimeStringForActivityType = new StringBuilder();

			//Adott tevékenységből csak egy dolog történt. Pl.: egész nap egyszer olvastam
			if (activities.get(activityType).size() == 1){
				//Egyszerűen hozzáfűzöm a time amount-ot
				finalTimeStringForActivityType
						.append(activities.get(activityType).get(0).getTimeAmount());
			}else {
				//Több esetén ezt a részleget sortoljuk.
				activities.get(activityType).sort(new Slot.SlotComparator());
				List<Slot> actualActivities = activities.get(activityType);

				//Végigmegyünk a sort-olt listán pl.: Olvasottak
				for (int i = 1; i < actualActivities.size(); i++) {
					if (actualActivities.get(i).getAction().equals(actualActivities.get(i - 1).getAction())){
						if (!finalTimeStringForActivityType.isEmpty() && finalTimeStringForActivityType.charAt(finalTimeStringForActivityType.length() - 1) == ')'){
							finalTimeStringForActivityType.setCharAt(
									finalTimeStringForActivityType.length() - 1,
									'+'
							);
							finalTimeStringForActivityType
									.append(actualActivities.get(i).getTimeAmount())
									.append(")");
						}else {
							finalTimeStringForActivityType
									.append(finalTimeStringForActivityType.isEmpty() ? "(" : "+(")
									.append(actualActivities.get(i - 1).getTimeAmount())
									.append("+")
									.append(actualActivities.get(i).getTimeAmount())
									.append(")");
						}
					}
				}
			}
			mapOfActivityTypesAndTimeStrings.put(activityType, finalTimeStringForActivityType.toString());
		}
		return mapOfActivityTypesAndTimeStrings;
	}


	private static double evaluateExpression(String expression) {
		Stack<Double> numbers = new Stack<>();
		Stack<Character> operators = new Stack<>();

		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);

			if (c == ' ')
				continue;

			if (Character.isDigit(c)) {
				StringBuilder sb = new StringBuilder();
				while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
					sb.append(expression.charAt(i++));
				}
				i--;
				numbers.push(Double.parseDouble(sb.toString()));
			} else if (c == '(') {
				operators.push(c);
			} else if (c == ')') {
				while (operators.peek() != '(') {
					numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
				}
				operators.pop(); // Pop the '('
			} else if (isOperator(c)) {
				while (!operators.empty() && precedence(operators.peek()) >= precedence(c)) {
					numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
				}
				operators.push(c);
			}
		}

		while (!operators.empty()) {
			numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
		}

		return numbers.pop();
	}

	private static boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/';
	}

	private static int precedence(char op) {
		switch (op) {
			case '+':
			case '-':
				return 1;
			case '*':
			case '/':
				return 2;
		}
		return -1;
	}

	private static double applyOperator(char op, double b, double a) {
		return switch (op) {
			case '+' -> a + b;
			case '-' -> a - b;
			case '*' -> a * b;
			case '/' -> {
				if (b == 0) throw new ArithmeticException("Division by zero!");
				yield a / b;
			}
			default -> 0;
		};
	}

}
