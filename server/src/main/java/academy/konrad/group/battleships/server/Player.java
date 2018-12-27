package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Player extends Thread {

  //  private Socket socket;
  private boolean isFirst;
  //  private BufferedReader input;
  private PrintWriter output;

  public Player(Socket socket, boolean isFirst) {
//    this.socket = socket;
    this.isFirst = isFirst;
    try {
//      input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
      output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
      output.println("WELCOME");
      output.println("MESSAGE Waiting for opponent to connect");

    } catch (IOException e) {
      System.out.println("Player died: " + e);
    }
  }

  public void run() {

    // The thread is only started after everyone connects.
    output.println("MESSAGE All players connected");
    if (isFirst) {
      output.println("MESSAGE Your move");
    }

  }
}