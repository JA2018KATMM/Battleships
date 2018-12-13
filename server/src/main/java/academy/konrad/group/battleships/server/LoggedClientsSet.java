package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.util.Vector;

class LoggedClientsSet {

  private final Vector<LoggedClient> threads = new Vector<>();

  synchronized boolean addClient(LoggedClient loggedClient) {
    return threads.add(loggedClient);
  }

  synchronized void informAll(String message) throws IOException {
    for (LoggedClient thread : this.threads) {
      thread.inform(message);
    }
  }

  void removeClient(LoggedClient loggedClient) {
    this.threads.remove(loggedClient);
  }
}