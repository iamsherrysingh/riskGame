package GameLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ListIterator;
import java.util.Scanner;

/**
<<<<<<< HEAD
 * This class is our loader which implement from saveloadBuilder interface.
 */
public class LoadGame implements SaveLoadBuilder{

	private FileReader gameFileReader;
	public String fileName;
	public void setFile(String fileName) throws IOException{
			this.fileName=fileName;
		gameFileReader = new FileReader("src/main/resources/" + fileName);
	}
	
	/**
	 * This method save the extracted continents from the files into variables.
	 */
	@Override
	public void handleContinent(){
		
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName))){
			
			String continents;
			StringBuilder sb = new StringBuilder();
			String line = br.readLine().trim();
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
				line = br.readLine();
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
				
				if( !split[4].equals("null") ) {
					continent.setOwner(split[4]);
				}
				
				Database.getInstance().getContinentList().add(continent);
				
				
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method save the extracted countries from the files into variables.
	 */
	@Override
	public void handleCountry() {
		// Read countries
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName))){
			
			String countries;
			StringBuilder sb = new StringBuilder();
			String line = br.readLine().trim();
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
				line = br.readLine();
				
			}
			
			countries = sb.toString();
			countries = countries.trim();	
			Scanner countryScanner = new Scanner(countries);
			countryScanner.nextLine(); // Ignoring first line of countries
			
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
				
				if( !split[6].equals("null") ) {
					country.setOwner(split[6]);
				}
				
				Graph.adjList.add(country);
				
			}
		} 
		catch (IOException e) {

		}
	}
	
	/**
	 * This method save the extracted players from the files into variables.
	 */
	@Override
	public void handlePlayers() {
		
		// Read Players
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName))){
			
			String players;
			StringBuilder sb = new StringBuilder();
			String line = br.readLine().trim();
			int playersEncountered = 0;
			
			while (line != null) {
				
				if (line.equals("[FreeCards]"))
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
				line = br.readLine();
				
			}
			
			players = sb.toString();
			players = players.trim();
			Scanner playerScanner = new Scanner(players);
			playerScanner.nextLine(); // Ignoring first line of continents
			
			String playerLine[] = players.split("\n");
			
			for (int i = 1; i < playerLine.length; i++) {
				
				playerLine[i] = playerLine[i].trim();
				String split[] = playerLine[i].split(",");
				
				IPlayer playerObj = null;
				
				String playerStrategy = split[1];
				if(playerStrategy.equals("human")) {
					playerObj = new Player();
				}
				else if(playerStrategy.equals("random")) {
					playerObj = new RandomPlayer();
				}
				else if(playerStrategy.equals("aggressive")) {
					playerObj = new AggressivePlayer();
				}
				else if(playerStrategy.equals("benevolent")) {
					playerObj = new BenevolentPlayer();
				}
				else if(playerStrategy.equals("cheater")) {
					playerObj = new CheaterPlayer();
				}
				
								
				playerObj.setName(split[0]);
				playerObj.setNumber(Integer.parseInt(split[2]));
				playerObj.setNumberOfArmies(Integer.parseInt(split[3]));
				playerObj.setNumberOfFreeArmies(Integer.parseInt(split[4]));
				
				String splitCountries[] = split[5].split(":");
				for(int j = 0; j < splitCountries.length; j++) {
					playerObj.getMyCountries().add(Integer.parseInt(splitCountries[j]));
				}
				
				playerObj.setExchangeCardsTimes(Integer.parseInt(split[6]));
				if(split[7].equals("false")){
					playerObj.setCountryConquered(false);
				}
				else
					playerObj.setCountryConquered(true);
				
				if(split[8].equals("false")){
					playerObj.setDefenderRemoved(false);
				}
				else
					playerObj.setDefenderRemoved(true);
				
				if(!split[10].equals("noCards")) {
					String splitCards[] = split[7].split(":");
					for(int j = 0; j < splitCards.length; j++) {
						
						String cardParts[] = splitCards[j].split("-");
						Card tmpCard = new Card();
						
						tmpCard.setIdCard(Integer.parseInt(cardParts[0]));
						tmpCard.setOwner(Integer.parseInt(cardParts[1]));
						if(cardParts[3].equals("Infantry"))
							tmpCard.setCardType(cardType.Infantry);
						else if(cardParts[3].equals("Cavalry"))
							tmpCard.setCardType(cardType.Cavalry);
						else if(cardParts[3].equals("Artillery"))
							tmpCard.setCardType(cardType.Artillery);
						else if(cardParts[3].equals("Wild"))
							tmpCard.setCardType(cardType.Wild);
						
						playerObj.getPlayerCards().add(tmpCard);
					}

				}
								
				Database.playerList.add(playerObj);
				
			}
		} 
		catch (IOException e) {

		}
		
	}
	
	/**
	 * This method save the extracted free cards from the files into variables.
	 */
	@Override
	public void handleFreeCards() {

		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName))){
			
			String freeCards;
			StringBuilder sb = new StringBuilder();
			String line = br.readLine().trim();
			int freeCardsEncountered = 0;
			
			while (line != null) {
				if (line.equals("[CurrentState]"))
					break;
				if (freeCardsEncountered == 1) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}

				if (line.equals("[FreeCards]")) {
					freeCardsEncountered = 1;
					
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				line = br.readLine();
			}
			freeCards = sb.toString();
			freeCards = freeCards.trim();
			Scanner freeCardsScanner = new Scanner(freeCards);
			freeCardsScanner.nextLine(); // Ignoring first line of freeCards
			
			String freeCardsLine[] = freeCards.split("\n");
			
			for (int i = 1; i < freeCardsLine.length; i++) {
				
				freeCardsLine[i] = freeCardsLine[i].trim();
				
				String cardParts[] = freeCardsLine[i].split("-");
				Card tmpCard = new Card();
				
				if(cardParts[0].equals("Infantry"))
					tmpCard.setCardType(cardType.Infantry);
				else if(cardParts[0].equals("Cavalry"))
					tmpCard.setCardType(cardType.Cavalry);
				else if(cardParts[0].equals("Artillery"))
					tmpCard.setCardType(cardType.Artillery);
				else if(cardParts[0].equals("Wild"))
					tmpCard.setCardType(cardType.Wild);
				tmpCard.setIdCard(Integer.parseInt(cardParts[1]));
				tmpCard.setOwner(Integer.parseInt(cardParts[2]));
				
				CardPlay.getCardsList().add(tmpCard);
				
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method save the extracted cuurent state from the files into variables.
	 */
	@Override
	public void handleCurrentState() {
		
		// Read currentState
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName))){
				GamePlay gamePlayObj = GamePlay.getInstance();	
				String currentState;
				StringBuilder sb = new StringBuilder();
				String line = br.readLine().trim();
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
					
					line = br.readLine();
						
				}
					
				currentState = sb.toString();
				currentState = currentState.trim();
				Scanner currentStateScanner = new Scanner(currentState);
				currentStateScanner.nextLine(); // Ignoring first line of currentState
					
				String currentStateLine[] = currentState.split("\n");
				State state = State.initializeGame;
				
				if ( currentStateLine[1].equalsIgnoreCase("initializeGame") )
					state = State.initializeGame;
				else if ( currentStateLine[1].equalsIgnoreCase("mapEditor") )
					state = State.mapEditor;
				else if( currentStateLine[1].equalsIgnoreCase("startupPhase") )
					state = State.startupPhase;
				else if( currentStateLine[1].equalsIgnoreCase("editPlayer") )
					state = State.editPlayer;
				else if( currentStateLine[1].equalsIgnoreCase("troopArmies") )
					state = State.troopArmies;
				else if( currentStateLine[1].equalsIgnoreCase("exchangeCards") )
					state = State.exchangeCards;
				else if( currentStateLine[1].equalsIgnoreCase("attackPhase") )
					state = State.attackPhase;
				else if( currentStateLine[1].equalsIgnoreCase("fortificationPhase") )
					state = State.fortificationPhase;
				else if( currentStateLine[1].equalsIgnoreCase("gameFinished") )
					state = State.gameFinished;
				gamePlayObj.setCurrentState(state, currentStateLine[1]);	
				} 
				catch (IOException e) {

				}
		
	}
	
	/**
	 * This method save the extracted current players from the files into variables.
	 */
	@Override
	public void handleCurrentPlayer() {
		
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName))){
			
			String currentPlayer;
			StringBuilder sb = new StringBuilder();
			String line = br.readLine().trim();
			int currentPlayerEncountered = 0;
			
			while (line != null) {
				if (line.equals("***"))
					break;
				if (currentPlayerEncountered == 1) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}

				if (line.equals("[CurrentPlayer]")) {
					currentPlayerEncountered = 1;
					
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				line = br.readLine();
			}
			currentPlayer = sb.toString();
			currentPlayer = currentPlayer.trim();
			Scanner currentPlayerScanner = new Scanner(currentPlayer);
			currentPlayerScanner.nextLine(); // Ignoring first line of currentPlayer
			
			String currentPlayerLine[] = currentPlayer.split("\n");
			CurrentPlayer currentPlayerObj = CurrentPlayer.getInstance();
			
			currentPlayerLine[1] = currentPlayerLine[1].trim();
			String split[] = currentPlayerLine[1].split(",");
			
			ListIterator<IPlayer> playerItr = Database.playerList.listIterator();
			while(playerItr.hasNext()) {
				IPlayer tmpPlayer = playerItr.next();
				if(tmpPlayer.getNumber() == Integer.parseInt(split[0])) {
					currentPlayerObj.currentPlayerItr = playerItr;
					currentPlayerObj.currentPlayer = tmpPlayer;
					break;
				}
			}
			
			currentPlayerObj.setNumReinforceArmies(Integer.parseInt(split[1]));
			
			GamePlay.getInstance().setPlayerStrategy();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
