package Model;

import java.util.ArrayList;

public interface IPlayer {
	
	public PlayerStrategy getPlayerStrategy();
	public void setName(String name);
	public String getName();
	public void setNumber(Integer number);
	public Integer getNumber();
	public void setNumberOfArmies(Integer numberOfArmies);
	public Integer getNumberOfArmies();
	public void setNumberOfFreeArmies(Integer numberOfFreeArmies);
	public Integer getNumberOfFreeArmies();
	public void setMyCountries(Integer number);
	public ArrayList<Integer> getMyCountries();
	public Integer getExchangeCardsTimes();
	public void setExchangeCardsTimes(Integer exchangeCardsTimes);
	public ArrayList<Card> getPlayerCards();
	public void setPlayerCards(Card card);
	public boolean getCountryConquered();
	public void setCountryConquered(boolean countryConquered);
	public boolean getDefenderRemoved();
	public void setDefenderRemoved(boolean countryConquered);
	
}

