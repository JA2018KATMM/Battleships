package academy.konrad.group.battleships.server;

public class Main {
    public static void main(String[] args) {
        Thread.currentThread().setName("MainThread");
        new ServerAdministrator().runServer();
    }

}
