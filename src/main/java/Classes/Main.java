package Classes;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
        AdministrateDay();
    }


    public static void AdministrateDay() throws IOException {
	    Scanner textScanner = new Scanner(System.in);

	    String dayText = textScanner.nextLine();

        Editor editor = new Editor(dayText);

        editor.modifyWorkbooks();
    }
}