package academy.konrad.group.battleships.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
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
      } catch (EOFException exception) {
        // do nothing
      } catch (IOException exception) {
        exception.printStackTrace();
        close();
        return;
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

  void close() {
    try {
      this.dis.close();
      this.dos.close();
      clientSocket.close();
      loggedClientsSet.removeClient(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}