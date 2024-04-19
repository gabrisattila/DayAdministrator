package Classes;

import Classes.I18N.NoSuchCellException;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, NoSuchCellException {
        AdministrateDay();
		//ExcelModifier pattern = new ExcelModifier();
    }


    public static void AdministrateDay() throws IOException, NoSuchCellException {
	    Scanner textScanner = new Scanner(System.in);

	    String dayText = textScanner.nextLine();

        Editor editor = new Editor(dayText);

        editor.modifyWorkbooks();
    }
}