package academy.konrad.group.battleships;

import java.io.*;

class Listener {

  Object listen() throws IOException {

    ObjectInputStream ois = new ObjectInputStream(Connection.getConnection().getInputStream());
    Object fromServer = ois.read();
    ois.close();
    return fromServer;
  }
}
