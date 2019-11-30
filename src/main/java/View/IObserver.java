package View;
import GameLogic.GamePlay;
import GameLogic.IPlayer;

/**
 * This is an interface used to implement Observer Pattern
 */
public interface IObserver {
    public void update(GamePlay gamePlay, IPlayer currentPlayer);
}
