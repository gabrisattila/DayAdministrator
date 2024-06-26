package Classes.I18N;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Getter
public class AskTheUserForInformation extends Exception{

	private final String information;

	private final String typeOfInfo;

	public AskTheUserForInformation(String info, String typeOfInfo){
		information = info;
		this.typeOfInfo = typeOfInfo;
	}

	public static String getStringAnswer() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return reader.readLine();
	}

	public static int getIntegerAnswer() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return Integer.parseInt(reader.readLine());
	}

	public static double getDoubleAnswer() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return Double.parseDouble(reader.readLine());
	}

	public String handlingAndGetAnswer() throws IOException {
		if ("Time".equals(typeOfInfo))
			return handlingAndGetAnswerTime();
		else
			return handlingAndGetAnswerMoney();
	}

	public String handlingAndGetAnswerTime() throws IOException {
		System.out.println("\nKérlek add meg, hogy " + information + " ez alatt a kifejezés alatt mit értettél?");
		System.out.println("Egészen pontosan ez egy szabadidős, értékes vagy szükséges időtöltés volt?");
		System.out.println("Kérlek válaszd ki a válaszodnak megfelelő számot.");
		System.out.println("1.\tÉrtékes.\n2.\tSzükséges.\n3.\tSzabadidő.\n");
		String válasz = getStringAnswer();
		System.out.println("\nKöszönöm.");
		if (válasz.contains("1"))
			return "Értékes";
		else if (válasz.contains("2"))
			return "Szükséges";
		else
			return "Szabadidő";
	}

	public String handlingAndGetAnswerMoney() throws IOException {
		//TODO Megírni pénz eshetőségekre is
		String válasz = getStringAnswer();
		System.out.println("Köszönöm.");
		return válasz;
	}
}
