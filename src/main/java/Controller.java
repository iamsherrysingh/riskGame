public class Controller {
    public static void main(String[] args) {
        Mapx map= new Mapx();
//        Graph.printGraph(map.createGameGraph("map.map"));
        map.createGameGraph("map.map").printGraph();
    }
}
