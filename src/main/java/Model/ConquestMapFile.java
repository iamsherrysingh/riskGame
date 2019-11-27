package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;


public class ConquestMapFile extends DominationMapFile{

	public String continents;
	public String territories;
		
	
	public ConquestMapFile() {}

	public String getContinents() {
			return continents;
	}

	public String getTerritories() {
			return territories;
	}

	
	public void setContinents(String continents) {
		this.continents = continents;
	}

	public void setTerritories(String territories) {
		this.territories = territories;
	}
	
	
	/**
	 * This reads the conquest maps file and stores the country, continent and border details
	 * in their variables This is used by loadMap(). The variables generated
	 * by this method are used throughout the game.
	 *
	 * @param mapFile It is the name of the map file that is to be executed
	 * @throws FileNotFoundException Throws an exception if the file is not found
	 * @return true(If the method is executed completely)
	 */
	public boolean readMapConquest(String mapFile) throws FileNotFoundException {
		
		try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
			StringBuilder sb = new StringBuilder();
			
			String line = br.readLine().trim();

			int continentsEncountered = 0;
			while (line != null) {

				line = br.readLine();
				
				if (line.equals("[Continents]")) {
					continentsEncountered = 1;
				}
				else if(line.equals("[Territories]")){
				break;
				}


				if (continentsEncountered == 1) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}
		
			    continents = sb.toString().trim();
			
			}
			
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (Exception e) {

		}
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
			StringBuilder sb = new StringBuilder();
			
			String line = br.readLine();
			
			int countriesEncountered = 0;
			while (line != null) {
				
				if (line.equals("[Territories]")) {
					countriesEncountered = 1;
				}
				line = br.readLine();


				if (countriesEncountered == 1 && line!=null) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				
				
			}

			territories = sb.toString();
			territories = territories.trim();
			
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (Exception e) {

		}

		
		return true;
	}

	
	/**
	 * This method operates on the gameGraph variable and converts it to conquest map file.
	 *
	 * @param f the file which used to save map
	 * @param mapName name of map
	 * @throws IOException If the Input or Output file is invalid
	 * @return true(If the method executes and the map is saved) or false(If no map
	 *         name is entered or is invalid)
	 */
	public boolean writeMapConquest(String mapName, File f) throws IOException {

			FileWriter writer = new FileWriter(f);
			writer.write("name " + mapName + System.getProperty("line.separator"));
			writer.write(System.getProperty("line.separator"));
			writer.write("[files]" + System.getProperty("line.separator"));
			writer.write("pic sample.jpg" + System.getProperty("line.separator"));
			writer.write("map sample.gif" + System.getProperty("line.separator"));
			writer.write("crd sample.cards" + System.getProperty("line.separator"));
			writer.write("prv world.jpg" + System.getProperty("line.separator"));
			writer.write(System.getProperty("line.separator"));
			
			writer.write("[continents]" + System.getProperty("line.separator"));
			writer.write(continents + System.getProperty("line.separator"));
			
			writer.write("[Territories]" + System.getProperty("line.separator"));
			writer.write(territories + System.getProperty("line.separator"));
			
			writer.close();
			return true;
	
	}

	
}
