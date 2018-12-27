package academy.konrad.group.battleships.server;

import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ListenerThread extends Thread {

  private static final int PORT_NUMBER = 8081;
  private static final List<Socket> clients = new ArrayList<>();

  @Override
  public void run() {
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(PORT_NUMBER);
    } catch (IOException exception) {
      System.out.println("Exception caught when trying to listen on port "
          + PORT_NUMBER + " or listening for a connection");
      exception.printStackTrace();
    }

    while (!Thread.currentThread().isInterrupted()) {
      try {
        Socket clientSocket = serverSocket.accept();
        SingleApp singleApp = new SingleApp(clientSocket);
        Thread thread = new Thread(singleApp);
        Logger.error("Jest watek");
        thread.start();

        /*if (clientSocket != null) {
          clients.add(clientSocket);
        }
        if (clients.size() == 2) {
          SingleGame clientsPair = new SingleGame(clients.get(0), clients.get(1));
          clients.clear();
          System.out.println(clients.size());
          Thread thread = new Thread(clientsPair);
          thread.start();
        }
        */

      } catch (IOException exception) {
        exception.printStackTrace();
        Thread.currentThread().interrupt();
      }
    }
  }
}
