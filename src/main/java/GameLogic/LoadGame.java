package GameLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LoadGame implements SaveLoadBuilder{

	private File gameFile;
	private BufferedReader bufferedReader; 
	
	public void setFile(String fileName) throws IOException{
			
		gameFile = new File("src/main/resources/" + fileName);
		bufferedReader =  new BufferedReader(new FileReader(gameFile));
	}
	
	@Override
	public void handleContinent(){
		
		try {
			
			String continents;
			StringBuilder sb = new StringBuilder();
			String line = bufferedReader.readLine().trim();
			int continentsEncountered = 0;
			
			while (line != null) {
				if (line.equals("[countries]"))
					break;
				if (continentsEncountered == 1) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}

				if (line.equals("[continents]")) {
					continentsEncountered = 1;
					
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				line = bufferedReader.readLine();
			}
			continents = sb.toString();
			continents = continents.trim();
			Scanner continentScanner = new Scanner(continents);
			continentScanner.nextLine(); // Ignoring first line of continents
			
			String continentLine[] = continents.split("\n");
			
			for (int i = 1; i < continentLine.length; i++) {
				
				continentLine[i] = continentLine[i].trim();
				String split[] = continentLine[i].split(",");
				Continent continent = new Continent(Integer.parseInt(split[2]), split[0],Integer.parseInt(split[3]), split[1]);
				Database.getInstance().getContinentList().add(continent);
				
				if( split.length == 5 ) {
					continent.setOwner(split[4]);
				}
				else {
					continent.setOwner("");
				}
				
			}
			
		/*	for(Continent itr: Database.getInstance().getContinentList()){
				System.out.println(itr.color + " " + itr.name + " " + itr.owner);
			} */
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void handleCountry() {
		
		// Read countries
		try {
			
			String countries;
			StringBuilder sb = new StringBuilder();
			String line = bufferedReader.readLine().trim();
			int countriesEncountered = 0;
			
			while (line != null) {
				
				if (line.equals("[Players]"))
					break;
				if (countriesEncountered == 1) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				if (line.equals("[countries]")) {
					countriesEncountered = 1;
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				line = bufferedReader.readLine();
				
			}
			
			countries = sb.toString();
			countries = countries.trim();	
			Scanner countryScanner = new Scanner(countries);
			countryScanner.nextLine(); // Ignoring first line of continents
			
			String countryLine[] = countries.split("\n");
			
			for (int i = 1; i < countryLine.length; i++) {
				
				countryLine[i] = countryLine[i].trim();
				String split[] = countryLine[i].split(",");
				
				Country country = new Country();
				int foundIndex = 0;
				for(int j = 0; j < split.length; j++) {	
					if( split[j].equals("[borders]") ) {
						foundIndex = j + 1;
					}			
				}
				for(int k = foundIndex; k < split.length; k++) {
					country.addNeighbour(Integer.parseInt(split[k]));
				}
				
				country.setNumber(Integer.parseInt(split[0]));
				country.setName(split[5]);
				country.setInContinent(Integer.parseInt(split[3]));
				country.setNumberOfArmies(Integer.parseInt(split[4]));
				country.setCoOrdinate1(Integer.parseInt(split[1]));
				country.setCoOrdinate2(Integer.parseInt(split[2]));
				
				if( split[6] != null ) {
					country.setOwner(split[6]);
				}
				
				Graph.adjList.add(country);
				
			}
		} 
		catch (IOException e) {

		}
	}
	
	@Override
	public void handlePlayers() {
		
		// Read Players
		try {
			
			String players;
			StringBuilder sb = new StringBuilder();
			String line = bufferedReader.readLine().trim();
			int playersEncountered = 0;
			
			while (line != null) {
				
				if (line.equals("[CurrentState]"))
					break;
				if (playersEncountered  == 1) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				if (line.equals("[Players]")) {
					playersEncountered = 1;
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				line = bufferedReader.readLine();
				
			}
			
			players = sb.toString();
			players = players.trim();
			Scanner playerScanner = new Scanner(players);
			playerScanner.nextLine(); // Ignoring first line of continents
			
			String playerLine[] = players.split("\n");
			
	/*		for (int i = 1; i < playerLine.length; i++) {
				
				playerLine[i] = playerLine[i].trim();
				String split[] = playerLine[i].split(",");
				
				//IPlayer player = new IPlayer();
				int foundIndex = 0;
				for(int j = 0; j < split.length; j++) {	
					if( split[j].equals("[borders]") ) {
						foundIndex = j + 1;
					}			
				}
				for(int k = foundIndex; k < split.length; k++) {
					country.addNeighbour(Integer.parseInt(split[k]));
				}
				
				country.setNumber(Integer.parseInt(split[0]));
				country.setName(split[5]);
				country.setInContinent(Integer.parseInt(split[3]));
				country.setNumberOfArmies(Integer.parseInt(split[4]));
				country.setCoOrdinate1(Integer.parseInt(split[1]));
				country.setCoOrdinate2(Integer.parseInt(split[2]));
				
				if( split[6] != null ) {
					country.setOwner(split[6]);
				}
				
				Graph.adjList.add(country);
				
			} */
		} 
		catch (IOException e) {

		}
		
	}
	
	@Override
	public void handleFreeCards() {
		
	}
	
	@Override
	public void handleCurrentState() {
		
		// Read currentState
			try {
				GamePlay gamePlayObj = GamePlay.getInstance();	
				String currentState;
				StringBuilder sb = new StringBuilder();
				String line = bufferedReader.readLine().trim();
				int currentStateEncountered = 0;
					
				while (line != null) {
						
					if (line.equals("[CurrentPlayer]"))
					 break;	
					if (currentStateEncountered  == 1) {
						sb.append(line);
						sb.append(System.lineSeparator());
					}
					if (line.equals("[CurrentState]")) {
						currentStateEncountered = 1;
						sb.append(line);
						sb.append(System.lineSeparator());
					}
					
					line = bufferedReader.readLine();
						
				}
					
				currentState = sb.toString();
				currentState = currentState.trim();
				Scanner currentStateScanner = new Scanner(currentState);
				currentStateScanner.nextLine(); // Ignoring first line of currentState
					
				String currentStateLine[] = currentState.split("\n");
				State state = State.initializeGame;;
				
				if ( currentStateLine[0].equalsIgnoreCase("initializeGame") )
					state = State.initializeGame;
				else if ( currentStateLine[0].equalsIgnoreCase("mapEditor") )
					state = State.mapEditor;
				else if( currentStateLine[0].equalsIgnoreCase("startupPhase") )
					state = State.startupPhase;
				else if( currentStateLine[0].equalsIgnoreCase("editPlayer") )
					state = State.editPlayer;
				else if( currentStateLine[0].equalsIgnoreCase("troopArmies") )
					state = State.troopArmies;
				else if( currentStateLine[0].equalsIgnoreCase("exchangeCards") )
					state = State.exchangeCards;
				else if( currentStateLine[0].equalsIgnoreCase("attackPhase") )
					state = State.attackPhase;
				else if( currentStateLine[0].equalsIgnoreCase("fortificationPhase") )
					state = State.fortificationPhase;
				else if( currentStateLine[0].equalsIgnoreCase("gameFinished") )
					state = State.gameFinished;
				gamePlayObj.setCurrentState(state, currentStateLine[0]);	
				} 
				catch (IOException e) {

				}
		
	}
	
	@Override
	public void handleCurrentPlayer() {
		
	}
	
}
