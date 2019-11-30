package GameLogic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is our save loader which implement from saveloadBuilder interface.
 */

public class SaveGame implements SaveLoadBuilder {

	private File gameFile;
	private FileWriter gameFileWriter;
	
	
	/**
	 * This method set our file for load or save purposing.
	 * 
	 * @param fileName This is the file name which is string.
	 */
	public void setFile(String fileName) throws IOException{
		
		gameFile = new File("src/main/resources/" + fileName);
		gameFile.createNewFile();
		gameFileWriter = new FileWriter(gameFile);	
	}
	
	/**
	 * This method extract the continents and save them into files.
	 */
	@Override
	public void handleContinent(){
		try {
			this.gameFileWriter.write("[continents]" + System.getProperty("line.separator"));
			
			for(Continent itr : Database.continentList){
				
				if (itr.getOwner().isEmpty()) {
					itr.owner = "null";
				}
				this.gameFileWriter.write(itr.name + "," + itr.color + "," + itr.number + "," + itr.controlValue + "," + itr.owner + System.getProperty("line.separator"));
			}
			
		//	this.gameFileWriter.write("***" + System.getProperty("line.separator"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method extract the countries and save them into files.
	 */
	@Override
	public void handleCountry() {
		try {
			this.gameFileWriter.write("[countries]" + System.getProperty("line.separator"));
			
			for(Country itr : Graph.adjList){
				
				if (itr.owner.isEmpty()) {
					itr.owner = "null";
				}
				this.gameFileWriter.write(itr.number + "," + itr.coOrdinate1 + "," + itr.getCoOrdinate2 + "," + itr.inContinent + "," + itr.numberOfArmies + "," + itr.name + "," + itr.owner);
				this.gameFileWriter.write(",[borders]" + ",");
				for(Integer itrb: itr.neighbours) {
					this.gameFileWriter.write(itrb + ",");
				}
				this.gameFileWriter.write(System.getProperty("line.separator"));
			}
			
		//	this.gameFileWriter.write("***" + System.getProperty("line.separator"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * This method extract the players and save them into files.
	 */
	@Override
	public void handlePlayers() {
		try {
			this.gameFileWriter.write("[Players]" + System.getProperty("line.separator"));
			
			if( Database.playerList.size() >= 1) {
				
				for(IPlayer itr : Database.playerList){
					
					this.gameFileWriter.write(itr.getName() + "," + itr.getPlayerStrategy() + "," + itr.getNumber() + "," + itr.getNumberOfArmies() + "," + itr.getNumberOfFreeArmies() + ",");
					//this.gameFileWriter.write("MyCountries" + ":");
					for(Integer myCountriesItr : itr.getMyCountries()) {
						this.gameFileWriter.write(myCountriesItr + ":");
					}
					//this.gameFileWriter.write("EndMyCountries" + ",");
					this.gameFileWriter.write(",");
					this.gameFileWriter.write(itr.getExchangeCardsTimes() + ","+ itr.getCountryConquered() + "," + itr.getDefenderRemoved() + "," + Player.lastDiceSelected + ",");
					//this.gameFileWriter.write("MyCards" + ":");
					
					if(itr.getPlayerCards().size() == 0) {
						this.gameFileWriter.write("noCards");
					}
					for(Card itrC: itr.getPlayerCards()) {
						this.gameFileWriter.write(itrC.getIdCard() + "-" + itrC.getOwner() + "-" + itrC.getCardType()  + ":");
					}
					//this.gameFileWriter.write("EndMyCards");
					this.gameFileWriter.write(System.getProperty("line.separator"));
				}
			
			}
			else {
				this.gameFileWriter.write("No-Player" + System.getProperty("line.separator"));
			}
	//		this.gameFileWriter.write("***" + System.getProperty("line.separator"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method extract free cards and save them into files.
	 */
	@Override
	public void handleFreeCards() {
		try {
			this.gameFileWriter.write("[FreeCards]" + System.getProperty("line.separator"));
			
			for(Card itr : CardPlay.getCardsList()){
				
				this.gameFileWriter.write(itr.getCardType() + "-" + itr.getIdCard() + "-" + itr.getOwner() + System.getProperty("line.separator"));
			}
	//		this.gameFileWriter.write("***" + System.getProperty("line.separator"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method extract the current state and save them into files.
	 */
	@Override
	public void handleCurrentState() {
		try {
			GamePlay gamePlayObj = GamePlay.getInstance();
			this.gameFileWriter.write("[CurrentState]" + System.getProperty("line.separator"));
			String state = "";
			if ( gamePlayObj.getCurrentState() == State.initializeGame )
				state = "initializeGame";
			else if ( gamePlayObj.getCurrentState() == State.mapEditor )
				state = "mapEditor";
			else if ( gamePlayObj.getCurrentState() == State.startupPhase )
				state = "startupPhase";
			else if ( gamePlayObj.getCurrentState() == State.editPlayer )
				state = "editPlayer";
			else if ( gamePlayObj.getCurrentState() == State.troopArmies )
				state = "troopArmies";
			else if ( gamePlayObj.getCurrentState() == State.exchangeCards )
				state = "exchangeCards";
			else if ( gamePlayObj.getCurrentState() == State.attackPhase )
				state = "attackPhase";
			else if ( gamePlayObj.getCurrentState() == State.fortificationPhase )
				state = "fortificationPhase";
			else if ( gamePlayObj.getCurrentState() == State.gameFinished )
				state = "gameFinished";
			
			this.gameFileWriter.write(state + System.getProperty("line.separator"));
//			this.gameFileWriter.write("***" + System.getProperty("line.separator"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method extract the current player and save them into files.
	 */
	@Override
	public void handleCurrentPlayer() {
		try {
			CurrentPlayer currentPlayerObj = CurrentPlayer.getInstance();
			GamePlay gamePlayObj = GamePlay.getInstance();
			
			this.gameFileWriter.write("[CurrentPlayer]" + System.getProperty("line.separator"));
			
			if( gamePlayObj.getCurrentState() == State.exchangeCards || gamePlayObj.getCurrentState() == State.attackPhase || gamePlayObj.getCurrentState() == State.fortificationPhase || gamePlayObj.getCurrentState() == State.gameFinished )
				this.gameFileWriter.write( currentPlayerObj.getCurrentPlayer().getNumber()+ "," + currentPlayerObj.getNumReinforceArmies() + System.getProperty("line.separator"));
			this.gameFileWriter.write("***" + System.getProperty("line.separator"));
			gameFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}