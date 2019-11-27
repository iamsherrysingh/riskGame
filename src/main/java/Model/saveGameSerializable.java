package Model;
import java.io.*;

public class saveGameSerializable{
    public static void main(String[] args)
    {
        GamePlay.getInstance().loadGameMap("map.map");

        Country.addCountry("jaskaran","Asia",Graph.getInstance());
        Country.addCountry("birjot","Asia",Graph.getInstance());
        Country.addCountry("sehaj","Africa",Graph.getInstance());
        File f = null;

        // Serialization
        try
        {
            //System.out.println("11");
            //Saving of object in a file
             f = new File("/src/main/resources/saveGameState");
            FileOutputStream file = new FileOutputStream("/Users/jaskaransodhi/APP Project/riskGame/src/main/resources/saveGameState");
            ObjectOutputStream out = new ObjectOutputStream(file);
            System.out.println("11");

            // Method for serialization of object
            out.writeObject(Graph.getInstance());
            out.close();
            out.flush();
            file.close();

            System.out.println("Object has been serialized");

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }


        GamePlay.getInstance().getGraphObj().getAdjList().clear();
        Graph object1 = Graph.getInstance();
        object1.getAdjList().clear();

        // Deserialization
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("/Users/jaskaransodhi/APP Project/riskGame/src/main/resources/saveGameState");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            object1 = (Graph) in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized ");

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

      //  String c = object1.getAdjList().get(0).name;
//                Graph.getInstance().getAdjList().get(0).name;

        //System.out.println(c);
        System.out.println(object1.getAdjList().size());
    }
}
