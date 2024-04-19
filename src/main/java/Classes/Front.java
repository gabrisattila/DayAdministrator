package Classes;

import Classes.I18N.NoSuchCellException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Front {

	public static void AdministrateDay() throws IOException, NoSuchCellException {
		String dayText = readInput();
		Editor editor = new Editor(dayText);
		editor.modify();
	}

	public static String readInput() {
		StringBuilder userData = new StringBuilder();
		System.out.println("Írja le a napját. A különböző eseményeket, adatokat ';' karakterrel válassza el. \n");
		Scanner input = new Scanner(System.in);
		while (true) {
			String line = input.nextLine();
			if ("Fine".equalsIgnoreCase(line) || "fine".equalsIgnoreCase(line)) {
				break;
			}
			userData.append(line);
		}
		return userData.toString().trim();
	}
}
