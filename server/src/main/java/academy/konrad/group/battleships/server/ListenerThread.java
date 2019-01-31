package academy.konrad.group.battleships.server;

import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ListenerThread extends Thread {

  private static final int PORT_NUMBER = 8081;
  private static int clientCounter = 0;

  @Override
  public void run() {

    try (
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
    ) {
      Logger.info("Battleships are running!");

      while (!Thread.currentThread().isInterrupted()){
        Game game = new Game();
        Socket firstPlayeSocket = serverSocket.accept();
        firstPlayeSocket.setSoTimeout(5_000);
        Player firstPlayer = new Player(firstPlayeSocket, game);
        firstPlayer.setName("Player" + clientCounter);
        Logger.info("Client number " + clientCounter );
        clientCounter++;
        Socket secondPlayerSocket = serverSocket.accept();
        secondPlayerSocket.setSoTimeout(5_000);
        Player secondPlayer = new Player(secondPlayerSocket, game);
        secondPlayer.setName("Player" + clientCounter);
        Logger.info("Client number " + clientCounter );
        clientCounter++;
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
