package GameLogic;

import java.io.IOException;

public class Director {
	
	private SaveLoadBuilder builder;

	public void setBuilder(SaveLoadBuilder newSaveLoadBuilder, String fileName) {
		  
		builder = newSaveLoadBuilder;
	    
	    try {
			builder.setFile(fileName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	  }

	  public void constructBuilder() {
		  
		builder.handleContinent();
		builder.handleCountry();
		builder.handlePlayers();
		builder.handleFreeCards();
		builder.handleCurrentState();
		builder.handleCurrentPlayer();
		
	  }
}
