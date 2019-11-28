package Model;
import Model.*;

import java.io.FileReader;

public class testing {
    public static void main(String[] args) throws Exception{
        GamePlay.getInstance().loadGameMap("map2.map");
        GamePlay.getInstance().fileType="Conquest";
        GamePlay.getInstance().saveMap("aaa.map");

    }
}