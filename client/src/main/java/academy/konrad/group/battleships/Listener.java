package academy.konrad.group.battleships;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

class Listener {

  private static Socket socket;

  static String listen() throws IOException {

    DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
    DataInputStream ois = new DataInputStream(socket.getInputStream());
    String message = ois.readUTF();

    socket.close();
    ois.close();
    oos.close();
    return message;
  }

  static void connect() throws IOException {
    InetAddress host = InetAddress.getLocalHost();
    socket = new Socket(host.getHostName(), 8081);
  }

  static boolean isConnected() {
    return (socket != null && !socket.isClosed());
  }
}
