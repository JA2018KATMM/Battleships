package academy.konrad.group.battleships.server;

import academy.konrad.group.battleships.properties.GamePropertiesAPI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ListenerThread extends Thread {

  private static final int PORT_NUMBER = new GamePropertiesAPI().getIntValueByKey("port");
  private static final List<Socket> clients = new ArrayList<>();

  public void run() {
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(PORT_NUMBER);
    } catch (IOException exception) {
      System.out.println("Exception caught when trying to listen on port "
          + PORT_NUMBER + " or listening for a connection");
      exception.printStackTrace();
    }

    boolean shouldContinue = true;

    while (shouldContinue) {
      try {
        Socket clientSocket = serverSocket.accept();
        if (clientSocket != null) {
          clients.add(clientSocket);
        }
        if (clients.size() == 2) {
          SingleGame clientsPair = new SingleGame(clients.get(0), clients.get(1));

          Thread thread = new Thread(clientsPair);
          thread.start();
        }

      } catch (IOException exception) {
        exception.printStackTrace();
        shouldContinue = false;
      }
    }
  }
}
