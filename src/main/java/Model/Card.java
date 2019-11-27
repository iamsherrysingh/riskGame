package Model;

import java.util.ArrayList;

enum cardType {
	Infantry,
	Cavalry,
	Artillery,
	Wild
}
/** 
 * This Class maintains data for each cards.
 * The methods of this class are called by GamePlay.
 * @author Mehrnoosh Amjadi
 */
public class Card {
	
	private cardType cType;
	private Integer idCard;
	private Integer owner;
	
	/** 
	 * Constructor of the Card Class.
	 * @param cType is the type for each card
	 * @param idCard is the card id which is unique.
	 * @param owner it holds the owner of the card.
	 */
	public Card(cardType cType, Integer idCard, Integer owner) {
		this.cType = cType;
		this.idCard = idCard;
		this.owner = owner;
	}
	
	public void setCardType(cardType cType) {
		this.cType = cType;
	}
	
	public cardType getCardType() {
		return cType;
	}
	
	public void setIdCard(Integer idCard) {
		this.idCard = idCard;
	}
	
	public Integer getIdCard() {
		return idCard;
	}
	
	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	
	public Integer getOwner() {
		return owner;
	}
}

/** 
 * This Class maintains the list of all the cards in the game. and initialize them
 * The methods of this class are called by GamePlay.
 * This class is implemented with Singleton Design pattern.
 * @author Mehrnoosh Amjadi
 */
class CardPlay{
	
	private static CardPlay cardPlayObj = null;
	private static ArrayList<Card> cardsList;
	
	/** 
	 * Constructor of the CardPlay Class.
	 */
	private CardPlay() {
    	cardsList = new ArrayList<Card>();
    	initilizaGamecards();
    }
	
    public static CardPlay getInstance() {
        if( cardPlayObj == null )
        	cardPlayObj = new CardPlay();
        return cardPlayObj;
    }
    
	public static ArrayList<Card> getCardsList(){
		return cardsList;
	}
	
  
	/** 
	 * This method Initialize all the cards in the game: 
	 * 14 for Infantry, 14 for Cavalry, 14 Artillery and 2 wild cards.  
	 */
    private void initilizaGamecards() {
    	
    	Card cardObj;
    	
    	for(int i=0; i < 14; i++) {	
    		cardObj = new Card(cardType.Infantry,i+1,0);
    		cardsList.add(cardObj);
    	}
    	
    	for(int i=0; i < 14; i++) {	
    		cardObj = new Card(cardType.Cavalry,i+15,0);
    		cardsList.add(cardObj);
    	}
    	
    	for(int i=0; i < 14; i++) {	
    		cardObj = new Card(cardType.Artillery,i+29,0);
    		cardsList.add(cardObj);
    	}
    	
    	for(int i=0; i < 2; i++) {	
    		cardObj = new Card(cardType.Wild,i+43,0);
    		cardsList.add(cardObj);
    	}
    }
  
	/** 
	 * PickCard method is called whenever a player conquered a country 
	 * @param playerId by this parameter, we can set the owner of the card.
	 * @return it returns an object of Card.
	 */
  	public Card pickCard(Integer playerId) {
  		
        double randomNumber = Math.random();
        int randomCardIndex = (int) (randomNumber * cardsList.size());
       
        Card tempCard = cardsList.get(randomCardIndex);
        tempCard.setOwner(playerId);
        cardsList.remove(randomCardIndex);
        
        return tempCard;
  	}
  	
	/** 
	 * refundCard method is called a player wants to exchange cards so the cards should back to stock.
	 * @param cardObj by this parameter, we can reset the data and back the card.
	 */
  	public void refundCard(Card cardObj) {
  		
  		cardObj.setOwner(0);
  		cardsList.add(cardObj);
  		
  	}
  	
	/** 
	 * This method check the validation of 3 cards requested for exchange.
	 * @param cardObj1 This refers to first card.
	 * @param cardObj2 This refers to second card.
	 * @param cardObj3 This refers to third card.
	 * @return true if the cards are based on risk game rule for exchange or return false whenever exchange cards is invalid.
	 */
  	public boolean checkExchangeCardsValidation(Card cardObj1, Card cardObj2, Card cardObj3) {
  		
  		if( cardObj1.getCardType() == cardType.Wild || 
  			cardObj2.getCardType() == cardType.Wild || 
  			cardObj3.getCardType() == cardType.Wild )
  			return true;
  		
  		if( cardObj1.getCardType() == cardObj2.getCardType()) {
  			if( cardObj2.getCardType() == cardObj3.getCardType() )
  				return true;
  		}
  		else
  			if( cardObj1.getCardType() != cardObj3.getCardType() && cardObj2.getCardType() != cardObj3.getCardType())
  				return true;
  		
  		return false;
  	}
  	
}
