package Classes.I18N;

import lombok.Getter;

import java.util.Objects;

@Getter
public class AskTheUserForInformation extends Exception{

	private final String information;

	private final String typeOfInfo;

	public AskTheUserForInformation(String info, String typeOfInfo){
		information = info;
		this.typeOfInfo = typeOfInfo;
	}

	public String handlingAndGetAnswer(){
		if ("Time".equals(typeOfInfo))
			return handlingAndGetAnswerTime();
		else
			return handlingAndGetAnswerMoney();
	}

	public String handlingAndGetAnswerTime(){
		System.out.println("Kérlek add meg, hogy " + information + " ez alatt a kifejezés alatt mit értettél?");
		System.out.println("Egészen pontosan ez egy szabadidős, értékes vagy szükséges időtöltés volt?");
		System.out.println("Kérlek válaszd ki a válaszodnak megfelelő számot.");
		System.out.println("1.\nÉrtékes.\n2.\tSzükséges.\n3.\tSzabadidő.\n");
		String válasz = System.in.toString();
		System.out.println("Köszönöm.");
		if (válasz.contains("1"))
			return "Értékes";
		else if (válasz.contains("2"))
			return "Szükséges";
		else
			return "Szabadidő";
	}

	public String handlingAndGetAnswerMoney(){
		//TODO Megírni pénz eshetőségekre is
		String válasz = System.in.toString();
		System.out.println("Köszönöm.");
		return válasz;
	}
}
