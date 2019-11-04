package View;

public class CardExchange implements IObserver {
	
	@Override
	public void update(GamePlay gameplayobj) {
		System.out.println("These are your cards");
	}
}
