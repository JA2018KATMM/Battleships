package academy.konrad.group.battleships.userinterface;

import java.io.IOException;
import java.io.ObjectInputStream;

class Listener {

  static ObjectInputStream ois;

  private Listener() {
  }

  static Object listen() throws IOException, ClassNotFoundException {

    Object fromServer = null;
    while (fromServer == null) {
      if (ois == null)
        ois = new ObjectInputStream(Connection.getInputStream());
      fromServer = ois.readObject();
      System.out.println(fromServer);
    }
    return fromServer;
  }
}