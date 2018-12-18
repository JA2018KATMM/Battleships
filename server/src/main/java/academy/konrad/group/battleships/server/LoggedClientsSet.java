package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.util.Vector;

class LoggedClientsSet {

  private final Vector<ClientsPair> threads = new Vector<>();

  synchronized boolean addClient(ClientsPair clientsPair) {
    return threads.add(clientsPair);
  }

  synchronized void informAll(String message) throws IOException {
    for (ClientsPair thread : this.threads) {
      //thread.inform(message);
    }
  }

  void removeClient(ClientsPair clientsPair) {
    this.threads.remove(clientsPair);
  }
}