package academy.konrad.group.battleships.userinterface;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Sender {

  void send(Object object) throws IOException {

    if (Connection.getConnection().isConnected()) {
      ObjectOutputStream oos = new ObjectOutputStream(Connection.getConnection().getOutputStream());
      oos.writeObject(object);
    }

  }
}
