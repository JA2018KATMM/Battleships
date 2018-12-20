package academy.konrad.group.battleships.userinterface;

import java.io.IOException;
import java.io.ObjectOutputStream;


class Sender {
  static ObjectOutputStream oos;

  private Sender() {
  }

  static void send(Object object) throws IOException {
    if (oos == null)
      oos = new ObjectOutputStream(Connection.getOutputStream());
    oos.writeObject(object);
  }
}