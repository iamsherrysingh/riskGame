package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * This file holds data members and all the methods which are related to player.
 * This has singleton implementation.
 */
public class Player implements IPlayer {

    private String name;
    private Integer number, numberOfArmies, numberOfFreeArmies;
	private ArrayList<Integer> myCountries = new ArrayList<Integer>();
	private Integer exchangeCardsTimes;
	private ArrayList<Card> playerCards;
	private boolean countryConquered;
	private boolean defenderRemoved;
	static Integer lastDiceSelected = null;

	public Player(Integer number, String name, Integer numberOfArmies) {
		this.number = number;
		this.name = name;
		this.numberOfArmies = numberOfArmies;
		playerCards = new ArrayList<Card>();
		exchangeCardsTimes = 0;
		countryConquered = false;
		defenderRemoved = false;
	}
	
	public PlayerStrategy getPlayerStrategy() {
		return PlayerStrategy.human;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Integer getNumberOfArmies() {
		return numberOfArmies;
	}
	
	public void setNumberOfArmies(Integer numberOfArmies) {
		this.numberOfArmies = numberOfArmies;
	}

    public Integer getNumberOfFreeArmies() {
        return numberOfFreeArmies;
    }

    public void setNumberOfFreeArmies(Integer numberOfFreeArmies) {
        this.numberOfFreeArmies = numberOfFreeArmies;
    }

	public ArrayList<Integer> getMyCountries() {
		return myCountries;
	}

	public void setMyCountries(Integer number) {
		myCountries.add(number);
	}
	
	public Integer getExchangeCardsTimes() {
		return exchangeCardsTimes;
	}
	
	public void setExchangeCardsTimes(Integer exchangeCardsTimes) {
		this.exchangeCardsTimes = exchangeCardsTimes;
	}
	
	public ArrayList<Card> getPlayerCards(){
		return playerCards;
	}
	
	public void setPlayerCards(Card card) {
		playerCards.add(card);
	}
	
	public boolean getCountryConquered() {
		return countryConquered;
	}
	
	public void setCountryConquered(boolean countryConquered) {
		this.countryConquered = countryConquered;
	}
	
	public boolean getDefenderRemoved() {
		return defenderRemoved;
	}
	
	public void setDefenderRemoved(boolean defenderRemoved) {
		this.defenderRemoved = defenderRemoved;
	}

	/**
	 * This method check validation of reinforcements and do reinforce.
	 * @param countryName This is an string which specified by user for reinforcement
	 * @param numberOfArmies This is an integer parameter which specify the number of armies for reinforcement
	 * @param graphObj This is an object which pass the game graph.
	 * @param currentPlayerObj This is an object which is current player of the game.
	 * @return true(if runs successfully or false in case it fails any validation)
	 */
	public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj, CurrentPlayer currentPlayerObj) {

		// check: if target country is not exist, return false
		Country targetCountry = Country.getCountryByName(countryName, graphObj);

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

	/**
	 * This handles the command entered by the defender to defend his/her country
	 * with the number of armies he/she entered.
	 * 
	 * @param numberOfArmiesDefenderCanSelect This is the integer value that a
	 *                                        defender is allowed to select the
	 *                                        number of armies.
	 * @param scanner                         This is a scanner to take the command
	 *                                        written by the defender as an input.
	 * @return an integer value that is the number of armies selected by a defender
	 *         to defend a country.
	 */
	public static Integer defenderCommandInput(Integer numberOfArmiesDefenderCanSelect, Scanner scanner) {

		String defenderCommand = "";
		Integer numberOfArmiesSelectedByTheDefender = null;
		defenderCommand = scanner.nextLine();
		String[] defenderDiceSplit = defenderCommand.split(" ");
		if (!(defenderDiceSplit.length == 2 && defenderDiceSplit[0].trim().equals("defend")
				&& ((Integer.parseInt(defenderDiceSplit[1])) < (numberOfArmiesDefenderCanSelect + 1))
				&& ((Integer.parseInt(defenderDiceSplit[1])) > 0))) {

			System.out.println("Please write a valid command");

		} else {
			numberOfArmiesSelectedByTheDefender = Integer.parseInt(defenderDiceSplit[1].trim());
		}
		defenderCommand = "";

		if (numberOfArmiesSelectedByTheDefender != null) {
			return numberOfArmiesSelectedByTheDefender;
		} else {
			return defenderCommandInput(numberOfArmiesDefenderCanSelect, scanner);
		}
	}

	/**
	 * This method attacks until no attack is possible using maximum number of dice
	 * to attack/defend.
	 * 
	 * @param fromCountry      AttackerCountry
	 * @param toCountry        DefenderCountry
	 * @param graphObj         its graphs instance
	 * @param currentPlayerObj its current player's instance
	 * @return true(if runs successfully) or false(in case it fails any validation)
	 */
	public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj,
			CurrentPlayer currentPlayerObj) {

		Country attackerCountry = Country.getCountryByName(fromCountry, graphObj);
		Country defenderCountry = Country.getCountryByName(toCountry, graphObj);
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
					System.out.println("Attacker Armies : "
							+ Country.getCountryByName(attackerCountry.name, Graph.getInstance()).getNumberOfArmies());
					System.out.println("Defender Armies : "
							+ Country.getCountryByName(defenderCountry.name, Graph.getInstance()).getNumberOfArmies());
					battle(attackerCountry, defenderCountry, AttackerArmiesSelected, DefenderArmiesSelected, attacker, defender);
					AttackerArmiesSelected = null;
					DefenderArmiesSelected = null;
					System.out.println("After Atack");
					System.out.println("Attacker Armies : "
							+ Country.getCountryByName(attackerCountry.name, Graph.getInstance()).getNumberOfArmies());
					System.out.println("Defender Armies : "
							+ Country.getCountryByName(defenderCountry.name, Graph.getInstance()).getNumberOfArmies());
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
						System.out.println("Please enter a command to move armies to " + defenderCountry.name);
						System.out.println("Please select a number greater than or equal to " + lastDiceSelected
								+ " and less than " + attackerCountry.getNumberOfArmies());

						Integer attackMove = null;
						Scanner scanner = new Scanner(System.in);

						attackMove = attackMoveCommand(lastDiceSelected, scanner, attackerCountry.getNumberOfArmies());

						if (attackMove != null) {
							attackMove(attackerCountry, defenderCountry, attackMove);
						} else {
							System.out.println("something went wrong!!");
						}
						System.out.println("AttackAllOut is finished here.");
						// scanner.close();

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
			System.out.println("Please select the country owned by you(" + currentPlayerObj.getCurrentPlayer().getName()
					+ ") as attackerCountry");
			return false;
		}

		return true;

	}

	/**
	 * This handles the attackMove command to move the right number of armies from
	 * attacker country to defender country when attacker wins the defender country
	 * 
	 * @param numberOfArmiesDuringLastAttack This is an integer value that stores
	 *                                       number of armies used by the attacker
	 *                                       in the las battle and is used for
	 *                                       validation.
	 * @param scanner                        Its a scanner used to take a command as
	 *                                       input.
	 * @param numberOfArmiesThatAttackerHave This is an integer value that the
	 *                                       attacker have in total in attacker
	 *                                       country.
	 * @return an integer value that is the number of armies an attacker selected to
	 *         move to his new owned country.
	 */
	public static Integer attackMoveCommand(Integer numberOfArmiesDuringLastAttack, Scanner scanner,
			Integer numberOfArmiesThatAttackerHave) {
		Integer attackMoveNumber = null;
		String Command = scanner.nextLine();

		String[] AttackMoveSplit = Command.split(" ");
		if (!(AttackMoveSplit.length == 2 && AttackMoveSplit[0].trim().equals("attackmove")
				&& ((Integer.parseInt(AttackMoveSplit[1])) >= numberOfArmiesDuringLastAttack))) {

			System.out.println("Please write a valid command");

		} else {
			attackMoveNumber = Integer.parseInt(AttackMoveSplit[1].trim());
		}

		if (attackMoveNumber != null) {

			if (attackMoveNumber < numberOfArmiesDuringLastAttack
					|| attackMoveNumber >= numberOfArmiesThatAttackerHave) {
				Integer range = numberOfArmiesThatAttackerHave - 1;
				if (numberOfArmiesDuringLastAttack == range) {
					System.out.println("you can only move " + range + " armies/army" + ", please retry:");
					return attackMoveCommand(numberOfArmiesDuringLastAttack, scanner, numberOfArmiesThatAttackerHave);
				} else {
					System.out.println("you have to select number of armies between the number "
							+ numberOfArmiesDuringLastAttack + "and " + range + ", please retry:");
					return attackMoveCommand(numberOfArmiesDuringLastAttack, scanner, numberOfArmiesThatAttackerHave);
				}
			} else {
				return attackMoveNumber;
			}
		} else {
			return attackMoveCommand(numberOfArmiesDuringLastAttack, scanner, numberOfArmiesThatAttackerHave);
		}
	}

	/**
	 * This method handles the operation to remove and add the exact number of
	 * armies selected by the currentplayer(attacker) to newly owned country.
	 * 
	 * @param attackerCountry This is an object for the attacker country.
	 * @param defenderCountry This is an object for the defender country.
	 * @param numberOfArmiesToMove an integer value selected by the attacker to move between countries.
	 * @param attacker This is an object for the attacker player.
	 * @param defender This is an object for defender player.
	 * @return true(if runs successfully) or false(if fails some validation)
	 */
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

	/**
	 * This method attacks from attackerCountry to defenderCountry with number of armies selected. 
	 * 
	 * @param fromCountry This is a attackerCountry.
	 * @param toCountry This is a defenderCountry.
	 * @param numDice Its an integer value for number of armies attacker choose to attack with.
	 * @param graphObj This is the object of the Graph.
	 * @param currentPlayerObj This is the object of currentPlayer.
	 * @return true(if runs successfully) or false(in case it fails any validation)
	 */
	public boolean attackCountry(String fromCountry, String toCountry, Integer numDice, Graph graphObj,
			CurrentPlayer currentPlayerObj) {

		Country attackerCountry = Country.getCountryByName(fromCountry, graphObj);
		Country defenderCountry = Country.getCountryByName(toCountry, graphObj);
		String attackerName = attackerCountry.getOwner();
		String defenderName = defenderCountry.getOwner();
		IPlayer attacker = Database.getPlayerByName(attackerName);
		IPlayer defender = Database.getPlayerByName(defenderName);

		// Owner of attackerCountry should be same as current player
		if (attackerCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {

			// Owner of defender Country cannot be currentPlayer
			if (defenderCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {

				System.out.println("You can only attack the countries that are owned by some other player");
				return false;
			} else {

				// checks adjacency of the countries
				if (attackerCountry.neighbours.contains(defenderCountry.getNumber())) {

					// can attack keeping minimum 1 army in his own country
					if (attackerCountry.getNumberOfArmies() > 1) {

						// armies in attacker country should be more than armies selected for attack
						if (attackerCountry.getNumberOfArmies() > numDice) {

							// max armies for attack can be 3
							if (numDice < 4) {
								Integer defenderDice = 0;

								Scanner sc = new Scanner(System.in);
								if (defenderCountry.getNumberOfArmies() >= 2) {
									System.out.println(defenderCountry.getOwner()
											+ ", you can select maximum of 2 armies to defend your country");
									defenderDice = defenderCommandInput(2, sc);

								} else {
									System.out.println(defenderCountry.getOwner()
											+ ", you only have 1 army with which you can defend your country");
									defenderDice = defenderCommandInput(1, sc);
								}

								// sc.close();

								// defender can select max 2 armies
								if (defenderDice > 2) {
									System.out.println("defender entered a number greater than 2");
									return false;
								} else {

									System.out.println("Before Atack");
									System.out.println("Attacker Armies : "
											+ Country.getCountryByName(attackerCountry.name, Graph.getInstance())
													.getNumberOfArmies());
									System.out.println("Defender Armies : "
											+ Country.getCountryByName(defenderCountry.name, Graph.getInstance())
													.getNumberOfArmies());

									battle(attackerCountry, defenderCountry, numDice, defenderDice,attacker,defender);
									System.out.println("After Atack");
									System.out.println("Attacker Armies : "
											+ Country.getCountryByName(attackerCountry.name, Graph.getInstance())
													.getNumberOfArmies());
									System.out.println("Defender Armies : "
											+ Country.getCountryByName(defenderCountry.name, Graph.getInstance())
													.getNumberOfArmies());
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
										System.out.println(
												"Please enter a command to move armies to " + defenderCountry.name);
										System.out.println(
												"Please select a number greater than or equal to " + lastDiceSelected
														+ " and less than " + attackerCountry.getNumberOfArmies());

										Integer attackMove = null;
										Scanner scanner = new Scanner(System.in);
										attackMove = attackMoveCommand(lastDiceSelected, scanner,
												attackerCountry.getNumberOfArmies());

										if (attackMove != null) {
											attackMove(attackerCountry, defenderCountry, attackMove);
										} else {
											System.out.println("something went wrong!!");
										}

										// scanner.close();
										System.out.println("attackCountry command finished");
									}

								}
							} else {
								System.out.println("You can attack with atmost 3 armies");
							}
						} else if (attackerCountry.getNumberOfArmies() == numDice) {
							System.out.println("You cannot attack with all your armies");
							return false;
						} else {
							System.out.println("You selected more armies than you have in " + fromCountry);
							return false;
						}
					} else {
						System.out.println("You dont have enough number of armies to attack from " + fromCountry);
						return false;
					}
				} else {
					System.out.println("Attacker country and the defender country should be adjacent");
					return false;
				}
			}
		} else {
			System.out.println("Please select the country owned by you(" + currentPlayerObj.getCurrentPlayer().getName()
					+ ") as attackerCountry");
			return false;
		}
		return true;
	}

	
	/**
	 * This gives a random number whenever dice is rolled upto maxDice.
	 * @param maxDice its an integer value upto which we want a number.
	 * @return a random number
	 */
	public static int getRandomNumber(Integer maxDice) {
		Random randomGenerator;
		randomGenerator = new Random();
		return randomGenerator.nextInt(maxDice) + 1;
	}

	
	/**
	 * This method manages the battle between attackerCountry and defenderCountry and declares whoever wins the battle.
	 * 
	 * @param attackerCountry object of the country that attacks the other country.
	 * @param defenderCountry object of the country that is attacked.
	 * @param attackerArmies Integer value, number of armies selected by attacker to attack.
	 * @param defenderArmies Integer value, number of armies selected by defender to defend.
	 * @return true(if runs successfully)
	 */
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
					defender.setNumberOfArmies(defender.getNumberOfArmies()-1);
				} else {
					attackerArmiesKilled++;
					System.out.println("--- Defender wins the battle ---");
					attacker.setNumberOfArmies(defender.getNumberOfArmies()-1);
				}
			}
		} else {

			for (int i = 0; i < defenderArmies; i++) {

				if (attackerDices.get(i) > defenderDices.get(i)) {
					defenderArmiesKilled++;
					System.out.println("--- Attacker wins the battle ---");
					defender.setNumberOfArmies(defender.getNumberOfArmies()-1);
				} else {
					attackerArmiesKilled++;
					System.out.println("--- Defender wins the battle ---");
					attacker.setNumberOfArmies(defender.getNumberOfArmies()-1);
				}
			}
		}

		attackerCountry.setNumberOfArmies(attackerCountry.getNumberOfArmies() - attackerArmiesKilled);
		defenderCountry.setNumberOfArmies(defenderCountry.getNumberOfArmies() - defenderArmiesKilled);

		return true;
	}

	/**
	 * This allows a player to move any number of armies from his owned country to
	 * any of his owned neighbor country, leaving behind at least 1 army unit.
	 *
	 * @param fromCname The name of the country from where the armies are to be moved
	 * @param toCountryName The name of the country to which the armies are to be moved
	 * @param numberOfArmies The total number of armies to be moved
	 * @param gameGraph This is an object of the class Graph
	 * @return true(If all the conditions are satisfied and the desired country is fortified) or false(If the countries specified are absent or it does not fulfill the requirements) 
	 */
	public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {
		Country toCountry = Country.getCountryByName(toCountryName, gameGraph);
		Country fromcountry = Country.getCountryByName(fromCname, gameGraph);

		if (fromcountry == null || toCountry == null) {
			System.out.println("One or both countries do not exist");
			return false;
		} else if (!(toCountry.getOwner().equalsIgnoreCase(fromcountry.getOwner()))) {
			System.out.println("A player has to own both the countries");
			return false;
		} else if (!(toCountry.getNeighbours().contains(fromcountry.getNumber()))) {
			System.out.println("Both countries should be adjacent");
			return false;
		} else if (!(fromcountry.getNumberOfArmies() - numberOfArmies > 0)) {
			System.out.println("You must leave at least 1 army unit behind");
			return false;
		}

		ArrayList<Integer> toCountryNeighbours = toCountry.getNeighbours();

		fromcountry.setNumberOfArmies(fromcountry.getNumberOfArmies() - numberOfArmies);
		toCountry.setNumberOfArmies(toCountry.getNumberOfArmies() + numberOfArmies);

		Country.updatePlayerListAndDeclareWinner(gameGraph);

		return true;
	}
	
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
}

