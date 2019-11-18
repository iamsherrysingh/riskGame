package View;
import Model.GamePlay;
import Model.Player;

public interface IObserver {
    public void update(GamePlay gamePlay, Player player);
}
