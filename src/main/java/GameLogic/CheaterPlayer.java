package GameLogic;

import java.util.*;

public class CheaterPlayer extends Player implements IPlayer {

	public ArrayList<Integer> myCountries = new ArrayList<Integer>();

	/**
	 * This is a constructor of the class CheaterPlayer. It implements the
	 * CheaterPlayer strategy when given command.
	 * 
	 * @param number         It is the number of the player as an integer
	 * @param name           It is the name of the player as a String
	 * @param numberOfArmies It is the integer number denoting the total number of
	 *                       armies of the player
	 */
	public CheaterPlayer(Integer number, String name, Integer numberOfArmies) {
		this.number = number;
		this.name = name;
		this.numberOfArmies = numberOfArmies;
		playerCards = new ArrayList<Card>();
		exchangeCardsTimes = 0;
		countryConquered = false;
		defenderRemoved = false;
	}

	public CheaterPlayer() {
		playerCards = new ArrayList<Card>();
	}
	
	public PlayerStrategy getPlayerStrategy() {
		return PlayerStrategy.cheater;
	}

	@Override
	/**
	 * This method implements using the CheaterPlayer strategy. While reinforcing,
	 * this method doubles the number of armies on all its countries.
	 * 
	 * @param countryName      The name of the country to be reinforced
	 * @param numberOfArmies   The total number of armies in integer form
	 * @param graphObj         Object of the class Graph
	 * @param currentPlayerObj Object of the class CurrentPlayer and fetches the
	 *                         state of the current player
	 * @return true(If the method executes and reinforcement is doubled)
	 */
	public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj,
			CurrentPlayer currentPlayerObj) {

		Integer doubleNumberOfArmies = GamePlay.getInstance().getCurrentPlayerObj().getCurrentPlayer()
				.getNumberOfArmies() * 2;
		for (Country country : graphObj.getAdjList()) {

			if (country.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName())) {


				if (country.getNumberOfArmies() > 0) {
					country.setNumberOfArmies(country.getNumberOfArmies() * 2);
					GamePlay.getInstance().getCurrentPlayerObj().getCurrentPlayer()
							.setNumberOfArmies(doubleNumberOfArmies);

				} else {
					System.out.println("0Armies during reinforcement");
				}
			}

		}

		GamePlay.getInstance().setCurrentOperation("Reinforcement done by cheater player");
		return true;
	}

	@Override
	/**
	 * This method attacks in a form that it conquers all the neighbours of all the
	 * countries in a single move
	 * 
	 * @param fromCountry      It is a String and denotes the name of the country
	 *                         from where the armies are to be sent
	 * @param toCountry        It is a String and denotes the name of the country
	 *                         that is to be attacked
	 * @param graphObj         It is an object of the class Graph
	 * @param currentPlayerObj It is an object of the class CurrentPlayer
	 * @return true(if the method executes successfully) or false(if the country
	 *         entered is invalid or some other validation fails)
	 */
	public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {

		boolean isThereAnyEnemy = false;

		for (Country country : graphObj.getAdjList()) {
			if (!country.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName())) {
				isThereAnyEnemy = true;
				break;
			} else {
				isThereAnyEnemy = false;
			}
		}

		if (isThereAnyEnemy) {
			ArrayList<Country> countriesOwnedbyPlayer = new ArrayList<Country>();
			for (Country country : graphObj.getAdjList()) {
				if (country.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName())) {
					countriesOwnedbyPlayer.add(country);
				}
			}

			for (int i = 0; i < countriesOwnedbyPlayer.size(); i++) {

				ArrayList<Integer> neighboursOfCountry = countriesOwnedbyPlayer.get(i).neighbours;

				for (int j = 0; j < neighboursOfCountry.size(); j++) {

					if (!Country.getCountryByNumber(neighboursOfCountry.get(j), graphObj).getOwner()
							.equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName())) {
						System.out.println("owner of country before attack :"
								+ Country.getCountryByNumber(neighboursOfCountry.get(j), graphObj).owner);
						Country.getCountryByNumber(neighboursOfCountry.get(j), graphObj)
								.setOwner(GamePlay.getInstance().getCurrentPlayerName());
						countryConquered = true;
						System.out.println("owner of country after attack :"
								+ Country.getCountryByNumber(neighboursOfCountry.get(j), graphObj).owner);

					}

					for (IPlayer ip : Database.playerList) {
						if (ip.getMyCountries().size() == 0) {
							Database.playerList.remove(ip);
							defenderRemoved = true;
						}
					}
				}
			}
			GamePlay.getInstance().setCurrentOperation("all-out attack done by cheater player");
			return true;
		} else {
			System.out.println("All the countries in the map belongs to the same player");
			return false;
		}
	}

	@Override
	/**
	 * This method doubles the number of armies of the countries whose neighbours
	 * are owned by other players.
	 * 
	 * @param fromCname      The name of the country from where the armies are to be
	 *                       moved
	 * @param toCountryName  The name of the country to which the armies are to be
	 *                       moved
	 * @param numberOfArmies The total number of armies to be moved
	 * @param gameGraph      This is an object of the class Graph
	 * @return true(If all the conditions are satisfied and the desired country is
	 *         fortified) or false(If the countries specified are absent or it does
	 *         not fulfill the requirements
	 */
	public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {

		int itr = 0;
		boolean neighbourWithDifferentOwner = false;

		for (Country country : gameGraph.getAdjList()) {

			if (country.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName())) {

				for (int i = 0; i < country.neighbours.size(); i++) {

					if (!Country.getCountryByNumber(country.neighbours.get(i), gameGraph).getOwner()
							.equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName())) {

						neighbourWithDifferentOwner = true;
						itr++;
						break;

					}

				}

				if (neighbourWithDifferentOwner = true) {

					if (country.getNumberOfArmies() > 0) {
						System.out.println("Armies in country before fortify " + country.getNumberOfArmies());
						country.setNumberOfArmies(country.getNumberOfArmies() * 2);
						System.out.println("Armies in country after fortify " + country.getNumberOfArmies());
						neighbourWithDifferentOwner = false;
					} else {
						System.out.println("0armies in fortify");
					}
				}

			}

		}
		boolean DiffOwner = false;
		for (Country country : Graph.getInstance().getAdjList()) {

			if (!country.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName())) {
				DiffOwner = true;
				break;
			}

		}
		if (DiffOwner) {

		} else {
			System.out.println("Game should stop as player won the whole map");
		}

		if (itr > 0) {
			System.out.println("Fortification done in cheater player");
			return true;
		} else {
			System.out.println("All countries belongs to same player");
			return false;
		}

	}
}
