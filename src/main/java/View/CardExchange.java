package View;
import Model.GamePlay;

public class CardExchange implements IObserver {
	
	@Override
	public void update(GamePlay gameplay) {
		System.out.println("These are your cards");
	}
}
