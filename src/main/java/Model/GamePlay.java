package Model;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This Class maintains the state of the game and current player. The methods of
 * this class are called by Controller. This class has singleton implementation.
 */
public class GamePlay implements ISubject {

	/**
	 * This file holds most of the utility functions that call other methods for
	 * implementation in gamePlay mode
	 */

	private static GamePlay gamePlay = null;
	private State currentState;
	private Mapx mapxObj;
	private Graph graphObj;
	private Database databaseObj;
	private CardPlay cardPlayObj;
	private CurrentPlayer currentPlayerObj;
	Integer lastDiceSelected = null;

	public CurrentPlayer getCurrentPlayerObj() {
		return currentPlayerObj;
	}

	private GamePlay() {
		currentState = State.initializeGame;
		mapxObj = new Mapx();
		databaseObj = Database.getInstance();
		currentPlayerObj = CurrentPlayer.getInstance();
		graphObj = Graph.getInstance();
		cardPlayObj = CardPlay.getInstance();
	}

	public static GamePlay getInstance() {
		if (gamePlay == null)
			gamePlay = new GamePlay();
		return gamePlay;
	}

	public Graph getGraphObj() {
		return graphObj;
	}

	public Mapx getMapxObj() {
		return mapxObj;
	}

	public State getCurrentState() {
		return currentState;
	}

	/**
	 * Set Current State of the game
	 * 
	 * @param newState
	 * @param newStateStr
	 */
	private void setCurrentState(State newState, String newStateStr) {
		System.out.println("<== State of game changed to: " + newStateStr + " ==>");
		currentState = newState;
	}

	/**
	 * Add Continent Function
	 * 
	 * @param continentName
	 * @param controlValue
	 * @return
	 */
	public boolean addContinent(String continentName, Integer controlValue) {

		if (!Continent.addContinent(continentName, controlValue))
			return false;

		return true;
	}

	/**
	 * Remove Continent Function
	 * 
	 * @param continentName
	 * @return
	 */
	public boolean removeContinent(String continentName) {

		if (!Continent.removeContinent(continentName, graphObj))
			return false;

		return true;
	}

	/**
	 * Add Country Function
	 * 
	 * @param countryName
	 * @param continentName
	 * @return
	 */
	public boolean addCountry(String countryName, String continentName) {

		if (!Country.addCountry(countryName, continentName, graphObj))
			return false;

		return true;
	}

	/**
	 * Remove Country Function
	 * 
	 * @param countryName
	 * @return
	 */
	public boolean removeCountry(String countryName) {

		if (!Country.removeCountry(countryName, graphObj))
			return false;

		return true;
	}

	/**
	 * Add Neighbor Function
	 * 
	 * @param countryName
	 * @param neighborCountryName
	 * @return
	 */
	public boolean addNeighbor(String countryName, String neighborCountryName) {

		if (!Country.addNeighbour(countryName, neighborCountryName, graphObj))
			return false;

		return true;
	}

	/**
	 * Remove Neighbor Function
	 * 
	 * @param countryName
	 * @param neighborCountryName
	 * @return
	 */
	public boolean removeNeighbor(String countryName, String neighborCountryName) {

		if (!Country.removeNeighbour(countryName, neighborCountryName, graphObj))
			return false;

		return true;
	}

	/**
	 * Show map Function
	 * 
	 * @return
	 */
	public boolean showMap() {

		Graph.showMap();
		return true;
	}

	/**
	 * Save Map Function
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean saveMap(String fileName) {

		try {
			if (!mapxObj.saveMap(graphObj, fileName))
				return false;
			setCurrentState(State.editPlayer, "Edit Player");
		} catch (IOException io) {
			System.out.println("IO Exception Occured");
			return false;
		}

		return true;
	}

	/**
	 * Edit Map Function
	 * 
	 * @param mapName
	 * @return
	 */
	public boolean editMap(String mapName) {
		try {
			File file = new File("src/main/resources/" + mapName);
			if (file.exists()) {
				mapxObj.loadMap("src/main/resources/" + mapName, graphObj);
			} else {
				graphObj = Graph.getInstance();
				System.out.println("New Game Graph created");

			}

			// change current state
			setCurrentState(State.mapEditor, "Map Editor");
		} catch (IOException e) {
			System.out.println("IOException occured");
		}
		return true;
	}

	/**
	 * Validate Map Function
	 * 
	 * @return
	 */
	public boolean validateMap() {

		if (!Mapx.validateMap(graphObj))
			return false;

		return true;
	}

	/**
	 * Load Game Map Function
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean loadGameMap(String fileName) {
		try {
			mapxObj.loadMap("src/main/resources/" + fileName, graphObj);
			setCurrentState(State.editPlayer, "Edit Player");
		} catch (Exception e) {
			System.out.println("File not found");
			return false;
		}

		return true;
	}

	/**
	 * Add Player Function
	 * 
	 * @param playerName
	 * @return
	 */
	public boolean addPlayer(String playerName) {

		if (!Player.addPlayer(playerName, 0))
			return false;

		return true;
	}

	/**
	 * Remove Player Function
	 * 
	 * @param playerName
	 * @return
	 */
	public boolean removePlayer(String playerName) {

		if (!Player.removePlayer(playerName))
			return false;

		return true;
	}

	/**
	 * Populate Countries Function
	 * 
	 * @return
	 */
	public boolean populateCountries() {

		// set number of armies for each player
		Integer numArmies;
		switch (Database.playerList.size()) {
		case 2:
			numArmies = 40;
			break;
		case 3:
			numArmies = 35;
			break;
		case 4:
			numArmies = 30;
			break;
		case 5:
			numArmies = 25;
			break;
		case 6:
			numArmies = 20;
			break;
		default:
			System.out.println("Number of players should be between 2 and 6");
			return false;
		}

		for (Player iter : Database.playerList) {
			iter.numberOfArmies = numArmies;
		}

		Integer playerNumberToBeAssigned = 1;
		while (!Country.allCountriesPopulated(graphObj)) {
			double randomDouble = Math.random();
			randomDouble = randomDouble * graphObj.getAdjList().size() + 1;
			int randomInt = (int) randomDouble;
			Integer randomCountryNumber = randomInt;

			Country countryToBePopulated = Country.getCountryByNumber(randomCountryNumber, graphObj);
			if (countryToBePopulated.getOwner() == null) {
				Player assignedPlayer = Player.getPlayerByNumber(playerNumberToBeAssigned);
				countryToBePopulated.setOwner(assignedPlayer.getName());
				assignedPlayer.setMyCountries(countryToBePopulated.getNumber());
				playerNumberToBeAssigned++;
			} else {
				continue;
			}

			if (playerNumberToBeAssigned > Database.getInstance().getPlayerList().size()) {
				playerNumberToBeAssigned = 1;
			}

		}

		// Change current state to next state
		setCurrentState(State.troopArmies, "Troop Armies");

		// Set current player to the first player
		currentPlayerObj.goToFirstPlayer(currentState, graphObj);

		return true;
	}

	/**
	 * Place Army Function
	 * 
	 * @param country
	 * @return
	 */
	public boolean placeArmy(String country) {

		Country targetCountry = Country.getCountryByName(country, graphObj);
		if (targetCountry == null) {

			return false;
		}

		if (currentPlayerObj.getCurrentPlayer().getNumberOfArmies() <= 0) {

			System.out.println("All armies are placed");

			// Change state of game
			setCurrentState(State.exchangeCards, "exchangeCards");

			// Set current player to the first player
			currentPlayerObj.goToFirstPlayer(currentState, graphObj);

			return false;
		}

		if (targetCountry.getOwner() != null) {
			if (targetCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName()) == false) {

				System.out.println("The country is not belong to the current player");

				return false;
			}
		}

		if (targetCountry.getOwner() == currentPlayerObj.getCurrentPlayer().getName()
				|| targetCountry.getOwner() == null) {
			targetCountry.setOwner(currentPlayerObj.getCurrentPlayer().getName());
			targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() + 1);
			currentPlayerObj.getCurrentPlayer()
					.setNumberOfArmies(currentPlayerObj.getCurrentPlayer().getNumberOfArmies() - 1);
		}
		return true;
	}

	/**
	 * Place All Function
	 * 
	 * @return
	 */
	public boolean placeAll() {
		try {
			while (!Player.allPlayersRemainingArmiesExhausted()) {
				for (Country thisCountry : graphObj.getAdjList()) {
					Player playerThatOwnsThisCountry = Player.getPlayerByName(thisCountry.getOwner());
					if (playerThatOwnsThisCountry.getNumberOfArmies() > 0) {
						thisCountry.setNumberOfArmies(thisCountry.getNumberOfArmies() + 1);
						playerThatOwnsThisCountry.setNumberOfArmies(playerThatOwnsThisCountry.getNumberOfArmies() - 1);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("errrroeee: " + e.getMessage());
		}
		// change state of game
		setCurrentState(State.exchangeCards, "exchangeCards");

		// Set current player to the first player
		currentPlayerObj.goToFirstPlayer(currentState, graphObj);

		return true;
	}

	/**
	 * Exchange Cards Function
	 * 
	 * @param cardNumber1
	 * @param cardNumber2
	 * @param cardNumber3
	 * @return
	 */
	public boolean exchangeCards(Integer cardNumber1, Integer cardNumber2, Integer cardNumber3) {

		Player currentPlayer = currentPlayerObj.getCurrentPlayer();
		Integer currentPlayerCardsListSize = currentPlayer.playerCards.size();

		if (((cardNumber1 > currentPlayerCardsListSize) && (cardNumber1 < 1))
				|| ((cardNumber2 > currentPlayerCardsListSize) && (cardNumber2 < 1))
				|| ((cardNumber3 > currentPlayerCardsListSize) && (cardNumber3 < 1))) {

			System.out.println("Input Numbers is wrong");
			return false;
		}

		if (!cardPlayObj.checkExchangeCardsValidation(currentPlayer.playerCards.get(cardNumber1 - 1),
				currentPlayer.playerCards.get(cardNumber2 - 1), currentPlayer.playerCards.get(cardNumber3 - 1)))
			return false;

		Integer exchageArmies = (currentPlayer.exchangeCardsTimes + 1) * 5;
		currentPlayer.exchangeCardsTimes++;
		currentPlayerObj.setNumReinforceArmies(currentPlayerObj.getNumReinforceArmies() + exchageArmies);

		System.out.println("You exchanged your cards with " + exchageArmies + " armies.");

		Card card1 = currentPlayer.playerCards.get(cardNumber1 - 1);
		Card card2 = currentPlayer.playerCards.get(cardNumber2 - 1);
		Card card3 = currentPlayer.playerCards.get(cardNumber3 - 1);

		currentPlayer.playerCards.remove(cardNumber1 - 1);
		currentPlayer.playerCards.remove(cardNumber2 - 1);
		currentPlayer.playerCards.remove(cardNumber3 - 1);

		cardPlayObj.refundCard(card1);
		cardPlayObj.refundCard(card2);
		cardPlayObj.refundCard(card3);

		if (currentPlayer.playerCards.size() < 3)
			// Change current state to next state
			setCurrentState(State.reinforcementPhase, "Reinforcement");

		return true;
	}

	/**
	 * Fortify none Function
	 * 
	 * @return
	 */
	public boolean ignoreExchangeCards() {

		if (currentPlayerObj.getCurrentPlayer().playerCards.size() < 5)
			// Change current state to next state
			setCurrentState(State.reinforcementPhase, "Reinforcement");
		else {
			System.out.println("You have more than 5 cards. You should exchange your cards.");
			return false;
		}
		return true;
	}

	/**
	 * Reinforce Army Function
	 * 
	 * @param countryName
	 * @param numberOfArmies
	 * @return
	 */
	public boolean reinforceArmy(String countryName, Integer numberOfArmies) {

		if (!Player.reinforcement(countryName, numberOfArmies, graphObj))
			return false;

		if (currentPlayerObj.getNumReinforceArmies() > 0) {

			System.out.println("Please reinforce the remain " + currentPlayerObj.getNumReinforceArmies() + "armies");
		} else {
			// Change current state to next state
			setCurrentState(State.fortificationPhase, "Fortification");
		}

		return true;
	}

	/**
	 * Fortify Army Function
	 * 
	 * @param sourceCountry
	 * @param destinationCountry
	 * @param numberOfArmy
	 * @return
	 */
	public boolean fortifyArmy(String sourceCountry, String destinationCountry, Integer numberOfArmy) {

		if (!Player.fortify(sourceCountry, destinationCountry, numberOfArmy, getGraphObj()))
			return false;

		// Change current state to next state
		setCurrentState(State.reinforcementPhase, "Reinforcement");

		// Change current player
		currentPlayerObj.goToNextPlayer(currentState, graphObj);

		return true;
	}

	/**
	 * Fortify none Function
	 * 
	 * @return
	 */
	public boolean ignoreFortifyArmy() {

		// Change current state to next state
		setCurrentState(State.reinforcementPhase, "Reinforcement");

		// Change current player
		currentPlayerObj.goToNextPlayer(currentState, graphObj);

		return true;
	}

	/**
	 * when player decided noattack
	 * 
	 * @return true if implemented
	 */
	public boolean ignoreAttack() {

		// Change current state to next state
		setCurrentState(State.attackPhase, "Attacking");

		System.out.println(
				"Player " + currentPlayerObj.getCurrentPlayerObj().getCurrentPlayer().name + " decided not to attack");

		// add fortification

		// Change current player
		currentPlayerObj.goToNextPlayer(currentState, graphObj);

		return true;
	}

	public Integer defenderCommandInput(Integer numberOfArmiesDefenderCanSelect, Scanner sc) {
		String defenderCommand = "";
		Integer defenderDice = null;
		defenderCommand = sc.nextLine();
		String[] defenderDiceSplit = defenderCommand.split(" ");
		if (!(defenderDiceSplit.length == 2 && defenderDiceSplit[0].trim().equals("defend")
				&& ((Integer.parseInt(defenderDiceSplit[1])) < (numberOfArmiesDefenderCanSelect + 1))
				&& ((Integer.parseInt(defenderDiceSplit[1])) > 0))) {

			System.out.println("Please write a valid command");

		} else {
			defenderDice = Integer.parseInt(defenderDiceSplit[1].trim());
		}
		defenderCommand = "";

		if (defenderDice != null) {
			return defenderDice;
		} else {
			return defenderCommandInput(numberOfArmiesDefenderCanSelect, sc);
		}
	}

	public Integer defenderArmiesSelectionForAllout() {
		return 0;
	}

	public boolean attackAllout(String fromCountry, String toCountry) {

		Country attackerCountry = Country.getCountryByName(fromCountry, graphObj);
		Country defenderCountry = Country.getCountryByName(toCountry, graphObj);

		if (attackerCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().name)) {

			if (defenderCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().name)) {
				System.out.println("You can only attack the countries that are owned by some other player");
				return false;
			} else {
				if (attackerCountry.neighbours.contains(defenderCountry.getNumber())) {

					Integer AttackerArmiesSelected = null;
					Integer DefenderArmiesSelected = null;

					if (attackerCountry.getNumberOfArmies() > 3) {
						AttackerArmiesSelected = 3;
						// armies selected 3
					} else if (attackerCountry.getNumberOfArmies() == 3) {
						AttackerArmiesSelected = 2;
						// armies selected 2
					} else if (attackerCountry.getNumberOfArmies() == 2) {
						AttackerArmiesSelected = 1;
						// armies selected 1
					} else {
						System.out.println("Cannot attack anymore " + fromCountry + ", has only 1 army left!");
					}

					if (defenderCountry.getNumberOfArmies() >= 2) {
						DefenderArmiesSelected = 2;
						// armies selected 2
					} else if (defenderCountry.getNumberOfArmies() == 1) {
						DefenderArmiesSelected = 1;
						// armies selected 1
					} else {
						System.out.println("No more armies to defend the country");
					}

					System.out
							.println("A" + Country.getCountryByName("Quebec", Graph.getInstance()).getNumberOfArmies());

					System.out.println(Country.getCountryByName("Greenland", Graph.getInstance()).getNumberOfArmies());
					battle(attackerCountry, defenderCountry, AttackerArmiesSelected, DefenderArmiesSelected);
					AttackerArmiesSelected = null;
					DefenderArmiesSelected = null;
					System.out
							.println("A" + Country.getCountryByName("Quebec", Graph.getInstance()).getNumberOfArmies());

					System.out.println(Country.getCountryByName("Greenland", Graph.getInstance()).getNumberOfArmies());

					if (defenderCountry.getNumberOfArmies() == 0) {
						System.out.println("Attacker won the country " + defenderCountry.name);

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
						System.out.println("allout is finished here.");
						scanner.close();

					} else if (attackerCountry.getNumberOfArmies() == 1) {

						System.out.println("no attack anymore possible!!!");
					} else {
						attackAllout(fromCountry, toCountry);
					}

				} else {
					System.out.println("Attacker country and the defender country should be adjacent");
				}
			}
		} else {
			System.out.println("Please select the country owned by you(" + currentPlayerObj.getCurrentPlayer().name
					+ ") as attackerCountry");
			return false;
		}

		// System.out.println("AllOut khatam");
		return true;

	}
	// Scanner scanner = new Scanner(System.in);

	public Integer attackMoveCommand(Integer numberOfArmiesDuringLastAttack, Scanner scanner,
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

			if (attackMoveNumber < numberOfArmiesDuringLastAttack || attackMoveNumber>=numberOfArmiesThatAttackerHave) {
				Integer range =numberOfArmiesThatAttackerHave-1;
				if(numberOfArmiesDuringLastAttack==range) {
					System.out.println("you can only move " + range + " armies/army" + ", please retry:" );
					return attackMoveCommand(numberOfArmiesDuringLastAttack, scanner, numberOfArmiesThatAttackerHave);}
				else {
				System.out.println("you have to select number of armies between the number " + numberOfArmiesDuringLastAttack + "and " + range +", please retry:");
				return attackMoveCommand(numberOfArmiesDuringLastAttack, scanner, numberOfArmiesThatAttackerHave);
			}} else {
				return attackMoveNumber;
			}
		} else {
			return attackMoveCommand(numberOfArmiesDuringLastAttack, scanner, numberOfArmiesThatAttackerHave);
		}
		// scanner.close();

	}

	public boolean attackMove(Country attackerCountry, Country defenderCountry, Integer numberOfArmiesToMove) {

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

	public boolean attackCountry(String fromCountry, String toCountry, Integer numDice) {

		Country attackerCountry = Country.getCountryByName(fromCountry, graphObj);
		Country defenderCountry = Country.getCountryByName(toCountry, graphObj);

		if (attackerCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().name)) { // Owner of
																										// attackerCountry
																										// should be
																										// same as
																										// currentPlayer

			if (defenderCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().name)) { // Owner of
																											// defenderCointry
																											// cannot be
																											// currentPlayer
				System.out.println("You can only attack the countries that are owned by some other player");
				return false;
			} else {
				if (attackerCountry.neighbours.contains(defenderCountry.getNumber())) { // checks adjacency of the
																						// countries
					if (attackerCountry.getNumberOfArmies() > 1) { // can attack keeping min 1 army in his own country
						if (attackerCountry.getNumberOfArmies() > numDice) { // armies in attacker country should be
																				// more than armies selected for attack

							if (numDice < 4) { // max armies for attack can be 3
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

								sc.close();
								if (defenderDice > 2) { // defender can select max 2 armies
									System.out.println("defender entered a number greater than 2");
									return false;
								} else {

									System.out.println("A" + Country.getCountryByName("Quebec", Graph.getInstance())
											.getNumberOfArmies());

									System.out.println(Country.getCountryByName("Greenland", Graph.getInstance())
											.getNumberOfArmies());

									battle(attackerCountry, defenderCountry, numDice, defenderDice);

									if (defenderCountry.getNumberOfArmies() == 0) {
										System.out.println("Attacker won the country " + defenderCountry.name);

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
										System.out.println("allout is finished here.");
										scanner.close();
									

										System.out.println("attackCountry command finished");
									}
									System.out.println("A" + Country.getCountryByName("Quebec", Graph.getInstance())
											.getNumberOfArmies());

									System.out.println(Country.getCountryByName("Greenland", Graph.getInstance())
											.getNumberOfArmies());
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
				}
			}

		} else {
			System.out.println("Please select the country owned by you(" + currentPlayerObj.getCurrentPlayer().name
					+ ") as attackerCountry");
			return false;
		}

		return true;
	}

	public static int getRandomNumber(Integer maxDice) {
		Random randomGenerator;
		randomGenerator = new Random();
		return randomGenerator.nextInt(maxDice) + 1;
	}

	public boolean battle(Country attackerCountry, Country defenderCountry, Integer attackerArmies,
			Integer defenderArmies) {

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

		if (defenderArmies > attackerArmies) {
			for (int i = 0; i < attackerArmies; i++) {

				if (attackerDices.get(i) > defenderDices.get(i)) {

					defenderArmiesKilled++;
					System.out.println("--- Attacker wins the battle ---");
				} else {
					attackerArmiesKilled++;
					System.out.println("--- Defender wins the battle ---");

				}
			}
		} else {

			for (int i = 0; i < defenderArmies; i++) {

				if (attackerDices.get(i) > defenderDices.get(i)) {

					defenderArmiesKilled++;
					System.out.println("--- Attacker wins the battle ---");
				} else {
					attackerArmiesKilled++;
					System.out.println("--- Defender wins the battle ---");

				}
			}
		}

		System.out.println("AC" + attackerArmiesKilled);
		System.out.println("DC" + defenderArmiesKilled);
		attackerCountry.setNumberOfArmies(attackerCountry.getNumberOfArmies() - attackerArmiesKilled);
		defenderCountry.setNumberOfArmies(defenderCountry.getNumberOfArmies() - defenderArmiesKilled);

		return true;
	}

	@Override
	public void notifyObservers() {

	}

	@Override
	public void attachObserver(Object observer) {

	}

	@Override
	public void detachObserver(Object observer) {

	}
}
