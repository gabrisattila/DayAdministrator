package Classes;

import Classes.I18N.NoSuchCellException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import static Classes.I18N.I18N.isDate;
import static Classes.I18N.I18N.toDate;

public class Main {

	public static void main(String[] args) throws IOException, NoSuchCellException {
        AdministrateDay();
    }


    public static void AdministrateDay() throws IOException, NoSuchCellException {
        System.out.println("Írja le a napját. A különböző eseményeket, adatokat ';' karakterrel válassza el. \n" +
                "Ha Entert akar ütni, mert zavarja a túl hosszú sor, de még nem végzett a nappal, nyomja meg az Enter billentyűt, de a Shift-el együtt.");
        Scanner textScanner = new Scanner(System.in);
        System.out.println("\n");

	    String dayText = textScanner.nextLine();

        Editor editor = new Editor(dayText);

        editor.modifyWorkbooks();
    }
}