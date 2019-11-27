package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadGame implements SaveLoadBuilder{

private BufferedReader gameFile;
	
	public void setFile(String fileName) throws IOException{
		
		this.gameFile = new BufferedReader(new FileReader(fileName));
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
	public void handleCurrentPlayer() {
		
	}
	
}
