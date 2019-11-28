package GameLogic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGame implements SaveLoadBuilder {

	private File gameFile;
	private FileWriter gameFileWriter;
	
	public void setFile(String fileName) throws IOException{
		
		gameFile = new File("src/main/resources/" + fileName);
		gameFile.createNewFile();
		gameFileWriter = new FileWriter(gameFile);	
	}
	
	@Override
	public void handleContinent(){
		try {
			this.gameFileWriter.write("[continents]" + System.getProperty("line.separator"));
			
			for(Continent itr : Database.continentList){
				
				if (itr.owner == null ) {
					itr.owner = null;
				}
				this.gameFileWriter.write(itr.name + "," + itr.color + "," + itr.number + "," + itr.controlValue + "," + itr.owner + System.getProperty("line.separator"));
			}
			
		//	this.gameFileWriter.write("***" + System.getProperty("line.separator"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void handleCountry() {
		try {
			this.gameFileWriter.write("[countries]" + System.getProperty("line.separator"));
			
			for(Country itr : Graph.adjList){
				
				if (itr.owner == null ) {
					itr.owner = null;
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
	
	@Override
	public void handlePlayers() {
		try {
			this.gameFileWriter.write("[Players]" + System.getProperty("line.separator"));
			
			if( Database.playerList.size() >= 1) {
				
				for(IPlayer itr : Database.playerList){
					
					this.gameFileWriter.write(itr.getName() + "," + itr.getPlayerStrategy() + "," + itr.getNumber() + "," + itr.getNumberOfArmies() + "," + itr.getNumberOfFreeArmies() + "," + itr.getMyCountries() + "," + itr.getExchangeCardsTimes() + "," + itr.getCountryConquered() + "," + itr.getDefenderRemoved() + "," + null );
					this.gameFileWriter.write("[Cards]" + ",");
					for(Card itrC: itr.getPlayerCards()) {
						this.gameFileWriter.write(itrC.getCardType() + "," + itrC.getIdCard() + "," + itrC.getOwner());
					}
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
	
	@Override
	public void handleFreeCards() {
		try {
			this.gameFileWriter.write("[FreeCards]" + System.getProperty("line.separator"));
			
			for(Card itr : CardPlay.getCardsList()){
				
				this.gameFileWriter.write(itr.getCardType() + " " + itr.getIdCard() + " " + itr.getOwner() + System.getProperty("line.separator"));
			}
	//		this.gameFileWriter.write("***" + System.getProperty("line.separator"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	@Override
	public void handleCurrentPlayer() {
		try {
			GamePlay gamePlayObj = GamePlay.getInstance();
			this.gameFileWriter.write("[CurrentPlayer]" + System.getProperty("line.separator"));
			
			if( gamePlayObj.getCurrentState() == State.exchangeCards || gamePlayObj.getCurrentState() == State.attackPhase || gamePlayObj.getCurrentState() == State.fortificationPhase || gamePlayObj.getCurrentState() == State.gameFinished )
				this.gameFileWriter.write( gamePlayObj.getCurrentPlayerObj().getCurrentPlayer().getName() + gamePlayObj.getCurrentPlayerObj().getCurrentPlayer().getNumber() + System.getProperty("line.separator"));
	//		this.gameFileWriter.write("***" + System.getProperty("line.separator"));
			gameFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/*
import java.io.FileWriter;

public class SaveGame {
    public static void main(String[] args)throws Exception {
        GamePlay.getInstance().loadGameMap("map.map");
        saveGame("game.save");
    }

    public static boolean saveGame(String saveFile)throws Exception
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/SavedGames/"+saveFile));
        writer.write("[CurrentState]\ninitializationState\n");

        writer.write("\n[Players]\n");
        for(IPlayer player:Database.getInstance().getPlayerList()){
            writer.write(player.getNumber()+" "+player.getName()+" "+player.getNumberOfArmies());
        }

        writer.write("\n[GameGraph]\n");
        for(Country country: GamePlay.getInstance().getGraphObj().getAdjList()) {
            writer.write(country.getNumber() + " " + country.getName() + " " + country.getInContinent() + " " + country.getOwner() + " " + country.getNumberOfArmies() + " " + country.getCoOrdinate1() + " " + country.getGetCoOrdinate2() + " " + country.getNeighbours().toString()+"\n");
        }

        writer.close();
        return true;
    }
}
*/
