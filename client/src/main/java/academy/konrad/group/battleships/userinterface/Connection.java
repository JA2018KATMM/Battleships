package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.properties.GamePropertiesAPI;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

class Connection {

  private static final Socket socket = new Socket();
  private static GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI();

  private Connection() {
  }

  //TODO zamiana na wczytywane z właściwości
  static void initialize() throws IOException {
    InetSocketAddress socketAddress = new InetSocketAddress
        (InetAddress.getByName("51.38.130.222"), 8081);
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
