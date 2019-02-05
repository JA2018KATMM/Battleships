package academy.konrad.group.battleships.efficiencytest;

import academy.konrad.group.battleships.properties.GamePropertiesAPI;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

class Connection {

  private Socket socket;
  private static GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI();

  Connection() {
    this.socket = initialize();
  }

  Socket initialize()  {
    Socket socket = new Socket();
    try {
      InetSocketAddress socketAddress =
          new InetSocketAddress("localhost", 8081);
      socket.connect(socketAddress, 5000);
      return socket;
    } catch (IOException exception) {
      Logger.error(exception.getCause());
    }
    return null;
  }

  OutputStream getOutputStream() {
    try {
      return socket.getOutputStream();
    } catch (IOException exception) {
      Logger.error(exception.getMessage());
    }

    throw new IllegalStateException();
  }

  InputStream getInputStream() {
    try {
      return socket.getInputStream();
    } catch (IOException exception) {
      Logger.error(exception.getMessage());
    }
    throw new IllegalStateException();
  }

  static GamePropertiesAPI getGamePropertiesAPI() {
    return gamePropertiesAPI;
  }

  static String getMessage(String key) {
    return gamePropertiesAPI.getCurrentBundle().getString(key);
  }
}
