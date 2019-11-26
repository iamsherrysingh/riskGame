package Model;

import java.util.ArrayList;

public class CheaterPlayer implements IPlayer {

	@Override
	public PlayerStrategy getPlayerStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNumber(Integer number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNumberOfArmies(Integer numberOfArmies) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getNumberOfArmies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNumberOfFreeArmies(Integer numberOfFreeArmies) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getNumberOfFreeArmies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMyCountries(Integer number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Integer> getMyCountries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getExchangeCardsTimes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExchangeCardsTimes(Integer exchangeCardsTimes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Card> getPlayerCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPlayerCards(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getCountryConquered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCountryConquered(boolean countryConquered) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getDefenderRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDefenderRemoved(boolean countryConquered) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj,
			CurrentPlayer currentPlayerObj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getNumberOfCountriesOwned(String playerName, Graph gameGraph) {
		return null;
	}

	@Override
	public Integer getTotalArmiesOwnedByPlayer(Graph gameGraph) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean attackCountry(String fromCountry, String toCountry, Integer numDice, Graph graphObj, CurrentPlayer currentPlayerObj) {
		return false;
	}

}
