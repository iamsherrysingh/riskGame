public class tes {

    public static void main(String[] args) {
        System.out.println("Print something");
        System.out.println("Clear everything");
        System.out.flush();
        System.out.println("HelloWorld");

    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
