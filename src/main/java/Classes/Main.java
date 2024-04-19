package Classes;

import Classes.I18N.NoSuchCellException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import static Classes.I18N.I18N.isDate;
import static Classes.I18N.I18N.toDate;

public class Main {

	public static void main(String[] args) throws IOException, NoSuchCellException {
//        AdministrateDay();
		//ExcelModifier pattern = new ExcelModifier();
    }


    public static void AdministrateDay() throws IOException, NoSuchCellException {
	    Scanner textScanner = new Scanner(System.in);

	    String dayText = textScanner.nextLine();

        Editor editor = new Editor(dayText);

        editor.modifyWorkbooks();
    }
}