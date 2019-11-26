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
	public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj, CurrentPlayer currentPlayerObj);
	public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj);
	public boolean normalAttack(String fromCountry, String toCountry, Integer numDice, Graph graphObj, CurrentPlayer currentPlayerObj);
	public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph);
	public Integer getNumberOfCountriesOwned(String playerName, Graph gameGraph);
	public Integer getTotalArmiesOwnedByPlayer(Graph gameGraph);
	
}

