package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AggressivePlayer implements IPlayer {

	private String name;
	private Integer number, numberOfArmies, numberOfFreeArmies;
	private ArrayList<Integer> myCountries = new ArrayList<Integer>();
	private Integer exchangeCardsTimes;
	public ArrayList<Card> playerCards;
	public boolean countryConquered;
	public boolean defenderRemoved;
	static Integer lastDiceSelected = null;
	Country strongestCountryFound = null;

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
		return PlayerStrategy.aggressive;
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
	public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj,
			CurrentPlayer currentPlayerObj) {

		numberOfArmies = currentPlayerObj.getNumReinforceArmies();
		Country strongestCountry = null;

		for (Country country : graphObj.getAdjList()) {

			if (country.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {
				if (strongestCountry == null) {
					strongestCountry = country;
					// countryName=country.getName();
				} else {
					if (country.getNumberOfArmies() > strongestCountry.getNumberOfArmies()) {
						strongestCountry = country;
					}
				}
			}
		}

		strongestCountryFound = strongestCountry; // used in other methods also
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

		GamePlay.getInstance().setCurrentOperation(
				"Country " + targetCountry.name + " reinforced with " + numberOfArmies + " armies.");
		return true;

	}

	@Override
	public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {

		Country attackerCountry = strongestCountryFound;
		Country defenderCountry = null;
		Integer weakestCountryNumber = null;

		if (attackerCountry.neighbours.size() != 0) {

			for (int i = 0; i < attackerCountry.neighbours.size(); i++) {

				Integer countryNumber = attackerCountry.neighbours.get(i);

				if (weakestCountryNumber == null) {
					weakestCountryNumber = countryNumber;
				} else if (Country.getCountryByNumber(weakestCountryNumber, graphObj).getNumberOfArmies() > Country
						.getCountryByNumber(countryNumber, graphObj).getNumberOfArmies()) {
					weakestCountryNumber = countryNumber;
				}

			}

			defenderCountry = Country.getCountryByNumber(weakestCountryNumber, graphObj);

			String attackerName = attackerCountry.getOwner();
			String defenderName = defenderCountry.getOwner();
			IPlayer attacker = Database.getPlayerByName(attackerName);
			IPlayer defender = Database.getPlayerByName(defenderName);

			if (attackerCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {

				if (defenderCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {

					System.out.println("You can only attack the countries that are owned by some other player");
					return false;
				} else {

					if (attackerCountry.neighbours.contains(defenderCountry.getNumber())) {

						Integer AttackerArmiesSelected = null;
						Integer DefenderArmiesSelected = null;

						if (attackerCountry.getNumberOfArmies() > 3) {
							// armies selected 3
							AttackerArmiesSelected = 3;
						} else if (attackerCountry.getNumberOfArmies() == 3) {
							// armies selected 2
							AttackerArmiesSelected = 2;
						} else if (attackerCountry.getNumberOfArmies() == 2) {
							// armies selected 1
							AttackerArmiesSelected = 1;
						} else {
							System.out.println("Cannot attack anymore " + fromCountry + ", has only 1 army left!");
						}

						if (defenderCountry.getNumberOfArmies() >= 2) {
							// armies selected 2
							DefenderArmiesSelected = 2;
						} else if (defenderCountry.getNumberOfArmies() == 1) {
							// armies selected 1
							DefenderArmiesSelected = 1;
						} else {
							System.out.println("No more armies to defend the country");
						}

						System.out.println("Before Atack");
						System.out.println("Attacker Armies : " + Country
								.getCountryByName(attackerCountry.name, Graph.getInstance()).getNumberOfArmies());
						System.out.println("Defender Armies : " + Country
								.getCountryByName(defenderCountry.name, Graph.getInstance()).getNumberOfArmies());
						battle(attackerCountry, defenderCountry, AttackerArmiesSelected, DefenderArmiesSelected,
								attacker, defender);
						AttackerArmiesSelected = null;
						DefenderArmiesSelected = null;
						System.out.println("After Atack");
						System.out.println("Attacker Armies : " + Country
								.getCountryByName(attackerCountry.name, Graph.getInstance()).getNumberOfArmies());
						System.out.println("Defender Armies : " + Country
								.getCountryByName(defenderCountry.name, Graph.getInstance()).getNumberOfArmies());
						System.out.println("--------------------------------");

						if (defenderCountry.getNumberOfArmies() == 0) {
							System.out.println("Attacker won the country " + defenderCountry.name);
							attacker.setMyCountries(defenderCountry.getNumber());
							defenderCountry.setOwner(attackerCountry.getOwner());
							defender.getMyCountries().remove(defenderCountry.getNumber());
							setCountryConquered(true);
							if (defender.getMyCountries().size() == 0) {
								defenderRemoved = true;
							}
							// System.out.println("Please enter a command to move armies to " +
							// defenderCountry.name);
							// System.out.println("Please select a number greater than or equal to " +
							// lastDiceSelected
							// + " and less than " + attackerCountry.getNumberOfArmies());

							Integer attackMove = null;

							attackMove = lastDiceSelected;

							if (attackMove != null) {
								attackMove(attackerCountry, defenderCountry, attackMove);
							} else {
								System.out.println("something went wrong!!");
							}
							System.out.println("AttackAllOut is finished here.");

						} else if (attackerCountry.getNumberOfArmies() == 1) {
							System.out.println("no attack anymore possible!!!");
						} else {
							attackAllout(fromCountry, toCountry, graphObj, currentPlayerObj);
						}

					} else {
						System.out.println("Attacker country and the defender country should be adjacent");
						return false;
					}
				}
			} else {
				System.out.println("Please select the country owned by you("
						+ currentPlayerObj.getCurrentPlayer().getName() + ") as attackerCountry");
				return false;
			}

			GamePlay.getInstance().setCurrentOperation(
					"Performing all-out attack from " + attackerCountry.name + " to " + defenderCountry.name);
			
			// if defender lost all of his country, attacker will owned all of his cards.
			if ( getDefenderRemoved() == true) {

				for (Card itr : defender.getPlayerCards()) {
					Card tempcard = itr;
					tempcard.setOwner(currentPlayerObj.getCurrentPlayer().getNumber());
					currentPlayerObj.getCurrentPlayer().setPlayerCards(tempcard);
				}
				Database.removePlayer(defenderName);
				setDefenderRemoved(false);
			}
			return true;
		} else {
			System.out.println("All the neighbouring countries are owned by the current player");
			return false;
		}

	}

	public static boolean attackMove(Country attackerCountry, Country defenderCountry, Integer numberOfArmiesToMove) {

		if (defenderCountry.getNumberOfArmies() == 0) {

			if (attackerCountry.getNumberOfArmies() > numberOfArmiesToMove) {

				defenderCountry.setNumberOfArmies(numberOfArmiesToMove);
				attackerCountry.setNumberOfArmies(attackerCountry.getNumberOfArmies() - numberOfArmiesToMove);

			} else {

				System.out.println("you selected a greater number than you are allowed to move from attacker country");
				return false;
			}

		} else {
			System.out.println("something went wrong!!");
			return false;
		}
		return true;
	}

	public static int getRandomNumber(Integer maxDice) {
		Random randomGenerator;
		randomGenerator = new Random();
		return randomGenerator.nextInt(maxDice) + 1;
	}

	public static boolean battle(Country attackerCountry, Country defenderCountry, Integer attackerArmies,
			Integer defenderArmies, IPlayer attacker, IPlayer defender) {

		lastDiceSelected = attackerArmies;
		Integer index = 0;
		Integer defenderArmiesKilled = 0;
		Integer attackerArmiesKilled = 0;
		ArrayList<Integer> defenderDices = new ArrayList<Integer>();
		ArrayList<Integer> attackerDices = new ArrayList<Integer>();

		for (int i = 0; i < attackerArmies; i++) {
			index = getRandomNumber(6);
			attackerDices.add(index);
		}
		for (int i = 0; i < defenderArmies; i++) {
			index = getRandomNumber(6);
			defenderDices.add(index);
		}
		Collections.sort(attackerDices);
		Collections.sort(defenderDices);
		Collections.reverse(attackerDices);
		Collections.reverse(defenderDices);

		if (defenderArmies > attackerArmies) {
			for (int i = 0; i < attackerArmies; i++) {
				if (attackerDices.get(i) > defenderDices.get(i)) {
					defenderArmiesKilled++;
					System.out.println("--- Attacker wins the battle ---");
					defender.setNumberOfArmies(defender.getNumberOfArmies() - 1);
				} else {
					attackerArmiesKilled++;
					System.out.println("--- Defender wins the battle ---");
					attacker.setNumberOfArmies(defender.getNumberOfArmies() - 1);
				}
			}
		} else {

			for (int i = 0; i < defenderArmies; i++) {

				if (attackerDices.get(i) > defenderDices.get(i)) {
					defenderArmiesKilled++;
					System.out.println("--- Attacker wins the battle ---");
					defender.setNumberOfArmies(defender.getNumberOfArmies() - 1);
				} else {
					attackerArmiesKilled++;
					System.out.println("--- Defender wins the battle ---");
					attacker.setNumberOfArmies(defender.getNumberOfArmies() - 1);
				}
			}
		}

		attackerCountry.setNumberOfArmies(attackerCountry.getNumberOfArmies() - attackerArmiesKilled);
		defenderCountry.setNumberOfArmies(defenderCountry.getNumberOfArmies() - defenderArmiesKilled);

		return true;
	}

	@Override
	public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {
		return false;
	}

    @Override
    /**
     * This method returns the total number of countries owned by the players.
     * @param playerName The name of the player
     * @param gameGraph This is an object of the class Graph
     * @return An integer value that is equal to the total number of countries owned by the player
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
     * @param gameGraph It is an object of the class Graph
     * @returnAn integer value that is equal to the total number of armies owned by the player
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
    public boolean normalAttack(String fromCountry, String toCountry, Integer numDice, Graph graphObj, CurrentPlayer currentPlayerObj) {
        return false;
    }

	public static Integer getLastDiceSelected() {
		return lastDiceSelected;
	}

	public static void setLastDiceSelected(Integer lastDiceSelected) {
		AggressivePlayer.lastDiceSelected = lastDiceSelected;
	}

}
