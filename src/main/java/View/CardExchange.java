package View;
import Model.*;

public class CardExchange implements IObserver {
	
    String header =          "=============================================="  + "\n" +
            			 	 "============== Card Exchange View ============";
    String footer =          "==============================================";
    

	@Override
	public void update(GamePlay gameplay, IPlayer player) {

		if( player.getPlayerCards().size() > 0 ) {
			
			int counter = 0;
			System.out.println(header + "\n");
			for(Card itr:player.getPlayerCards()) {
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
