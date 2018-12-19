package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.properties.GamePropertiesAPI;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Connection {

  private static final Socket socket = new Socket();
  private static Connection connection;
  private GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI();

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
      socket.connect(new InetSocketAddress( gamePropertiesAPI.getValueByKey("ip"), gamePropertiesAPI.getIntValueByKey("port")), 5000);
      return true;
    } catch (IOException exception) {
      exception.printStackTrace();
      return false;
    }
  }

}
