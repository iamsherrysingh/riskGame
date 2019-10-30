package Model;

import java.util.ArrayList;

enum cardType {
	Infantry,
	Cavalry,
	Artillery,
	Wild
}
public class Card {
	
	private cardType cType;
	private Integer idCard;
	private Integer owner;
	
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

class CardPlay{
	
	private static CardPlay cardPlayObj = null;
	private ArrayList<Card> cardsList;
	
	private CardPlay() {
    	cardsList = new ArrayList<Card>();
    	initilizaGamecards();
    }
	
    public static CardPlay getInstance() {
        if( cardPlayObj == null )
        	cardPlayObj = new CardPlay();
        return cardPlayObj;
    }
    
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
  
  	public Card pickCard(Integer playerId) {
  		
        double randomNumber = Math.random();
        int randomCardIndex = (int) (randomNumber * cardsList.size());
       
        Card tempCard = cardsList.get(randomCardIndex);
        tempCard.setOwner(playerId);
        cardsList.remove(randomCardIndex);
        
        return tempCard;
  	}
  	
  	public void refundCard(Card cardObj) {
  		
  		cardObj.setOwner(0);
  		cardsList.add(cardObj);
  		
  	}
}
