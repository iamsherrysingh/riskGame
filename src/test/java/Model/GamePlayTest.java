package Model;
import Model.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GamePlayTest {
    static GamePlay gamePlay;

    @Before
    public void setUp() throws Exception {
        gamePlay= GamePlay.getInstance();
        gamePlay.editMap("map.map");
    }

    @After
    public void tearDown() throws Exception {
        gamePlay.getGraphObj().getAdjList().clear();
        gamePlay = null;
        Database.getInstance().getContinentList().clear();
        Database.getInstance().getPlayerList().clear();
    }

    @Test
    public void calculateReinforcementArmies(){
        Player.addPlayer("seha", 0);
        Player.addPlayer("kammu", 0);
        gamePlay.populateCountries();
        gamePlay.placeAll();
        gamePlay.getCurrentPlayerObj().calculateReinforceentArmies();

        assertEquals(7, gamePlay.getCurrentPlayerObj().getNumReinforceArmies().intValue());

    }


}
