package academy.konrad.group.battleships.userinterface;

import java.io.IOException;
import java.io.ObjectInputStream;

class Listener {

  Object listen() {

    Object fromServer = null;
    try {
      ObjectInputStream ois = new ObjectInputStream(Connection.getConnection().getInputStream());
      fromServer = ois.readObject();
    }catch (IOException e){
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return fromServer;
  }
}
