package org.example.Parser;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class MoneyParser extends PartParser{

	private final ArrayList<Integer> moneyOfTheDay = new ArrayList<>();

	protected MoneyParser(Parser parser) {
		super(parser);
	}

	@Override
	public void parse() {
		part = originParser.getMoney();
		String[] money = part.split(" ");
		for (String m : money){
			if (Character.isDigit(m.charAt(0))){
				moneyOfTheDay.add(Integer.getInteger(m));
			}
		}
	}

}
