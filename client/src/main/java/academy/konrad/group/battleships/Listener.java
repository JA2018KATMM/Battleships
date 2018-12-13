package academy.konrad.group.battleships;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

class Listener {

  private static Listener listener;
  private Socket socket = new Socket();

  private Listener(){

  }

  static Listener getListener(){
    if(listener == null){
      listener = new Listener();
    }
    return listener;
  }

  String listen() throws IOException {

    DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
    DataInputStream ois = new DataInputStream(socket.getInputStream());
    String message = ois.readUTF();

    socket.close();
    ois.close();
    oos.close();
    return message;
  }

  void connect() {
    try {
      socket.connect(new InetSocketAddress("51.38.130.222", 6666), 5000);
    }catch (IOException e){
      e.printStackTrace();
    }
  }

  boolean isConnected() {
    return socket.isConnected();
  }
}
