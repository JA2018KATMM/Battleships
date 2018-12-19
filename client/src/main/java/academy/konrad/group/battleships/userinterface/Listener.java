package academy.konrad.group.battleships.userinterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

class Listener {

  Object listen() {

    Object fromServer = null;
    try {
      Socket socket = Connection.getConnection();
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      fromServer = ois.readObject();
    } catch (IOException | ClassNotFoundException exception) {
      exception.printStackTrace();
      return fromServer;
    }
    return fromServer;
  }
}
