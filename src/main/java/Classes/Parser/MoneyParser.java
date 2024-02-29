package Classes.Parser;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class MoneyParser extends PartParser{

	private final Money money;

	protected MoneyParser(Parser parser) {
		super(parser);
		money = new Money();
	}

	@Override
	public void parse() {
		part = originParser.getMoney();
		String[] spent = part.split(";");
		String forWhat = null;
		int howMuch = 0;
		for (String m : spent){
			if (Character.isDigit(m.charAt(0))){
				howMuch = Integer.getInteger(m);
			}else {
				forWhat = m;
			}
			money.addSpentMoney(forWhat, howMuch);
		}
	}

}
