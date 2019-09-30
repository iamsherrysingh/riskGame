package Controller;
import Model.Mapx;

import java.util.Scanner;

public class Controller {
    public static void main(String[] args) {
        Controller controller= new Controller();
        controller.startGame();
    }
    void startGame(){
        Mapx map= new Mapx();
        map.createGameGraph("src/main/resources/map.map").printGraph();
        Scanner commandScanner= new Scanner(System.in);
        System.out.print ("Enter number of Players: ");
        Integer numberOfPlayers= Integer.parseInt(commandScanner.nextLine().trim());

    }
}
