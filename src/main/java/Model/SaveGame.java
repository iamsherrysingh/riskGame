package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveGame implements SaveLoadBuilder {

	private BufferedWriter gameFile;
	
	public void setFile(String fileName) throws IOException{
		
		this.gameFile = new BufferedWriter(new FileWriter(fileName));
	}
	
	@Override
	public void handleContinent(){
		
	}
	
	@Override
	public void handleCountry() {
		
	}
	
	@Override
	public void handlePlayers() {
		
	}
	
	@Override
	public void handleFreeCards() {
		
	}
	
	@Override
	public void handleCurrentState() {
		
	}
	
	@Override
	public void currentPlayer() {
		
	}
}

