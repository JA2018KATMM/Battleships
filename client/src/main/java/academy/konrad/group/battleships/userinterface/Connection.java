package academy.konrad.group.battleships.userinterface;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Connection {

  private static final Socket socket = new Socket();
  private static Connection connection;

  Connection() {
    //TODO Obluga braku połączenia w gui
    initialize();
  }

  static Socket getConnection() {
    if (connection == null) {
      connection = new Connection();
    }
    return socket;
  }

  boolean initialize() {
    try {
      socket.connect(new InetSocketAddress("localhost", 8081), 5000);
      return true;
    } catch (IOException exception) {
      exception.printStackTrace();
      return false;
    }
  }

}
