package academy.konrad.group.battleships;

public class App {
  public static void main(String[] args) {
    Listener.getListener().connect();
    Main.main(args);
  }
}
