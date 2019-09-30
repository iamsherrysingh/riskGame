package Controller;
import Model.Mapx;

public class Controller {
    public static void main(String[] args) {
        Mapx map= new Mapx();
//        Model.Graph.printGraph(map.createGameGraph("map.map"));
        map.createGameGraph("src/main/resources/map.map").printGraph();
    }
}
