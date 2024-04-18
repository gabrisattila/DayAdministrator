package Classes.ModifyWorkBooks;

import Classes.I18N.AskTheUserForInformation;
import Classes.I18N.NoSuchCellException;
import Classes.ModifyWorkBooks.OwnFileTypes.Excel;
import Classes.Parser.DurationWithActivity;
import Classes.Parser.Slot;
import Classes.Parser.Time;
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
import static Classes.I18N.I18N.ActionTerms.getTypeOfAction;
import static Classes.ModifyWorkBooks.OwnFileTypes.Excel.*;

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
		Row todayRowInÉrtékes = getTodayRowOnASheet(excel.getSheet("Értékes"));
		Row todayRowInÉrtékes_Mi = getTodayRowOnASheet(excel.getSheet("Értékes_Mi"));
		String titleOfACell;
		String titleForAction;
		Map<String, List<Slot>> activites = new HashMap<>();
		for (Slot slot : értékes){
			titleForAction = getTitleOfAnAction(slot.getAction(), Értékes);
			if (notNull(activites .get(titleForAction))){
				activites.get(titleForAction).add(slot);
			}else {
				activites.put(titleForAction, new ArrayList<>(){{add(slot);}});
			}
			writeActionToACell(
					Objects.requireNonNull(
						getCellFromRowByTitle(
								getTitleOfAnAction(slot.getAction(), Értékes),
								todayRowInÉrtékes_Mi
						)
					),
					slot.getAction()
			);
		}

		Map<String, String> activitiesInTimeStrings = makeSeparatedTimeParts(activites);
		//A következő ciklussal töltöm fel az értékes sheet sorát
		for (int i = 0; i < rowLength(todayRowInÉrtékes); i++) {
			titleOfACell = getTitleOfACell(todayRowInÉrtékes.getCell(i));

			if (notNull(activitiesInTimeStrings.get(titleOfACell))){
			//Részletekre bontott formátum
				todayRowInÉrtékes.getCell(i + 1).setCellValue(activitiesInTimeStrings.get(titleOfACell));
			//Teljes idő
				todayRowInÉrtékes.getCell(i).setCellValue(evaluateExpression(activitiesInTimeStrings.get(titleOfACell)));
			}
		}
	}

	public void modifySzükséges() throws NoSuchCellException {
		Row todayRowInSzükséges = getTodayRowOnASheet(excel.getSheet("Szükséges"));
		Row todayRowInSzükséges_Mi = getTodayRowOnASheet(excel.getSheet("Szükséges_Mi"));
		for (Slot slot : szükséges){
			
		}
	}

	public void modifySzabadidő() throws NoSuchCellException {
		Row todayRowInSzabadidő = getTodayRowOnASheet(excel.getSheet("Szabadidő"));
		Row todayRowInSzabadidő_Mi = getTodayRowOnASheet(excel.getSheet("Szabadidő_Mi"));
		for (Slot slot : szabadidő){

		}
	}

	private void collectSlots() {
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
			addSlotToProperList(slot);
		}
		for (Slot slot : extras){
			addSlotToProperList(slot);
		}
	}

	private void addSlotToProperList(Slot slot){
		try {
			if (isÉrtékes(slot)){
				értékes.add(slot);
			} else if (isSzükséges(slot)) {
				szükséges.add(slot);
			} else if (isSzabadidő(slot)) {
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

	private Map<String, String> makeSeparatedTimeParts(Map<String, List<Slot>> activities){
		Map<String, String> mapOfActivityTypesAndTimeStrings = new HashMap<>();
		StringBuilder finalTimeStringForActivityType;
		for (String activityType : activities.keySet()){
			finalTimeStringForActivityType = new StringBuilder();
			if (activities.get(activityType).size() == 1){
				finalTimeStringForActivityType
						.append(activities.get(activityType).get(0).getTimeAmountFromTo());
			}else {
				activities.get(activityType).sort(new Slot.SlotComparator());
				List<Slot> actualActivities = activities.get(activityType);
				for (int i = 1; i < actualActivities.size(); i++) {
					if (actualActivities.get(i).getAction().equals(actualActivities.get(i - 1).getAction())){
						if (!finalTimeStringForActivityType.isEmpty() && finalTimeStringForActivityType.charAt(finalTimeStringForActivityType.length() - 1) == ')'){
							finalTimeStringForActivityType.setCharAt(
									finalTimeStringForActivityType.length() - 1,
									'+'
							);
							finalTimeStringForActivityType
									.append(actualActivities.get(i))
									.append(")");
						}else {
							finalTimeStringForActivityType
									.append("+(")
									.append(actualActivities.get(i).getTimeAmountFromTo())
									.append("+")
									.append(actualActivities.get(i - 1).getTimeAmountFromTo())
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
