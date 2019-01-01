package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ListenerThread extends Thread {

  private static final int PORT_NUMBER = 6666;
  private static final List<Socket> clients = new ArrayList<>();

  @Override
  public void run() {

    try (
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
    ) {
      System.out.println("Battleships are running!");

      while (true){
        Game game = new Game();
        Player firstPlayer = new Player(serverSocket.accept(), game);
        Player secondPlayer = new Player(serverSocket.accept(), game);
        game.currentPlayer = firstPlayer;
        game.waitingPlayer = secondPlayer;
        firstPlayer.start();
        secondPlayer.start();
      }

    } catch (IOException e) {
      System.err.println("Could not listen on port " + PORT_NUMBER);
      e.printStackTrace();
    }

//    try {
//      serverSocket = new ServerSocket(PORT_NUMBER);
//    } catch (IOException exception) {
//      System.out.println("Exception caught when trying to listen on port "
//          + PORT_NUMBER + " or listening for a connection");
//      exception.printStackTrace();
//    }
//
//    while (!Thread.currentThread().isInterrupted()) {
//      try {
//        Socket clientSocket = serverSocket.accept();
//        if (clientSocket != null) {
//          clients.add(clientSocket);
//        }
//        if (clients.size() == 2) {
//          SingleGame clientsPair = new SingleGame(clients.get(0), clients.get(1));
//          clients.clear();
//          System.out.println(clients.size());
//          Thread thread = new Thread(clientsPair);
//          thread.start();
//        }
//
//      } catch (IOException exception) {
//        exception.printStackTrace();
//        Thread.currentThread().interrupt();
//      }
//    }
  }
}
