package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.properties.GamePropertiesAPI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Connection {
  
  private final static  Socket socket = new Socket();
  private static GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI();

  public static void initialize() throws IOException {
   socket.connect(new InetSocketAddress( gamePropertiesAPI.getValueByKey("ip"), gamePropertiesAPI.getIntValueByKey("port")), 5000);
  }

  //TODO PREVENT NPE
  public static OutputStream getOutputStream() throws IOException {
    return socket.getOutputStream();
  }

  //TODO PREVENT NPE
  public static InputStream getInputStream() throws IOException {
    return socket.getInputStream();
  }

  //TODO CHECK HOW TO CLOSE SOCKET
}
