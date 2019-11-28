package Model;

import java.util.ListIterator;

/**
 * This Class handle players turn and calculate reinforcement armies
 */
public class CurrentPlayer {

	private static CurrentPlayer currentPlayerObj = null;
	
	public ListIterator<IPlayer> currentPlayerItr;
	public IPlayer currentPlayer;
	private Integer numReinforceArmies;
	CardPlay cardPlayObj;

	public static CurrentPlayer getCurrentPlayerObj() {
		return currentPlayerObj;
	}

	private CurrentPlayer() {
		currentPlayerItr = Database.playerList.listIterator();
		cardPlayObj = CardPlay.getInstance();
		// currentPlayer.setName("");
	}

	/**
	 * Current Player get Instance method with SingleTone design
	 * 
	 * @return It returns an instance of the class Currentplayer
	 */
	public static CurrentPlayer getInstance() {
		if (currentPlayerObj == null)
			currentPlayerObj = new CurrentPlayer();
		return currentPlayerObj;
	}

	public void setNumReinforceArmies(Integer numArmies) {
		this.numReinforceArmies = numArmies;
	}

	public Integer getNumReinforceArmies() {
		return this.numReinforceArmies;
	}

	public IPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Go to next player.
	 * 
	 * @param currentState It is an object of the class currentState
	 * @param gameGraph It is an object of the class Graph
	 */
	public void goToNextPlayer(Graph gameGraph) {

		if (currentPlayerItr.hasNext()) {

			numReinforceArmies = 0;		
			currentPlayer = currentPlayerItr.next();

			// System.out.println("Current player is " + currentPlayer.getName());
			Continent.updateContinentOwner(gameGraph);
			calculateReinforceentArmies();
		} else {
			goToFirstPlayer(gameGraph);
		}
	}

	/**
	 * Reset players turn whenever it comes to the end of the players list.
	 * 
	 * @param currentState It is an enumerator parameter of currentState enum.
	 * @param gameGraph It is an object of the class Graph
	 */
	public void goToFirstPlayer(Graph gameGraph) {
		currentPlayerItr = Database.playerList.listIterator();
		numReinforceArmies = 0;
		currentPlayer = currentPlayerItr.next();

		Continent.updateContinentOwner(gameGraph);
		calculateReinforceentArmies();
	}

	/**
	 * Calculate Reinforcement Armies
	 */
	public void calculateReinforceentArmies() {

		Integer numOfCountries = currentPlayer.getMyCountries().size();
		Integer numArmies = (numOfCountries / 3);

		for (Continent continentItr : Database.continentList) {
			if (continentItr.getOwner().equals(currentPlayer.getName()))
				numArmies += continentItr.getControlValue();
		}

		// Each player has at least 3 armies for reinforcement
		numReinforceArmies += (numArmies > 3) ? numArmies : 3;
	}

	/**
	 * Decrease Reinforcement Armies.
	 * 
	 * @param numOfArmies The number of armies in Integer form that are to be decreased.
	 */
	public void decreaseReinforceentArmies(Integer numOfArmies) {
		numReinforceArmies -= numOfArmies;
	}

	/**
	 * Update The number of players for current Player.
	 * 
	 * @param numArmies The number of armies that are to be added
	 */
	public void increaseCurrentPlayerArmies(Integer numArmies) {
		currentPlayer.setNumberOfArmies(currentPlayer.getNumberOfArmies() + numArmies);
		currentPlayerItr.set(currentPlayer);
	}
}

