import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Map {
    public static void main(String ar[]) throws FileNotFoundException {

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

            String continents = sb.toString();
            continents=continents.trim();
            System.out.println(continents);
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

            String countries = sb.toString();
            countries=countries.trim();
            System.out.println(countries);
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

            String borders = sb.toString();
            borders=borders.trim();
            System.out.println(borders);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}