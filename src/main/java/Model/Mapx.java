package Model;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This file holds most of the logic of the game
 */
public class Mapx {
	private String continents;
	protected String countries;
	protected String borders;
	Database database = Database.getInstance();

	private String territories;

	/**
	 * This reads the maps file and stores the country, continent and border details
	 * in their variables This is used by loadMap(). The variables generated
	 * by this method are used throughout the game.
	 *
	 * @param mapFile It is the name of the map file that is to be executed
	 * @throws FileNotFoundException Throws an exception if the file is not found
	 * @return true(If the method is executed completely)
	 */
	private boolean readMapIntoVariables(String mapFile) throws FileNotFoundException {
		// Read Continents
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
				Continent continent = new Continent(Database.getInstance().getContinentList().size() + 1, split[0],Integer.parseInt(split[1]), split[2]);
				database.getContinentList().add(continent);
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		catch(Exception e){

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
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		catch(Exception e){

		}

		// Read Borders
		try{
			BufferedReader br = new BufferedReader(new FileReader(mapFile));
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
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		catch(Exception e){

		}

		return true;
	}


	
	/**
	 * creates gameGraph of the map file provided gameGraph returned by this method
	 * is the most important variable in the whole game gameGraph is a Graph that
	 * holds an ArrayList of countries
	 *
	 * @param mapFile It is the name of the map file that is to be executed
	 * @param gameGraph This is an object of the class Graph
	 * @return true(If after executing, we are able to load the desired map.
	 */
	public boolean loadMap(String mapFile, Graph gameGraph)throws IOException {
		try {

			String fileType = recognizeFileType(mapFile);

			System.out.println("file format is: " + fileType);
			
			if (fileType=="Domination") {
				System.out.println("input file is in Domonation format");
//			    DominationMapFile readMapFile = new DominationMapFile();
//			    readMapFile.readMapIntoVariables(mapFile);
				readMapIntoVariables(mapFile);
			}
			else if (fileType=="Conquest") {
				System.out.println("input file is in Conquest format");
			    ConquestMapFile conquestMap = new ConquestMapFile();
			    MapReadWriteAdaptter readMapFile = new MapReadWriteAdaptter(conquestMap);
			    readMapFile.readMapIntoVariables(mapFile);
			}
			
			System.out.println("read file finished");
	
			
		} catch (FileNotFoundException f) {
			System.out.println(f.getMessage());
			return false;
		}


		gameGraph.getAdjList().clear(); // Clearing gameGraph before laoding new map

		gameGraph.getAdjList().clear(); //Clearing gameGraph before laoding new map

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
					Integer.parseInt(countryLineSubstrings[2]), null, 0, Integer.parseInt(countryLineSubstrings[3]),
					Integer.parseInt(countryLineSubstrings[4]), neighbours);
			gameGraph.getAdjList().add(country);
			
		}
		
		
		return true;
	}

	private String recognizeFileType(String mapFile) {
		
		String fileType = null;
		try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {

			String line = br.readLine().trim();

			while (line != null) {

				line = br.readLine();
				
				if (line.equals("[countries]")) {
					return fileType = "Domination";
				}
				else if(line.equals("[Territories]")){
					return fileType = "Conquest";
				}

			}
			
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (Exception e) {

		}
		return fileType;
        
	}
	

	/**
	 * This is a utility method that creates a plain text file
	 *
	 * @param mapName Name of the map
	 * @return file  
	 * @throws IOException If the Input or Output file is invalid 
	 */
	public static File createFile(String mapName) throws IOException {
		Scanner sc1 = new Scanner(System.in);
		File file = new File("src/main/resources/" + mapName);
		if (file.createNewFile()) {
			System.out.println("map saved");
		} else {
			System.out.println("File with same name already exists!!");
			System.out.println("press 1 to overwrite");
			System.out.println("press any other number to cancel");
			Integer in = 0;

			try {
				in = sc1.nextInt();
			} catch (Exception e) {
				System.out.println("Number Expected " + e.getMessage());
			}
			if (in == 1) {
				if (file.delete()) {
					// delete file to make new one with same name
				} else {
					System.out.println("something went wrong");
				}
				createFile(mapName);
			} else {
				System.out.println("cancelled");
			}
		}
		return file;
	}




	/**
	 * This method operates on the gameGraph variable and converts it to map file.
	 *
	 * @param gameGraph It is the object of the class Graph
	 * @throws IOException If the Input or Output file is invalid
	 * @return true(If the method executes and the map is saved) or false(If no map name is entered or is invalid)
	 */
	public boolean saveMap(Graph gameGraph, String mp) throws IOException {

		
		if (validateMap(gameGraph) == false) {
			return false;
		}

		if (mp.trim().length() == 0) {
			return false;
		}

        if(validateMap(gameGraph) == false){
            return false;
        }
        mp=mp.trim();
        if(mp.length()==0){

			System.out.println("Please enter a name for the map");
        	return false;
		}

		String[] DefaultMaps = { "map.map", "ameroki.map", "eurasien.map", "geospace.map", "lotr.map", "luca.map",
				"risk.map", "RiskEurope.map", "sersom.map", "teg.map", "tube.map", "uk.map", "world.map" , "conquestmap.map" };
		
		String mapName = mp.trim();
		boolean testEmptyString = "".equals(mapName);
		
		if (testEmptyString == false) {

			if (Arrays.asList(DefaultMaps).contains(mapName)) {
				System.out.println("you cannot edit a default map");
				return false;
			} else {
				
				File f = createFile(mapName);
				
				String fileType = "Domination";
				
				if (fileType=="Domination") {

				    DominationMapFile MapFile = new DominationMapFile();
				    MapFile.writeMapFile(gameGraph, mp, f);
				}
				else if (fileType=="Conquest") {
					System.out.println("input file is in Conquest format");
				    ConquestMapFile conquestMap = new ConquestMapFile();
				    MapReadWriteAdaptter MapFile = new MapReadWriteAdaptter(conquestMap);
				    MapFile.writeMapFile(gameGraph, mp, f);
				}
				
				
				
				return true;
			}
		} else {
			System.out.println("Please enter a valid map name!");
			return false;
		}

	}


	/**
	 * This method checks the gameGraph for graph connectivity
	 *
	 * @param gameGraph It is an object of the class Graph
	 * @return true(If the total count equals the total number of countries; Map is validated) or false(If the map is not connected) 
	 */
	public static boolean validateMap(Graph gameGraph) {
		Integer startPosition = 1;
		int count = 0;
		boolean visited[] = new boolean[gameGraph.getAdjList().size() + 1];
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(startPosition);

		while (stack.empty() == false) {
			Integer topElement = stack.peek();
			stack.pop();
			if (visited[topElement] == false) {
				count++;
				visited[topElement] = true;
			}

			Iterator<Integer> itr = gameGraph.getAdjList().get(topElement - 1).getNeighbours().iterator();
			while (itr.hasNext()) {
				Integer next = itr.next();
				if (visited[next] == false) {
					stack.push(next);
				}
			}
		}
		if (count == gameGraph.getAdjList().size()) {// if count==no. of countries return true;
			System.out.println("This map is valid.");
			return true;
		}
		System.out.println("This map is not connected.");
		return false;
	}

	public static boolean checkPath(String fromCountryName, String toCountryName, Graph gameGraph){
		Country toCountry = Country.getCountryByName(toCountryName, gameGraph);
		Country fromCountry = Country.getCountryByName(fromCountryName, gameGraph);
		System.out.println("To: "+toCountry.getName()+" owner "+toCountry.getOwner());
		System.out.println("From: "+fromCountry.getName()+" owner "+fromCountry.getOwner());
		System.out.println("CurrentPlayer: "+GamePlay.getInstance().getCurrentPlayerName());
		if (fromCountry == null || toCountry == null) {
			System.out.println("One or both countries do not exist");
			return false;
		}
		else if (!(toCountry.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName()) && fromCountry.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName()))){
			System.out.println("Both countries have to belong to current player");
			return false;
		}
        System.out.println("Good1");
		boolean pathExists= false;
		boolean visited[]=new boolean[gameGraph.getAdjList().size()+1];
		Queue<Country> queue= new LinkedList<Country>();
		visited[fromCountry.getNumber()]= true;
		queue.add(fromCountry);
        System.out.println("Good2");
		while(queue.size()!=0) {
            System.out.println("Looping with queue size "+queue.size());
			Country firstElement = queue.poll();
			if (firstElement.getName().equalsIgnoreCase(toCountry.getName()))
				return true;
			else {
				for (Integer neighbourNumber : firstElement.getNeighbours()) {
					Country neighbourCountry = Country.getCountryByNumber(neighbourNumber, gameGraph);

					if (neighbourCountry.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName()) && !visited[neighbourCountry.getNumber()]) {
						queue.add(neighbourCountry); //add only if path belongs to current player
					}
                    visited[neighbourCountry.getNumber()]=true;
				}
			}
		}
		return false;
	}




}