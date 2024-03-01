package Classes;

import Classes.ModifyWorkBooks.WorkBookModifier;

import java.io.IOException;
import java.util.Scanner;

import static Classes.Day.getDay;
import static Classes.I18N.TimeExcelFileName;
import static Classes.I18N.dataExcelsPath;

public class Main {

	public static void main(String[] args) throws IOException {
//        AdministrateDay();
		WorkBookModifier pattern = new WorkBookModifier();
    }


    public static void AdministrateDay() throws IOException {
	    Scanner textScanner = new Scanner(System.in);

	    String dayText = textScanner.nextLine();

        Editor editor = new Editor(dayText);

        editor.modifyWorkbooks();
    }
}