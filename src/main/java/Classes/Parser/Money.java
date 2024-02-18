package Classes.Parser;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Money {

	private final Map<String, Integer> spent;

	public Money(){
		spent = new HashMap<>();
	}

	public Money(Money money){
		this.spent = money.getSpent();
	}

	public void addSpentMoney(String forWhat, int howMuch){
		spent.put(forWhat, howMuch);
	}
}
