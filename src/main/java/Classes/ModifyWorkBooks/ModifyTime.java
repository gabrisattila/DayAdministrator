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
import static Classes.I18N.AskTheUserForInformation.getStringAnswer;
import static Classes.I18N.I18N.*;
import static Classes.I18N.I18N.ActionTerms.actionType.*;
import static Classes.I18N.I18N.ActionTerms.getTitleOfAnAction;
import static Classes.OwnFileTypes.Excel.*;
import static Classes.Parser.Action.previouslyContains;
import static java.util.Objects.isNull;


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


	protected void modifyÉrtékes() throws NoSuchCellException, IOException {
		modifyTimeExcelSheetsOfAnActionType(értékes, Értékes);
	}

	protected void modifySzükséges() throws NoSuchCellException, IOException {
		modifyTimeExcelSheetsOfAnActionType(szükséges, Szükséges);
	}

	protected void modifySzabadidő() throws NoSuchCellException, IOException {
		modifyTimeExcelSheetsOfAnActionType(szabadidő, Szabadidő);
	}

	private void modifyTimeExcelSheetsOfAnActionType(List<Slot> slots, ActionTerms.actionType actionType) throws NoSuchCellException, IOException {
		Row todayRowConcreteTimeSheet = getTodayRowOnASheet(excel.getSheet(actionType.toString()));
		Row todayRowActionStringSheet = getTodayRowOnASheet(excel.getSheet(actionType + "_Mi"));

		Map<String, List<Slot>> activities = sortSlotsByActionType(slots, actionType);

		writeActionStringsToRow(slots, actionType, todayRowActionStringSheet);

		Map<String, String> activitiesInSpearatedTimeStrings = makeSeparatedTimeParts(activities);

		writeTimesToRow(activitiesInSpearatedTimeStrings, todayRowConcreteTimeSheet);
	}

	protected Map<String, List<Slot>> sortSlotsByActionType(List<Slot> slots, ActionTerms.actionType typeOfSlots) throws IOException {
		Map<String, List<Slot>> activities = new HashMap<>();
		String titleOfAction;
		for (Slot slot : slots){
			titleOfAction = getTitleOfAnAction(slot.getActionDescriptor(), typeOfSlots);
			if (notNull(activities.get(titleOfAction))){
				activities.get(titleOfAction).add(slot);
			}else {
				activities.put(titleOfAction, new ArrayList<>(){{add(slot);}});
			}
		}
		return activities;
	}

	private void writeActionStringsToRow(List<Slot> slots, ActionTerms.actionType typeOfSlots, Row todayMiRow) throws IOException {
		for (Slot slot : slots){
			writeActionToACell(
					excel.getCellFromRowByTitle(
							getTitleOfAnAction(slot.getActionDescriptor(), typeOfSlots),
							todayMiRow
					),
					slot.getActionString()
			);
		}
	}

	private void writeTimesToRow(Map<String, String> activitiesInTimeStrings, Row rowToWrite) {
		String titleOfIthCell;
		String titleOfIPlus1thCell;
		String separatedTimeString;
		for (int i = 0; i < rowLength(rowToWrite); i++) {
			titleOfIthCell = trimPartAfterTheFirstParentheses(excel.getTitleOfACell(rowToWrite.getCell(i)));
			titleOfIPlus1thCell = trimPartAfterTheFirstParentheses(excel.getTitleOfACell(rowToWrite.getCell(i + 1)));
			separatedTimeString = activitiesInTimeStrings.get(titleOfIthCell);

			if (notNull(separatedTimeString)){

				if (isNull(rowToWrite.getCell(i)))
					rowToWrite.createCell(i);

				if (isNull(rowToWrite.getCell(i + 1)))
					rowToWrite.createCell(i + 1);

				//Amennyiben egy olyan cellában állunk melynek van címe és a rákövekező cellának ugyanúgy -> tehát nincs felsorolva szeparálva az idő
				if (!titleOfIthCell.isEmpty() && !titleOfIPlus1thCell.isEmpty())
				{
					rowToWrite.getCell(i).setCellValue(evaluateExpression(separatedTimeString));
				}
				//Itt az utazással kapcsolatos adatokat írjuk a cellákba
				if (textContainsString(titleOfIthCell, "utazás")) {
					//TODO Megcsinálni az utazás esetén, hogy írom az cellákat.
					throw new NotImplementedException("Utazás cella szerkesztése kimaradt, a beleírni kívánt érték: " + separatedTimeString);
				}
				//Ez esetben pedig a következő cellába olyan érték kerül ami szeparált time formátumot tartalmaz.
				if (!textContainsString(titleOfIthCell, "utazás") &&
						!(!titleOfIthCell.isEmpty() && !titleOfIPlus1thCell.isEmpty())){
					//Teljes idő
					rowToWrite.getCell(i).setCellValue(evaluateExpression(separatedTimeString));

					//Részletekre bontott formátum
					rowToWrite.getCell(i + 1).setCellValue(activitiesInTimeStrings.get(titleOfIthCell));
				}
			}
		}
	}


	private boolean isÉrtékes(Slot slot) throws NoSuchCellException, AskTheUserForInformation, IOException {
		String action = slot.getActionDescriptor();
		return usualÉrtékesContains(action) || previusÉrtékesContains(action);
	}

	protected boolean isÉrtékesForTest(Slot slot) throws AskTheUserForInformation, IOException {
		return usualÉrtékesContains(slot.getActionDescriptor());
	}

	private boolean isSzükséges(Slot slot) throws NoSuchCellException, AskTheUserForInformation, IOException {
		String action = slot.getActionDescriptor();
		return usualSzükségesContains(action) || previousSzükségesContains(action);
	}

	protected boolean isSzükségesForTest(Slot slot) throws AskTheUserForInformation, IOException {
		return usualSzükségesContains(slot.getActionDescriptor());
	}

	private boolean isSzabadidő(Slot slot) throws NoSuchCellException, AskTheUserForInformation, IOException {
		String action = slot.getActionDescriptor();
		return usualSzabadidőContains(action) || previousSzabadidőContains(action);
	}

	protected boolean isSzabadidőForTest(Slot slot) throws AskTheUserForInformation, IOException {
		return usualSzabadidőContains(slot.getActionDescriptor());
	}

	private boolean usualÉrtékesContains(String action) {
		return Értékes == getActionType(action);
	}

	private boolean usualSzükségesContains(String action) {
		return Szükséges == getActionType(action);
	}

	private boolean usualSzabadidőContains(String action) {
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


	protected Map<String, String> makeSeparatedTimeParts(Map<String, List<Slot>> activities){
		Map<String, String> mapOfActivityTypesAndTimeStrings = new HashMap<>();
		StringBuilder finalTimeStringForActivityType = new StringBuilder();
		//Végigmegyek a tevékenység fajtákon az előre megadott típusból. Pl.: értékesbe tartozik olv., meló, stb...

		boolean finalIsEmptyYet;
		boolean sameActionsInRow;
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
					finalIsEmptyYet = finalTimeStringForActivityType.isEmpty();
					sameActionsInRow = actualActivities.get(i).getAction().equals(actualActivities.get(i - 1).getAction());

					if (finalIsEmptyYet) {
						finalTimeStringForActivityType
								.append("(")
								.append(actualActivities.get(i - 1).getTimeAmount())
								.append(sameActionsInRow ? "+" : ") + (")
								.append(actualActivities.get(i).getTimeAmount())
								.append(")");
					} else {
						if (sameActionsInRow){
							finalTimeStringForActivityType.setCharAt(
									finalTimeStringForActivityType.length() - 1,
									'+'
							);
						}else {
							finalTimeStringForActivityType
									.append(" + (");
						}
						finalTimeStringForActivityType
								.append(actualActivities.get(i).getTimeAmount())
								.append(")");
					}
				}
			}
			mapOfActivityTypesAndTimeStrings.put(activityType, finalTimeStringForActivityType.toString().trim());
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
		return switch (op) {
			case '+', '-' -> 1;
			case '*', '/' -> 2;
			default -> -1;
		};
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
