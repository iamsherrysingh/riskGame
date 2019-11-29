package GameLogic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TournamentTest {

	  static GamePlay gamePlay;
	  Mapx mapx;

	  /**
	   * This method runs before every test method and creates an object for GamePlay and Mapx
	   */
	  @Before
	  public void setUp() {
		  gamePlay = GamePlay.getInstance();
	      mapx= GamePlay.getInstance().getMapxObj();
	  }

	  /**
	   * This method runs after each and every test method and clears the instance changed by the test method
	   */
	  @After
	  public void tearDown() {
		  gamePlay.getGraphObj().getAdjList().clear();
	      gamePlay = null;
	  }
	    
	  /**
	   *  This is a jUnit test for {@link GameLogic.GamePlay.tournament(ArrayList<String> tournamentData)}
	   * @throws Exception
	   */
	  @Test
	    public void Tournamenttest() throws Exception {

		  ArrayList<String> str = new ArrayList<String>();
		  str.add("-M");
		  str.add("map.map");
		  str.add("map2.map");
		  str.add("-P");
		  str.add("cheater");
		  str.add("random");
		  str.add("-G");
		  str.add("3");
		  str.add("-D");
		  str.add("20");
		  gamePlay.tournament(str);
	      Integer numberOfPlayers=0;
	      numberOfPlayers= Database.playerList.size();
	      if( numberOfPlayers > 1)
	    	  assertTrue(true);
	  }

}
