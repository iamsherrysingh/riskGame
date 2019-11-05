package View;
import Model.GamePlay;

import Model.GamePlay;

public class CardExchange implements IObserver {
	
    String header=          "______________________________________________"  + "\n" +
            				"_______________Card Exchange View_____________";
    String footer=          "______________________________________________";
    
	@Override
	public void update(GamePlay gameplay) {
		System.out.println("These are your cards");
	}
}
