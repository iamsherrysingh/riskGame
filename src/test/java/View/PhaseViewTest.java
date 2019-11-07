package View;
import Model.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * It contains different Junit tests for the methods defined in {@link Model.GamePlay}
 *
 * @see Model.GamePlay
 * @author birjotsingh17
 */

public class PhaseViewTest {
    static GamePlay gamePlay;

    /**
     * This method runs before every test method and creates an object for GamePlay and also loads a map
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        gamePlay= GamePlay.getInstance();
        gamePlay.editMap("randomfile.map");
    }

    /**
     * This method runs after each and every test method and clears the instance changed by the test method
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        gamePlay.getGraphObj().getAdjList().clear();
        gamePlay = null;
        Database.getInstance().getContinentList().clear();
        Database.getInstance().getPlayerList().clear();
    }

    /**
     * This test link to
     * {@link View.PhaseView#update(GamePlay, Player)}
     *
     */
    @Test
    public void phaseViewTest(){
        String expectedOutput= "Current State is :  initializeGame\n" +
                "Operation: New Game Graph created";
        assertEquals(expectedOutput, gamePlay.getPhaseView().data);
    }

}
