package Model;

import java.io.*;

public class ConquestMap extends Mapx{
	
	  Database database = Database.getInstance();

	  public  String continents;
	  private String territories;


	  public boolean readMapIntoVariables(String mapFile) throws FileNotFoundException{
		  
		  readMapConquest(mapFile);


		  convertConquestToDominate();
		  
		  return true;
	  }

	  
	  public boolean writeMapFile(Graph gameGraph, String mapName, File f) throws IOException{
		  
		  convertDominateToConquest(gameGraph);
		  
		  writeMapConquest(mapName, f);
		  
		  return true;
	  }

  
	  private boolean convertConquestToDominate() {
			System.out.println(" convertConquestToDominate started-----");

			String continentLine[] = continents.split("\n");
			for (int i = 1; i < continentLine.length; i++) {

				continentLine[i] = continentLine[i].trim();

				String split[] = continentLine[i].split("=");

				Continent continent = new Continent
				(Database.getInstance().getContinentList().size() + 1, split[0], Integer.parseInt(split[1]), "red");

				database.getContinentList().add(continent);
				
			}

			/*
			System.out.println("========start of territories========" );
			System.out.println(database.getContinentList());
			System.out.println("========end of territories========" );
			*/

			countries = "[countries]";
			countries += "\n";
			
			int sizeOfContinents = database.getContinentList().size();
			
			String territoriesLine[] = territories.split("\n");
			String[] countryName= new String[territoriesLine.length];
			for (int i = 0; i < territoriesLine.length; i++) {
                
				territoriesLine[i] = territoriesLine[i].trim();

				// hamid
				if(territoriesLine[i].equalsIgnoreCase(" ")) {
					continue;
				}
				
				
				String split[] = territoriesLine[i].split(",");

				int continetIndx = 0;
				for(int j=0; j<sizeOfContinents;j++) {
					
					Continent continent = database.getContinentList().get(j);
					if( continent.getName().equalsIgnoreCase(split[3]) ) {
						continetIndx = j+1;
					}
					
				}
				
				countries += i+1 + "," + split[0] + "," + continetIndx + ","  + split[1] + "," + split[2] + "\n";
				
				countryName[i] = split[0];
			}

			/*
			System.out.println("========start of countries========" );
			System.out.println(countries);
			System.out.println("========end of countries========" );
			*/
		

			int numOfCountries = territoriesLine.length;

			borders = "[borders]";
			borders += "\n";
			for (int i = 0; i < numOfCountries; i++) {
			
				borders += i+1 + " ";

				territoriesLine[i] = territoriesLine[i].trim();
				String split[] = territoriesLine[i].split(",");

				for(int j=4; j<split.length;j++) {
					String name1 = split[j];
					
					for (int k = 0; k < numOfCountries; k++) {
						String name2 = countryName[k];

						if( name1.equalsIgnoreCase(name2) ) {
							borders += k+1 + " ";
							break;
						}
						
					}

				}
				
				borders += "\n";
			
			}


			System.out.println("=======================");
			continents="[continents]\n";
			for(Continent continent: Database.getInstance().getContinentList()){
				continents+=(continent.getName() +" "+continent.getControlValue()+" "+continent.getColor())+"\n";
			}
			continents=continents.trim();
			countries= countries.trim();
			borders= borders.trim();
//			for(String s:countryName){
//				System.out.println(s);
//			}
		/*  System.out.println(continents);
		  System.out.println(countries);
		  System.out.println(borders);
*/
		  return true;



		}
  
	  
	  private boolean convertDominateToConquest(Graph gameGraph) {

			continents = "[Continents]";
			countries += "\n";
			for (Continent continent : Database.getInstance().getContinentList()) {
				continents += continent.getName() + "=" +  continent.getControlValue() + "\n";
			}
			
			
			territories = "[Territories]" + "\n";
			for (Country country : gameGraph.getAdjList()) {
				territories += country.getName() + "," + 
			                   country.getCoOrdinate1() + "," + 
				               country.getCoOrdinate2 + "," +  
				               country.getInContinent() + "," +
				               country.getNeighbours() + "\n";
			}
			
			return true;
		}
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
