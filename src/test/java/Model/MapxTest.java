package Model;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class MapxTest {
    static Mapx map;
    static Graph g;

    @Before
    public void setUp() throws Exception {
        map= new Mapx();
        g=map.createGameGraph("src/main/resources/map.map");
    }

    @BeforeClass
    public static void setUpClass() throws Exception{
        map= new Mapx();
        g=map.createGameGraph("src/main/resources/map.map");

    }

    @After
    public void tearDownClass() throws Exception {
        map=null;
        g=null;
        Database.getInstance().getContinentList().clear();
        Database.getInstance().getPlayerList().clear();
    }

    @AfterClass
    public static void tearDown() throws Exception{
        map=null;
        g=null;
        Database.getInstance().getContinentList().clear();
        Database.getInstance().getPlayerList().clear();
    }

    @Test
    public void createGameGraph() {
    }

    @Test
    public void createFile() {
    }

    @Test
    public void saveMap() {
    }

    @Test
    public void validateMap() {
    }

    @Test
    public void addCountry1() {
        map.addCountry("Tchala","Africa",g);
        Country retrievedCountry= null;
        for(Country country: g.getAdjList()){
            if(country.getName().equalsIgnoreCase("Tchala")){
                retrievedCountry= country;
            }
        }
        assertTrue(retrievedCountry.getName().equalsIgnoreCase("Tchala"));
    }

    @Test
    public void addCountry2() {
        map.addCountry("Tchala2","NonExistentContinent",g);
        Country retrievedCountry= new Country(00,"",-1,"",-1,-1,-1,null);
        for(Country country: g.getAdjList()){
            if(country.getName().equalsIgnoreCase("Tchala2")){
                retrievedCountry= country;
            }
        }
        assertFalse(retrievedCountry.getName().equalsIgnoreCase("Tchala2"));
    }


    
    @Test
    public void addNeighbour_NotExistFirstCountry() {
    	System.out.println("-----addNeighbour_NotExistFirstCountry start-----");

    	ArrayList<Integer> neighbours = null;
		Country firstCountry = new Country( 0, "fistCountry", 0 , "---", 0 , 0 , 0 , neighbours);
    	Country secndCountry = g.getAdjList().get(0);

    
		boolean add = map.addNeighbour( firstCountry.getName(),  secndCountry.getName(), g);
    
		//we need a function to check if a country name exist in the graph or not. Can I write that?
		
		assertTrue(!add);
		
    	System.out.println("-----addNeighbour_NotExistFirstCountry end-----");
    }

   
    
    @Test
    public void addNeighbour_NotExistSecndCountry() {
    	System.out.println("-----addNeighbour_NotExistSecndCountry start-----");

    	ArrayList<Integer> neighbours = null;
		Country secondCountry = new Country( 0, "secndCountry", 0, " ", 0 , 0 , 0 , neighbours);
    	Country firstCountry = g.getAdjList().get(0);

    
    	boolean add = map.addNeighbour( firstCountry.getName(),  secondCountry.getName(), g);

    	assertTrue(!add);
		
    	System.out.println("-----addNeighbour_NotExistSecndCountry end-----");
    }

   
    @Test
    public void addNeighbour_AlreadyNeighbourCountries() {
    	System.out.println("-----addNeighbour_AlreadyNeighbourCountries start-----");

    	Country firstCountry = g.getAdjList().get(0);
    	int indx = firstCountry.neighbours.get(0);
    	Country secndCountry = g.getAdjList().get(indx-1);

    	boolean add = map.addNeighbour( firstCountry.getName(),  secndCountry.getName(), g);
    	
    	assertTrue(!add);
		
    	System.out.println("-----addNeighbour_AlreadyNeighbourCountries end-----");
    }

   

    @Test
    public void addNeighbour_NotNeighbour() {
    	System.out.println("-----addNeighbour_NotNeighbour start-----");
    	
    	Country firstCountry = null ;
    	Country secndCountry = null ;
    	
    	firstCountry = g.getAdjList().get(0);
    	
    	ArrayList<Integer> neibCountries = g.getAdjList().get(0).getNeighbours();  
    
		outer:
        for (Country country : g.getAdjList()) {
        	
        	if ( !firstCountry.getName().equalsIgnoreCase(country.getName()) ) {

            boolean Neib=false;
        	for (int index=0;  index<neibCountries.size(); index++) {

    	    	int indx = neibCountries.get(index);
    	    	Country neib = g.getAdjList().get(indx-1);
            	
                if ( country.getName().equalsIgnoreCase( neib.getName() ) ) Neib = true; 
                
        	}
        	
        	if(!Neib) {
        		secndCountry=country;
				break outer;
        	}
        	}
        }

   
		map.addNeighbour( firstCountry.getName(),  secndCountry.getName(), g);

		boolean neib = isNeighour( firstCountry, secndCountry, g );
		
		assertTrue(!neib);
		
    	System.out.println("-----addNeighbour_NotNeighbour end-----");
    }
   
    @Test
    public void removeNeighbour_TwoNeighbourCountry() {
       	System.out.println("-----removeNeighbour_TwoNeighbourCountry start-----");

    	Country firstCountry = g.getAdjList().get(0);
    	int indx = firstCountry.neighbours.get(0);
    	Country secndCountry = g.getAdjList().get(indx-1);

        
		map.removeNeighbour( firstCountry.getName(),  secndCountry.getName(), g);
    	
        
        boolean mutualNeighbour = isNeighour( firstCountry, secndCountry, g);

        
        assertTrue(!mutualNeighbour);
    	System.out.println("-----removeNeighbour_TwoNeighbourCountry end-----");
    }
 

    private boolean isNeighour(Country firstCountry, Country secndCountry, Graph graph) {
    
    boolean mutualNeighbour = false;
    int indx;
    
    for ( int index=0;  index<firstCountry.getNeighbours().size(); index++) {
    	
    	indx = firstCountry.neighbours.get(index);
    	Country neib = graph.getAdjList().get(indx-1);
    	if ( secndCountry.getName().equalsIgnoreCase(neib.getName()) ) mutualNeighbour = true;
    	
    }
    
    
    for ( int index=0;  index<secndCountry.getNeighbours().size(); index++) {
    	
    	indx = secndCountry.neighbours.get(index);
    	Country neib = graph.getAdjList().get(indx-1);
    	if ( firstCountry.getName().equalsIgnoreCase(neib.getName()) ) mutualNeighbour = true;
    	
    }
    
    return mutualNeighbour;     
	    
 }

    @Test
    public void removeCountry() {
    }

    @Test
    public void addContinent() {
    }

    @Test
    public void removeContinent() {
    }

    @Test
    public void addArmiesToCountry() {
    }

    @Test
    public void removeArmiesFromCountry() {
    }
}