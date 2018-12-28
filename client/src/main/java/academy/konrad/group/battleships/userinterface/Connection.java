package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.properties.GamePropertiesAPI;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

class Connection {

  private static final Socket socket = new Socket();
  private static GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI();

  private Connection() {
  }

  static void initialize() throws IOException {
    InetSocketAddress socketAddress = new InetSocketAddress(
        gamePropertiesAPI.getValueByKey("ip"),
        gamePropertiesAPI.getIntValueByKey("port"));
    socket.connect(socketAddress, 5000);
  }

  static OutputStream getOutputStream() {
    try {
      return socket.getOutputStream();
    } catch (IOException exception) {
      Logger.error(exception.getMessage());
    }

    throw new IllegalStateException();
  }

  static InputStream getInputStream() {
    try {
      return socket.getInputStream();
    } catch (IOException exception) {
      Logger.error(exception.getMessage());
    }
    throw new IllegalStateException();
  }
}
