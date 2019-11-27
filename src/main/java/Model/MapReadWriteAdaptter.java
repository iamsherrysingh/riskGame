package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/** 
 * This is the adapter pattern to handle conquest and domination file.
 */
public class MapReadWriteAdaptter extends DominationMapFile{
	
	     Database database = Database.getInstance();
	     protected String continents;
	     protected String territories;
	     
	     private ConquestMapFile conquestMap;

		 public MapReadWriteAdaptter(ConquestMapFile conquestMap) {
	     this.conquestMap = conquestMap;
	     }

	     
         /**
	      * This delegates the read method of Domination to the conquest maps file.
	      *
	      * @param mapFile It is the name of the map file that is to be executed
	      * @throws FileNotFoundException Throws an exception if the file is not found
	      * @return true(If the method is executed completely)
	      */
	      public boolean readMapIntoVariables(String mapFile) throws FileNotFoundException{

		  conquestMap.readMapConquest(mapFile);
		  
		  continents = conquestMap.getContinents();
		  territories = conquestMap.getTerritories();

		  convertConquestToDominate();
		  
		  return true;
	      }


		 /**
		  * This delegates the write method of Domination to the conquest maps file.
		  *
		  * @param gameGraph It is the graph of the game
		  * @param mapFile It is the name of the map file that is to be executed
		  * @param f the file which is used to write data on that
		  * @throws FileNotFoundException Throws an exception if the file is not found
		  * @return true(If the method is executed completely)
		  */
		  public boolean writeMapFile(Graph gameGraph, String mapName, File f) throws IOException{
			  
			  convertDominateToConquest(gameGraph);
			  
				
			  conquestMap.setContinents(continents);
		
			  conquestMap.setTerritories(territories);

			  
				System.out.println("--------here------");
				
				System.out.println(conquestMap.getContinents());
				System.out.println(conquestMap.getTerritories());
				
				
			  conquestMap.writeMapConquest(mapName, f);
			  
			  return true;
		  }
	

		 /**
		  * To convert conquest map data to domination map file.
		  *
		  * @throws FileNotFoundException Throws an exception if the file is not found
		  * @return true(If the method is executed completely)
		  */
		  private boolean convertConquestToDominate() {
	
				String continentLine[] = continents.split("\n");
				for (int i = 1; i < continentLine.length; i++) {
	
					continentLine[i] = continentLine[i].trim();
	
					String split[] = continentLine[i].split("=");
	
					Continent continent = new Continent
					(Database.getInstance().getContinentList().size() + 1, split[0], Integer.parseInt(split[1]), "red");
	
					database.getContinentList().add(continent);
					
				}
	
				countries = "[countries]" + "\n";

				int sizeOfContinents = database.getContinentList().size();
				 
				String[] territoriesLine = territories.split("\n");
				String[] countryName= new String[territoriesLine.length];
				for (int i = 0; i < territoriesLine.length; i++) {
	                
					territoriesLine[i] = territoriesLine[i].trim();
	
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
	
	
				continents="[continents]\n";
				for(Continent continent: Database.getInstance().getContinentList()){
					continents+=(continent.getName() +" "+continent.getControlValue()+" "+continent.getColor())+"\n";
				}
				continents=continents.trim();
				countries= countries.trim();
				borders= borders.trim();
	
			  return true;
	
			}
	  
		  
		 /**
		  * To convert domination map data to conquest map file.
		  *
		  * @throws FileNotFoundException Throws an exception if the file is not found
		  * @return true(If the method is executed completely)
		  */
		  private boolean convertDominateToConquest(Graph gameGraph) {
	
				continents = null;
				for (Continent continent : Database.getInstance().getContinentList()) {
					continents += continent.getName() + "=" +  continent.getControlValue() + "\n";
				}
				
				
				territories = null;
				for (Country country : gameGraph.getAdjList()) {
					
					ArrayList<Integer> neib = country.getNeighbours();

					
					territories += country.getName() + "," + 
				                   country.getCoOrdinate1() + "," + 
					               country.getCoOrdinate2 + "," +  
					               country.getInContinent() + ",";
					
					for ( int i=1; i<neib.size(); i++ ) {
						territories += country.getCountryByNumber(i,gameGraph).getName() + ",";
					}
					territories += "\n";
					
				}
				
				return true;
			}
				
	}
