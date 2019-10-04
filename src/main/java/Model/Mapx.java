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

import database.Database;

public class Mapx {
	private String continents, countries, borders;

	Database db = Database.getInstance();

	private void loadMap(String mapFile) throws FileNotFoundException {

		// Read Continents
		try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
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
			// System.out.println("Continents::::"+ continents);
			continents = continents.trim();
			addContinenttodb(continents);
			System.out.println("vfhgc" + db.getInstance().getcontinentNames());
			
//            System.out.println(continents);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Read countries
		try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			int countriesEncountered = 0;

			while (line != null) {
				if (line.equals("[borders]"))
					break;
				if (countriesEncountered == 1) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				if (line.equals("[countries]")) {
					countriesEncountered = 1;
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				line = br.readLine();
			}

			countries = sb.toString();
			countries = countries.trim();
//            System.out.println(countries);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Read Borders
		try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			int bordersEncountered = 0;

			while (line != null) {
				if (bordersEncountered == 1) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				if (line.equals("[borders]")) {
					bordersEncountered = 1;
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				line = br.readLine();
			}

			borders = sb.toString();
			borders = borders.trim();
//            System.out.println(neighbours);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Create the main gameGraph
	public Graph createGameGraph(String mapFile) {
		try {
			loadMap(mapFile);
		} catch (FileNotFoundException f) {
			System.out.println(f.getMessage());
		}

		Graph gameGraph = new Graph();

		Scanner countryScanner = new Scanner(this.countries);
		countryScanner.nextLine(); // Ignoring first line of this.countries
		while (countryScanner.hasNext()) {
			String lineCountry = countryScanner.nextLine();
			lineCountry = lineCountry.trim();
			String countryLineSubstrings[] = lineCountry.split(" ");

			ArrayList<Integer> neighbours = new ArrayList<Integer>();
			Scanner borderScanner = new Scanner(this.borders);
			borderScanner.nextLine(); // Ignoring first line of this.borders
			while (borderScanner.hasNext()) {
				String lineBorder = borderScanner.nextLine();
				lineBorder = lineBorder.trim();
				String borderLineSubstrings[] = lineBorder.split(" ");
				if (Integer.parseInt(borderLineSubstrings[0]) == Integer.parseInt(countryLineSubstrings[0])) {
					for (int i = 1; i < borderLineSubstrings.length; i++) {
						neighbours.add(Integer.parseInt(borderLineSubstrings[i]));
					}
					break;
				}
			}
			Country country = new Country(Integer.parseInt(countryLineSubstrings[0]), countryLineSubstrings[1],
					Integer.parseInt(countryLineSubstrings[2]), null, null, Integer.parseInt(countryLineSubstrings[3]),
					Integer.parseInt(countryLineSubstrings[4]), neighbours);
			gameGraph.getAdjList().add(country);
		}
		return gameGraph;
	}

	// Birjot

	public static File createfile(String mapName) throws IOException {

		Scanner sc1 = new Scanner(System.in);
		File file = new File("C:\\Users\\birjotsingh17\\Documents\\APP\\riskGame\\src\\main\\resources\\" + mapName);

		if (file.createNewFile()) {
			System.out.println("map saved");
		} else {
			System.out.println("File with same name already exists!!");
			System.out.println("press 1 to overwrite");
			System.out.println("press any other number to cancel");
			Integer in = sc1.nextInt();

			if (in == 1) {
				if (file.delete()) {
					// delete file to make new one with same name
				} else {
					System.out.println("something went wrong");
				}

				createfile(mapName);

			} else {
				System.out.println("cancelled");
			}

		}
		return file;
	}

	public void addContinenttodb(String continame) {
		

		String[] tempContinent = continame.split("\n");
		System.out.println("lenght" + tempContinent.length);
		String[] tosaveConti = new String[tempContinent.length];
		for(int i=1;i<tempContinent.length;i++) {
			
			String[] tempConti = tempContinent[i].split(" ");
		//	System.out.println(tempConti[0]);
			tosaveConti[i]=tempConti[0];
			//System.out.println("tosave" + tosaveConti[i]);
			
		}
	
		ArrayList<String> tosavec = new ArrayList<String>(Arrays.asList(tosaveConti));
		tosavec.remove(0);
		db.setcontinentNames(tosavec);

	}

	public void saveMap(Graph graph) throws IOException {

		ArrayList<Country> ct = graph.adjList;
		Iterator itr = ct.iterator();

		System.out.println(ct);

		Scanner scCreate = new Scanner(System.in);
		System.out.println("Enter new map name");

		String mapName = scCreate.nextLine();
		mapName = mapName + ".map";
		// Create the file
		File f = createfile(mapName);

		FileWriter writer = new FileWriter(f);
		writer.write("[files]" + System.getProperty("line.separator"));
		writer.write(System.getProperty("line.separator"));

		writer.write("[continents]" + System.getProperty("line.separator"));
		
		for(int i=0;i<db.getcontinentNames().size();i++) {
			writer.write( db.getcontinentNames().get(i) + System.getProperty("line.separator"));
		}
		writer.write(System.getProperty("line.separator"));
		
		writer.write("[countries] " + System.getProperty("line.separator"));
		writer.write(System.getProperty("line.separator"));
		Integer countitr = 0;
		while (itr.hasNext()) {
			Country country = (Country) itr.next();
			countitr++;
			String CountryName = country.name;
			Integer ContiNumber = country.inContinent ;
			Integer coordinateOne = country.coOrdinate1;
			Integer coordinateTwo = country.getCoOrdinate2;
			
			writer.write(countitr + " " + CountryName + " " + ContiNumber + " " + coordinateOne + " " + coordinateTwo + System.getProperty("line.separator"));
			
			
		}
		

		writer.write("[borders] " + System.getProperty("line.separator"));
		for (int num = 1; num < 2; num++) {
			writer.write("This is line " + num + System.getProperty("line.separator"));
		}

		writer.close();
		// TO DO
		// Save Model.Graph to .map file

	}

}