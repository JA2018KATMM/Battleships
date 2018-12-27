package academy.konrad.group.battleships.server;

import edu.emory.mathcs.backport.java.util.Collections;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ListenerThread extends Thread {

  private static final int PORT_NUMBER = 8081;
  private static final List<Socket> clients = Collections.synchronizedList(new ArrayList());

  @Override
  public void run() {
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(PORT_NUMBER);
    } catch (IOException exception) {
      Logger.error("Exception caught when trying to listen on port "
          + PORT_NUMBER + " or listening for a connection");
      exception.printStackTrace();
    }

    while (!Thread.currentThread().isInterrupted()) {
      try {
        Socket clientSocket = serverSocket.accept();
        if(clientSocket != null){
          Logger.error(clientSocket.toString());
          clients.add(clientSocket);
        }
        if (clients.size() == 2) {
          SingleGame clientsPair = new SingleGame(clients.get(0), clients.get(1));
          clients.clear();
          Logger.error(clients.size());
          Thread thread = new Thread(clientsPair);
          thread.start();
        }


      } catch (IOException exception) {
        exception.printStackTrace();
        Thread.currentThread().interrupt();
      }
    }
  }
}
