package academy.konrad.group.battleships.userinterface;

import java.io.IOException;
import java.io.ObjectOutputStream;

import static academy.konrad.group.battleships.userinterface.Connection.getOutputStream;

class Sender {

  static ObjectOutputStream oos;

  static void send(Object object) throws IOException {
    if (oos == null)
      oos = new ObjectOutputStream(getOutputStream());
    oos.writeObject(object);
  }
}