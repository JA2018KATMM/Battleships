package academy.konrad.group.battleships.server;

import edu.emory.mathcs.backport.java.util.Collections;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class ListenerThread extends Thread {

  private static final int PORT_NUMBER = 8081;
  @SuppressWarnings("unchecked")
  private static final List<Socket> clients = Collections.synchronizedList(new ArrayList<Socket>());

  @Override
  public void run() {
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(PORT_NUMBER);
    } catch (IOException exception) {
      Logger.error(exception.getMessage());
    }

    while (!Thread.currentThread().isInterrupted()) {
      try {
        Socket clientSocket = Objects.requireNonNull(serverSocket).accept();
        if (clientSocket != null) {
          clients.add(clientSocket);
          Logger.info("Klient number " + clients.size() + " " + clientSocket.toString());
        }
        if (clients.size() == 2) {
          SingleGame clientsPair = new SingleGame(clients.get(0), clients.get(1));
          clients.clear();
          Thread thread = new Thread(clientsPair);
          thread.start();
        }


      } catch (IOException exception) {
        Logger.error(exception.getMessage());
        Thread.currentThread().interrupt();
      }
    }
  }
}
