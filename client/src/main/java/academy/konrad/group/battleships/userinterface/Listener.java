package academy.konrad.group.battleships.userinterface;

import java.io.IOException;
import java.io.ObjectInputStream;

class Listener {

  Object listen() throws IOException {

    ObjectInputStream ois = new ObjectInputStream(Connection.getConnection().getInputStream());
    Object fromServer = ois.read();
    ois.close();
    return fromServer;
  }
}
