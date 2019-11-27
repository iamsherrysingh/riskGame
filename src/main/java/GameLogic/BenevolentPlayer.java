package GameLogic;

import java.util.ArrayList;

/**
 * This class implements the Benevolant player strategy.
 * The functionality of this class includes protecting the weakest countries, does not attack and fortification by moving armiers to the weakest countries
 */
public class BenevolentPlayer implements IPlayer {

	private String name;
	private Integer number, numberOfArmies, numberOfFreeArmies;
	private ArrayList<Integer> myCountries = new ArrayList<Integer>();
	private Integer exchangeCardsTimes;
	public ArrayList<Card> playerCards;
	public boolean countryConquered;
	public boolean defenderRemoved;
	static Integer lastDiceSelected = null;

	public void setMyCountries(ArrayList<Integer> myCountries) {
		this.myCountries = myCountries;
	}

	/**
	 * This is the constructor of the class Benevolant player
	 * @param number It is an integer number corresponding the number of the player
	 * @param name It is a String containing the name of the player
	 * @param numberOfArmies It is the integer number of the armies
	 */
	public BenevolentPlayer(Integer number, String name, Integer numberOfArmies) {
		this.number = number;
		this.name = name;
		this.numberOfArmies = numberOfArmies;
		playerCards = new ArrayList<Card>();
		exchangeCardsTimes = 0;
		countryConquered = false;
		defenderRemoved = false;
	}

	public boolean reinforcement(Graph graphObj, CurrentPlayer currentPlayerObj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public PlayerStrategy getPlayerStrategy() {
		return PlayerStrategy.benevolent; // Done
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
		myCountries.add(number);
	}

	@Override
	public void setNumberOfFreeArmies(Integer numberOfFreeArmies) {
		this.numberOfFreeArmies = numberOfFreeArmies;
	}

	@Override
	public ArrayList<Integer> getMyCountries() {
		return myCountries;
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
		playerCards.add(card);
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
    /**
     * This method is responsible to check validations of reinforcement and perform tasks associated with it
     * @param countryName This is n string which specified by user for reinforcement
     * @param numberOfArmies This is an integer parameter which specify the number of armies for reinforcement
     * @param graphObj This is an object which pass the game graph.
     * @param currentPlayerObj This is an object which is current player of the game.
     * @return true(if the method executes succesffully) or false(if the country entered does not exist or does not belong to the current player)
     */
	public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj,
			CurrentPlayer currentPlayerObj) {

		numberOfArmies = currentPlayerObj.getNumReinforceArmies();
		Country weakestCountry = null;

		for (Country country : graphObj.getAdjList()) {

			if (country.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {
				if (weakestCountry == null) {
					weakestCountry = country;
					// countryName=country.getName();
				} else {
					if (country.getNumberOfArmies() < weakestCountry.getNumberOfArmies()) {
						weakestCountry = country;
					}
				}
			}
		}

		// check: if target country is not existent, return false
		Country targetCountry = weakestCountry;

		if (targetCountry == null) {
			System.out.println("This country does not exist.");
			return false;
		}

		// check: if country does not belong to the currentPlayer, return false
		if (targetCountry.getOwner() != null) {
			if (targetCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName()) == false) {
				System.out.println("The country does not belong to the current player");
				return false;
			}
		}

		// Reinforce armies in the target country
		targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() + numberOfArmies);

		// increase the number of armies belong to the player
		currentPlayerObj.increaseCurrentPlayerArmies(numberOfArmies);
		currentPlayerObj.decreaseReinforceentArmies(numberOfArmies);

		GamePlay.getInstance().setCurrentOperation(
				"Country " + targetCountry.name + " reinforced with " + numberOfArmies + " armies.");
		return true;
	}

	@Override

	public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {
		// Do nothing
		GamePlay.getInstance().setCurrentOperation("Benevolent player doesnot perform any attack");
		return true;
	}

	@Override
	/**
	 *This method allows the user to move their armies to weaker countries
	 * 	@param fromCname The name of the country from where the armies are to be moved
	 * 	@param toCountryName The name of the country to which the armies are to be moved
	 *  @param numberOfArmies The total number of armies to be moved
	 *  @param gameGraph This is an object of the class Graph
	 *  @return true(If all the conditions are satisfied and the desired country is fortified) or false(If the countries specified are absent or it does not fulfill the requirements)
	 */
	public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {

		Country fromCountry = null;
		Country toCountry = null;

		ArrayList<Country> CountriesOwnedByCurrentPlayer = new ArrayList<Country>();
		for (Country country : gameGraph.getAdjList()) {

			if (country.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName())) {
				CountriesOwnedByCurrentPlayer.add(country);
				if ((toCountry == null)) {
					toCountry = country;
				} else if ((toCountry.getNumberOfArmies() > country.getNumberOfArmies())) {
					toCountry = country; // weakest country
				}
			}
		}

		CountriesOwnedByCurrentPlayer.remove(toCountry);

		for (Country country : gameGraph.getAdjList()) {

			if ((!country.equals(toCountry)) && fromCountry == null
					&& (Mapx.checkPath(country.name, toCountry.name, gameGraph) == true)) {
				fromCountry = country;
			} else if (fromCountry != null) {
				if ((!country.equals(toCountry)) && (country.getNumberOfArmies() > fromCountry.getNumberOfArmies())
						&& (Mapx.checkPath(country.name, toCountry.name, gameGraph) == true)) {
					fromCountry = country;
				}
			}
		}

		Integer AvgNumberOfArmies = (toCountry.getNumberOfArmies() + fromCountry.getNumberOfArmies()) / 2;

		numberOfArmies = AvgNumberOfArmies - toCountry.numberOfArmies;

		if (fromCountry == null || toCountry == null) {
			System.out.println("One or both countries do not exist");
			return false;
		} else if (!(toCountry.getOwner().equalsIgnoreCase(fromCountry.getOwner()))) {
			System.out.println("A player has to own both the countries");
			return false;
		} else if (!(Mapx.checkPath(toCountry.name, fromCountry.name, gameGraph))) {
			System.out.println("There should be the two countries.\n Current Player should own the path.");
			return false;
		} else if (!(fromCountry.getNumberOfArmies() - numberOfArmies > 0)) {
			System.out.println("You must leave at least 1 army unit behind");
			return false;
		}

		ArrayList<Integer> toCountryNeighbours = toCountry.getNeighbours();

		fromCountry.setNumberOfArmies(fromCountry.getNumberOfArmies() - numberOfArmies);
		toCountry.setNumberOfArmies(toCountry.getNumberOfArmies() + numberOfArmies);

		Country.updatePlayerListAndDeclareWinner(gameGraph);

		return true;
	}

	@Override
	/**
	 * This method returns the total number of countries owned by the players.
	 * 
	 * @param playerName The name of the player
	 * @param gameGraph  This is an object of the class Graph
	 * @return An integer value that is equal to the total number of countries owned
	 *         by the player
	 */
	public Integer getNumberOfCountriesOwned(String playerName, Graph gameGraph) {
		Integer numberOfCountriesOwned = 0;

		if (Database.getPlayerByName(playerName) == null)
			return -1;
		for (Country country : gameGraph.getAdjList()) {
			if (country.owner.equalsIgnoreCase(playerName)) {
				numberOfCountriesOwned += 1;
			}
		}
		return numberOfCountriesOwned;
	}

	@Override

	/**
	 * This method returns the total number of armies owned by the players.
	 * 
	 * @param gameGraph It is an object of the class Graph
	 * @returnAn integer value that is equal to the total number of armies owned by
	 *           the player
	 */
	public Integer getTotalArmiesOwnedByPlayer(Graph gameGraph) {
		Integer numberOfArmies = 0;

		if (Database.getPlayerByName(this.name) == null)
			return -1;
		for (Country country : gameGraph.getAdjList()) {
			if (country.owner.equalsIgnoreCase(this.name)) {
				numberOfArmies += country.numberOfArmies;
			}
		}
		return numberOfArmies;
	}

	@Override
	public boolean normalAttack(String fromCountry, String toCountry, Integer numDice, Graph graphObj,
			CurrentPlayer currentPlayerObj) {
		return false;
	}

	public static Integer getLastDiceSelected() {
		return lastDiceSelected;
	}

	public static void setLastDiceSelected(Integer lastDiceSelected) {
		BenevolentPlayer.lastDiceSelected = lastDiceSelected;
	}
}
