package GameLogic;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import GameLogic.AggressivePlayer;




public class AggressivePlayerTest {
    static GamePlay gamePlay;

    /**
     * This method runs before every test method and creates an object for GamePlay and Mapx
     */
    @Before
    public void setUp() {
        gamePlay= GamePlay.getInstance();
        gamePlay.editMap("map.map");
        gamePlay.addPlayer("sd","Human");
        gamePlay.addPlayer("ss","Human");
        gamePlay.populateCountries();
    }

    /**
     * This method runs after each and every test method and clears the instance changed by the test method
     */
    @After
    public void tearDown() {
        gamePlay.getGraphObj().getAdjList().clear();
        gamePlay = null;
        Database.getInstance().getContinentList().clear();
        Database.getInstance().getPlayerList().clear();
    }


    @Test
    public void testWeakestNeighbourEnemy(){
        Country India = Country.getCountryByName("India", gamePlay.getGraphObj());
        System.out.println(India.getOwner());
        if(new AggressivePlayer().getWeakestNeighbourEnemy(India)!=null){
            assertTrue(true);
        }
        else{
            assertTrue(false);
        }
    }
}
