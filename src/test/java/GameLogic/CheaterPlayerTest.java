package GameLogic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheaterPlayerTest {
    static GamePlay gamePlay;

    @Before
    public void setUp() throws Exception {
        gamePlay= GamePlay.getInstance();
        gamePlay.editMap("domination.map");
        gamePlay.addPlayer("sd","cheater");
        gamePlay.addPlayer("ss","cheater");
        gamePlay.populateCountries();
    }

    @After
    public void tearDown() throws Exception {
        gamePlay.getGraphObj().getAdjList().clear();
        gamePlay = null;
        Database.getInstance().getContinentList().clear();
        Database.getInstance().getPlayerList().clear();

    }

    @Test
    public void TestReinforcement(){

        String countryFound = null;
        for(Country country : gamePlay.getGraphObj().getAdjList()){
            if(country.getOwner().equalsIgnoreCase("sd")){
                countryFound=country.name;
            }
        }

        Integer before = Country.getCountryByName(countryFound, gamePlay.getGraphObj()).numberOfArmies;
        Database.getPlayerByName("sd").reinforcement(countryFound,0,gamePlay.getGraphObj(),gamePlay.getCurrentPlayerObj());
        Integer after = Country.getCountryByName(countryFound, gamePlay.getGraphObj()).numberOfArmies;

        if(after == 2*before){
            assertTrue(true);
        }else{
        assertTrue(false);}
    }

    @Test
    public void TestInvalidReinforcement(){
        String countryFound = null;
        for(Country country : gamePlay.getGraphObj().getAdjList()){
            if(country.getOwner().equalsIgnoreCase("sd")){
                countryFound=country.name;
            }
        }

        Integer before = Country.getCountryByName(countryFound, gamePlay.getGraphObj()).numberOfArmies;
        Database.getPlayerByName("sd").reinforcement(countryFound,0,gamePlay.getGraphObj(),gamePlay.getCurrentPlayerObj());
        Integer after = Country.getCountryByName(countryFound, gamePlay.getGraphObj()).numberOfArmies;

        if(after != 2*before){
            assertTrue(false);
        }else{
            assertTrue(true);}
    }


    @Test
    public void ValidAllout(){
        Integer countryFound = 0;
        Integer countryFoundAfter = 0;
        for(Country country : gamePlay.getGraphObj().getAdjList()){
            if(country.getOwner().equalsIgnoreCase("sd")){
                countryFound++;
            }
        }
         Database.getPlayerByName("sd").attackAllout("","",gamePlay.getGraphObj(), gamePlay.getCurrentPlayerObj());

        for(Country country : gamePlay.getGraphObj().getAdjList()){
            if(country.getOwner().equalsIgnoreCase("sd")){
                countryFoundAfter++;
            }
        }
        if(countryFound>countryFoundAfter){
            assertTrue(false);
        }else{
            assertTrue(true);}
    }


    @Test
    public void InvalidAllout(){
        Integer countryFound = 0;
        Integer countryFoundAfter = 0;
        for(Country country : gamePlay.getGraphObj().getAdjList()){
            if(country.getOwner().equalsIgnoreCase("sd")){
                countryFound++;
            }
        }
        Database.getPlayerByName("sd").attackAllout("","",gamePlay.getGraphObj(), gamePlay.getCurrentPlayerObj());

        for(Country country : gamePlay.getGraphObj().getAdjList()){
            if(country.getOwner().equalsIgnoreCase("sd")){
                countryFoundAfter++;
            }
        }
        if(countryFound<countryFoundAfter){
            assertTrue(true);
        }else{
            assertTrue(false);}
    }
}