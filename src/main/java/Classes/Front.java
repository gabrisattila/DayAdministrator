package Classes;

import Classes.I18N.NoSuchCellException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.macros.Module;
import org.apache.poi.poifs.macros.VBAMacroReader;

import java.io.IOException;
import java.util.Scanner;

public class Front {

	public static void AdministrateDay() throws IOException, NoSuchCellException {
		String dayText;
						//= readInput();
		dayText =
				"2024.04.19.; 5:03;\n" +
				"610-710 reggel; 86,5 kg; 1 kávé;\n" +
				"710-810 ut. - 30 olv.; \n" +
				"810-1205 meló;" +
				"1205-25 ebéd; 1 kávé;1235-16 meló;"+
				"16-17 ut. - 25 olv. - 10 olv.;\n" +
				"17-1730 vacsi; 1 kávé;\n" +
				"18-1930 Day Administrator; 20-2130 Videó; 2130-23 írás;" +
				"10 cigi;\n";
		Editor editor = new Editor(dayText);
		editor.modifyAndSaveAfterModification();
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
