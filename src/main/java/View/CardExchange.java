package View;
import Model.Card;
import Model.GamePlay;
import Model.Player;

/**
 * Card exchange is a concrete observer that implements card exchange view.
 * @author jaskaransodhi
 *
 */
public class CardExchange implements IObserver {
	
    String header =          "=============================================="  + "\n" +
            			 	 "============== Card Exchange View ============";
    String footer =          "==============================================";
    

	@Override
	public void update(GamePlay gameplay, Player player) {

		if( player.playerCards.size() > 0 ) {
			
			int counter = 0;
			System.out.println(header + "\n");
			for(Card itr:player.playerCards) {
				counter++;
				System.out.println(counter + "." + itr.getCardType());
			}
			System.out.println("\n" + footer);
			
		}
		else {
			System.out.println(header + "\n" + "You do not have any card." + "\n" + footer);
		}
	}
}
