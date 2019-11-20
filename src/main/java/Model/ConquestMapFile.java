package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConquestMapFile {

	private String continents;
	private String territories;
		
	
	public ConquestMapFile() {}

	public String getContinents() {
			return continents;
	}

	public String getTerritories() {
			return territories;
	}

	public boolean readMapConquest(String mapFile) throws FileNotFoundException {
		
		// Read Continents
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
		
	/*
		System.out.println(" end of reading continents " + continents);
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	*/
		
		
		// Read Territories
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

		
		//System.out.println(territories);
		
		return true;
	}


}
