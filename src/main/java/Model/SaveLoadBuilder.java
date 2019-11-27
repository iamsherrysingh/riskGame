package Model;

import java.io.IOException;
import java.util.ArrayList;

public interface SaveLoadBuilder {
	
	public void  handleContinent();
	public void  handleCountry();
	public void  handlePlayers();
	public void  handleFreeCards();
	public void  handleCurrentState();
	public void  handleCurrentPlayer();
	public void  setFile(String fileName) throws IOException;
	
}
