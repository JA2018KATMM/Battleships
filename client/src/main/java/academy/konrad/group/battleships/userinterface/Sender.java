package academy.konrad.group.battleships.userinterface;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

 class Sender {

  void send(Object object) throws IOException {

    if (Connection.getConnection().isConnected()) {
      Socket socket = Connection.getConnection();
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(object);
    }

  }
}
