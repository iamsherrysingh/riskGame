package Model;

/**
 * The state of the game in order.
 */
public enum State {
	initializeGame,
	mapEditor,
	startupPhase,
	editPlayer,
	troopArmies,
	newTurn,
	exchangeCards,
	reinforcementPhase,
	attackPhase,
	fortificationPhase,
	gameFinished
}
