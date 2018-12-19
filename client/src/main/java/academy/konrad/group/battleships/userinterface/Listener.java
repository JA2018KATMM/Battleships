package academy.konrad.group.battleships.userinterface;

import java.io.IOException;
import java.io.ObjectInputStream;

import static academy.konrad.group.battleships.userinterface.Connection.getInputStream;

class Listener {

  static ObjectInputStream ois;

  static Object listen() throws IOException, ClassNotFoundException {

    Object fromServer = null;
    while (fromServer == null) {
      if (ois == null)
        ois = new ObjectInputStream(getInputStream());
      fromServer = ois.readObject();
      System.out.println(fromServer);
    }
    return fromServer;
  }
}