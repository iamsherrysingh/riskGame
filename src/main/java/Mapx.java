import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Mapx {
    private String continents, countries, borders;
    Mapx()
    {
        continents=countries= borders ="";
    }
    private void loadMap() throws FileNotFoundException {

        try(BufferedReader br = new BufferedReader(new FileReader("map.map"))) {
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

///////////////////////////////

        try(BufferedReader br = new BufferedReader(new FileReader("map.map"))) {
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

///////////////////////////

        try(BufferedReader br = new BufferedReader(new FileReader("map.map"))) {
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

    public Graph graphRepresentation()
    {
        try
        {
            loadMap();
        }
        catch (FileNotFoundException f)
        {
            System.out.println(f.getMessage());
        }

        Graph gameGraph= new Graph(new ArrayList<Country>());

        //Scan countries and add
        //Create country object and borders from this.neighbours
        Scanner countryScanner= new Scanner(this.countries);
        countryScanner.nextLine(); //Ignoring first line of this.countries
        while(countryScanner.hasNext()) {
            String lineCountry= countryScanner.nextLine();
            lineCountry= lineCountry.trim();
            String countryLineSubstrings[]= lineCountry.split(" ");
            System.out.println(countryLineSubstrings[0]+"*"+countryLineSubstrings[4]);
            ArrayList<Integer> neighbours= new ArrayList<Integer>();
            Scanner borderScanner= new Scanner(this.borders);
            borderScanner.nextLine();
            while(borderScanner.hasNext())
            {
                String lineBorder= borderScanner.nextLine();
                lineBorder= lineBorder.trim();
                String borderLineSubstrings[]= lineBorder.split(" ");
                if(Integer.parseInt(borderLineSubstrings[0])==Integer.parseInt(countryLineSubstrings[0])){
                    for(int i=1;i<borderLineSubstrings.length;i++){
                        neighbours.add(Integer.parseInt(borderLineSubstrings[i]));
                    }

                }

            }

            Country country= new Country(Integer.parseInt(countryLineSubstrings[0]), countryLineSubstrings[1] ,Integer.parseInt(countryLineSubstrings[2]) ,null ,null ,Integer.parseInt(countryLineSubstrings[3]) ,Integer.parseInt(countryLineSubstrings[4]) ,neighbours);

        }

        return null;

    }

    public static void main(String[] args) {
        Mapx m= new Mapx();
        m.graphRepresentation();
    }
}