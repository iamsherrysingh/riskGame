package GameLogic;

import java.io.IOException;

/**
 * This Class is the director of Builder Patter
 */
public class Director {
	
	private SaveLoadBuilder builder;

	/**
	 * This method set our file for load or save purposing.
	 * 
	 * @param newSaveLoadBuilder This is an object of builder interface.
	 * @param fileName This is ths file name which is string.
	 */
	public void setBuilder(SaveLoadBuilder newSaveLoadBuilder, String fileName) {
		  
		builder = newSaveLoadBuilder;
	    
	    try {
			builder.setFile(fileName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	  }

	/**
	 * This method is constructBuilder of the builder pattern.
	 */
	  public void constructBuilder() {
		  
		builder.handleContinent();
		builder.handleCountry();
		builder.handlePlayers();
		builder.handleFreeCards();
		builder.handleCurrentState();
		builder.handleCurrentPlayer();
		
	  }
}
