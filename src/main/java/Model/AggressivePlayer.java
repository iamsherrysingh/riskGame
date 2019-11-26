package Model;

import java.util.ArrayList;

public class AggressivePlayer implements IPlayer {

    private String name;
    private Integer number, numberOfArmies, numberOfFreeArmies;
    private ArrayList<Integer> myCountries = new ArrayList<Integer>();
    private Integer exchangeCardsTimes;
    public ArrayList<Card> playerCards;
    public boolean countryConquered;
    public boolean defenderRemoved;
    static Integer lastDiceSelected = null;

    public AggressivePlayer(Integer number, String name, Integer numberOfArmies) {
        this.number = number;
        this.name = name;
        this.numberOfArmies = numberOfArmies;
        playerCards = new ArrayList<Card>();
        exchangeCardsTimes = 0;
        countryConquered = false;
        defenderRemoved = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayerStrategy getPlayerStrategy() {
        return null;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public Integer getNumberOfArmies() {
        return numberOfArmies;
    }

    @Override
    public void setNumberOfArmies(Integer numberOfArmies) {
        this.numberOfArmies = numberOfArmies;
    }

    @Override
    public Integer getNumberOfFreeArmies() {
        return numberOfFreeArmies;
    }

    @Override
    public void setMyCountries(Integer number) {

    }

    @Override
    public void setNumberOfFreeArmies(Integer numberOfFreeArmies) {
        this.numberOfFreeArmies = numberOfFreeArmies;
    }

    @Override
    public ArrayList<Integer> getMyCountries() {
        return myCountries;
    }

    public void setMyCountries(ArrayList<Integer> myCountries) {
        this.myCountries = myCountries;
    }

    @Override
    public Integer getExchangeCardsTimes() {
        return exchangeCardsTimes;
    }

    @Override
    public void setExchangeCardsTimes(Integer exchangeCardsTimes) {
        this.exchangeCardsTimes = exchangeCardsTimes;
    }

    @Override
    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    @Override
    public void setPlayerCards(Card card) {

    }

    @Override
    public boolean getCountryConquered() {
        return false;
    }

    public void setPlayerCards(ArrayList<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public boolean isCountryConquered() {
        return countryConquered;
    }

    @Override
    public void setCountryConquered(boolean countryConquered) {
        this.countryConquered = countryConquered;
    }

    @Override
    public boolean getDefenderRemoved() {
        return false;
    }

    public boolean isDefenderRemoved() {
        return defenderRemoved;
    }

    @Override
    public void setDefenderRemoved(boolean defenderRemoved) {
        this.defenderRemoved = defenderRemoved;
    }

    @Override
    public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj, CurrentPlayer currentPlayerObj) {
        
    	
    	 numberOfArmies=currentPlayerObj.getNumReinforceArmies();
		Country strongestCountry = null;

		for (Country country : graphObj.getAdjList()) {

			if(country.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {
				if(strongestCountry==null) {
					strongestCountry=country;
					//countryName=country.getName();
				}else {
					if(country.getNumberOfArmies()>strongestCountry.getNumberOfArmies()) {
						strongestCountry=country;
					}
				}
			}
		}


		// check: if target country is not exist, return false
		Country targetCountry = strongestCountry;

		if (targetCountry == null) {
			System.out.println("This country does not exist.");
			return false;
		}

		// check: if country does not belong to the currentPlayer, return false
		if (targetCountry.getOwner() != null) {
			if (targetCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName()) == false) {
				System.out.println("The country is not belong to the current player");
				return false;
			}
		}

		// check: if numberOfArmy is more than allocated army, return false
		if (numberOfArmies > currentPlayerObj.getNumReinforceArmies()) {
			System.out.println(
					"The current player can reinforce just " + currentPlayerObj.getNumReinforceArmies() + "armies");
			return false;
		}

		// Reinforce armies in the target country
		targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() + numberOfArmies);

		// increase the number of armies belong to the player
		currentPlayerObj.increaseCurrentPlayerArmies(numberOfArmies);
		currentPlayerObj.decreaseReinforceentArmies(numberOfArmies);

		return true;
    	
    }

    @Override
    public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {
       
    	
    	
    	return false;
    }

    @Override
    public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {
        return false;
    }

    @Override
    public Integer getNumberOfCountriesOwned(String playerName, Graph gameGraph) {
        return null;
    }

    @Override
    public Integer getTotalArmiesOwnedByPlayer(Graph gameGraph) {
        return null;
    }

    @Override
    public boolean attackCountry(String fromCountry, String toCountry, Integer numDice, Graph graphObj, CurrentPlayer currentPlayerObj) {
        return false;
    }

    public static Integer getLastDiceSelected() {
        return lastDiceSelected;
    }

    public static void setLastDiceSelected(Integer lastDiceSelected) {
        AggressivePlayer.lastDiceSelected = lastDiceSelected;
    }
}
