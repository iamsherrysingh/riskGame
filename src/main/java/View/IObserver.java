package View;
import GameLogic.GamePlay;
import GameLogic.IPlayer;

public interface IObserver {
    public void update(GamePlay gamePlay, IPlayer currentPlayer);
}
