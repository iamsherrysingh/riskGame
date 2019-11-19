
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class MapxConquest {
	private String continents;
	private String countries;
	private String borders;
	
	static ArrayList<Continent> continentList= new ArrayList<Continent>();

	
	
	private boolean readMapFile(String mapFile) throws FileNotFoundException {
		
		//Read Continents
		try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
			
			
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
			String continentLine[] = continents.split("\n");
			for (int i = 1; i < continentLine.length; i++) {
				continentLine[i] = continentLine[i].trim();
				String split[] = continentLine[i].split(" ");
				Continent continent = new Continent(Database.getInstance().getContinentList().size() + 1, split[0],
						Integer.parseInt(split[1]), split[2]);
				continentList.add(continent);
			}
		
		
		
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (Exception e) {

		}

		return true;
	}
	
	
	public void loadMap(String mapFile){
		
		System.out.println("hi");
		
		try {
			readMapFile(mapFile);
		} catch (FileNotFoundException f) {
			System.out.println(f.getMessage());
		}	
	}
	
	
}