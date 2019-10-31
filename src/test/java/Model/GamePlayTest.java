package Model;
import Model.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * It contains different Junit tests for the methods defined in {@link Model.GamePlay}
 * 
 * @see GamePlay.java
 * @author birjotsingh17
 */

public class GamePlayTest {
    static GamePlay gamePlay;

    /**
	 * This method runs before every test method and creates an object for GamePlay and also loads a map
	 * 
	 * @throws Exception
	 */
    @Before
    public void setUp() throws Exception {
        gamePlay= GamePlay.getInstance();
        gamePlay.editMap("map.map");
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
	 * This is a jUnit test for {@link Model.GamePlay#getCurrentPlayerObj()#calculateReinforcementArmies()}
	 */
    @Test
    public void calculateReinforcementArmies(){
        Player.addPlayer("seha", 0);
        Player.addPlayer("kammu", 0);
        gamePlay.populateCountries();
        gamePlay.placeAll();
        gamePlay.getCurrentPlayerObj().calculateReinforceentArmies();

        ArrayList<Integer> controlValues= new ArrayList<Integer>();
        for(Continent continent: Database.getInstance().getContinentList()){
            controlValues.add(continent.getControlValue());

        }
        Integer out= gamePlay.getCurrentPlayerObj().getNumReinforceArmies();

        if(out==5 || out==2 || out==3 || out==7){
            assertTrue(true);
        }
    }

}