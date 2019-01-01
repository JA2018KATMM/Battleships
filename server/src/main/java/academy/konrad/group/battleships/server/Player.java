package academy.konrad.group.battleships.server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Player extends Thread {

  //  private Socket socket;
  private BufferedReader input;
  private PrintWriter output;
  private Game game;

  public Player(Socket socket, Game game) {
//    this.socket = socket;
    this.game = game;
    try {
      input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
      output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
      output.println("WELCOME");
      output.println("MESSAGE Waiting for second player to connect");

    } catch (IOException e) {
      System.out.println("Player died: " + e);
    }
  }

  public void run() {

    // The thread is only started after all 2 players connect.
    output.println("MESSAGE All players connected");
    if (game.currentPlayer.equals(this)) {
      output.println("TURN Your move");
    }
    while (true) {
      try {
        String command = input.readLine();
        if (command == null) {
          break;
        } else if (command.startsWith("MOVE")) {
          System.out.println(command);
          game.waitingPlayer.output.println(command);
          game.currentPlayer.output.println("MESSAGE Please wait now your opponent shots!");
          game.changeTurn();
          game.currentPlayer.output.println("TURN");
        }
      } catch (IOException e) {
        System.out.println("Player died: " + e);
      }
    }
  }
}