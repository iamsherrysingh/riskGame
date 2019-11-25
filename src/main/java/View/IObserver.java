package View;
import Model.GamePlay;
import Model.IPlayer;
import Model.Player;

public interface IObserver {
    public void update(GamePlay gamePlay, IPlayer currentPlayer);

	public void update(GamePlay gamePlay, Player currentPlayer);
}
