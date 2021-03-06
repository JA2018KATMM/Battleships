package academy.konrad.group.battleships.server;

import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.ServerSocket;

class ListenerThread extends Thread {

  private static final int PORT_NUMBER = 8081;

  @Override
  public void run() {

    try (
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
    ) {
      Logger.info("Battleships are running!");

      while (!Thread.currentThread().isInterrupted()){
        Game game = new Game();
        Player firstPlayer = new Player(serverSocket.accept(), game);
        Player secondPlayer = new Player(serverSocket.accept(), game);
        game.currentPlayer = firstPlayer;
        game.waitingPlayer = secondPlayer;
        firstPlayer.start();
        secondPlayer.start();
      }

    } catch (IOException e) {
      Logger.error("Could not listen on port " + PORT_NUMBER);
      Logger.error(e.getMessage());
    }
  }
}
