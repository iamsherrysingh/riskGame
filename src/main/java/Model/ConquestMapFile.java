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


public class ConquestMapFile{

	private String continents;
	private String territories;
		
	
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


	public boolean writeMapConquest(String mapName) throws IOException {

			File f = Mapx.createFile(mapName);
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
