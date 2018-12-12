package academy.konrad.group.battleships.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class LoggedClient implements Runnable {
  private Socket clientSocket;
  private final DataInputStream dis;
  private final DataOutputStream dos;
  private final LoggedClientsSet loggedClientsSet;

  LoggedClient(Socket socket, DataInputStream is, DataOutputStream os, LoggedClientsSet clients) {
    this.clientSocket = socket;
    this.dis = is;
    this.dos = os;
    this.loggedClientsSet = clients;
  }

  @Override
  public void run() {

    while (true) {
      try {
        String message = dis.readUTF();

        if (message.equals("logout")) {
          this.clientSocket.close();
          break;
        }

        loggedClientsSet.informAll(message);

      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
    try {
      this.dos.close();
      this.dis.close();
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  void inform(String message) throws IOException {
    this.dos.writeUTF(message);
  }
}