package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Mapx {
    private String continents, countries, borders;

    private void loadMap(String mapFile) throws FileNotFoundException {

        //Read Continents
        try(BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int continentsEncountered=0;

            while (line != null) {
                if(line.equals("[countries]"))
                    break;
                if(continentsEncountered==1) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                if(line.equals("[continents]")) {
                    continentsEncountered=1;
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                line = br.readLine();
            }

            continents = sb.toString();
            continents=continents.trim();
//            System.out.println(continents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Read countries
        try(BufferedReader br = new BufferedReader(new FileReader("src/map.map"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int countriesEncountered=0;

            while (line != null) {
                if(line.equals("[borders]"))
                    break;
                if(countriesEncountered==1)
                {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                if(line.equals("[countries]")) {
                    countriesEncountered=1;
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                line = br.readLine();
            }

            countries = sb.toString();
            countries=countries.trim();
//            System.out.println(countries);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Read Borders
        try(BufferedReader br = new BufferedReader(new FileReader("src/map.map"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int bordersEncountered=0;

            while (line != null) {
                if(bordersEncountered==1)
                {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                if(line.equals("[borders]")) {
                    bordersEncountered=1;
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

    //Create the main gameGraph
    public Graph createGameGraph(String mapFile)
    {
        try
        {
            loadMap(mapFile);
        }
        catch (FileNotFoundException f)
        {
            System.out.println(f.getMessage());
        }

        Graph gameGraph= new Graph(new ArrayList<Country>());

        Scanner countryScanner= new Scanner(this.countries);
        countryScanner.nextLine(); //Ignoring first line of this.countries
        while(countryScanner.hasNext()) {
            String lineCountry= countryScanner.nextLine();
            lineCountry= lineCountry.trim();
            String countryLineSubstrings[]= lineCountry.split(" ");

            ArrayList<Integer> neighbours= new ArrayList<Integer>();
            Scanner borderScanner= new Scanner(this.borders);
            borderScanner.nextLine(); //Ignoring first line of this.borders
            while(borderScanner.hasNext())
            {
                String lineBorder= borderScanner.nextLine();
                lineBorder= lineBorder.trim();
                String borderLineSubstrings[]= lineBorder.split(" ");
                if(Integer.parseInt(borderLineSubstrings[0])==Integer.parseInt(countryLineSubstrings[0])){
                    for(int i=1;i<borderLineSubstrings.length;i++){
                        neighbours.add(Integer.parseInt(borderLineSubstrings[i]));
                    }
                    break;
                }
            }
            Country country= new Country(Integer.parseInt(countryLineSubstrings[0]), countryLineSubstrings[1] ,Integer.parseInt(countryLineSubstrings[2]) ,null ,null ,Integer.parseInt(countryLineSubstrings[3]) ,Integer.parseInt(countryLineSubstrings[4]) ,neighbours);
            gameGraph.getAdjList().add(country);
        }
        return gameGraph;
    }

    //For Birjot
    void saveMap(Graph graph){
        //TO DO
        //Save Model.Graph to .map file

    }

}