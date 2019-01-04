package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.properties.GamePropertiesAPI;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class Connection {

  private static final Socket socket = new Socket();
  private static GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI();

  private Connection() {
  }

  //TODO zamiana na wczytywane z właściwości
  static boolean initialize()  {
    InetSocketAddress socketAddress = null;
    try {
      socketAddress = new InetSocketAddress
          (InetAddress.getLocalHost(), 8081);
      socket.connect(socketAddress, 5000);
      return true;
    } catch (IOException e) {
      Logger.error(e.getMessage());
    }
    return false;
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
