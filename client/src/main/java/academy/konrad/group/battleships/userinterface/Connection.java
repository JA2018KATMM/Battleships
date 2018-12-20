package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.properties.GamePropertiesAPI;

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

  static OutputStream getOutputStream() throws IOException {
    return socket.getOutputStream();
  }

  static InputStream getInputStream() throws IOException {
    return socket.getInputStream();
  }
}
