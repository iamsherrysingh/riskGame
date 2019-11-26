package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class saveGame {


    public static File createFile(String fileName) throws IOException {
        Scanner sc1 = new Scanner(System.in);
        File file = new File("src/main/resources/saveGameState" + fileName);
        if (file.createNewFile()) {
            System.out.println("file saved");
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
                createFile(fileName);
            } else {
                System.out.println("cancelled");
            }
        }
        return file;
    }



    public boolean savePlayerList(ArrayList<IPlayer> PList, String mp) throws IOException {

        PList = Database.playerList;
        mp = mp.trim();
        if (mp.length() == 0) {
            System.out.println("Please enter a name for the map");
            return false;
        }
        String mapName = mp.trim();


        boolean testEmptyString = "".equals(mapName);
        if (testEmptyString == false) {


            // Create the file
            File f = createFile(mapName);
            FileWriter writer = new FileWriter(f);

            for (int i = 0; i < PList.size(); i++) {
                writer.write(PList.get(i).getName() + System.getProperty("line.separator"));
            }
            writer.close();
            return true;
        } else {
            System.out.println("Please enter a valid map name!");
            return false;


        }

    }}
