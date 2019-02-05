package academy.konrad.group.battleships.server;

import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.ServerSocket;

class ListenerThread extends Thread {

  private static final int PORT_NUMBER = 8081;
  private int clientCounter = 1;
  private int gameCounter = 0;

  @Override
  public void run() {

    try (
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
    ) {
      Logger.info("Battleships are running!");

      while (!Thread.currentThread().isInterrupted()) {
        Game game = new Game();
        gameCounter++;
        Player firstPlayer = new Player(serverSocket.accept(), game);
        game.currentPlayer = firstPlayer;
        Player secondPlayer = new Player(serverSocket.accept(), game);
        game.waitingPlayer = secondPlayer;
        firstPlayer.setName("Player number: " + clientCounter);
        Logger.info("Player number: " + clientCounter + " added to game number: " + gameCounter);
        clientCounter++;
        secondPlayer.setName("Player number: " + clientCounter);
        Logger.info("Player number: " + clientCounter + " added to game number: " + gameCounter);
        clientCounter++;
        firstPlayer.start();
        secondPlayer.start();
        System.out.println("*** GAME STARTS ***");
      }

    } catch (IOException exception) {
      Logger.error("Could not listen on port " + PORT_NUMBER);
      Logger.error(exception.getMessage());
    }
  }
}
