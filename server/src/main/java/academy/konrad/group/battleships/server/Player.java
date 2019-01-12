package academy.konrad.group.battleships.server;

import org.pmw.tinylog.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Player extends Thread {

  private BufferedReader input;
  private PrintWriter output;
  private Game game;

  //TODO resource bundle
  Player(Socket socket, Game game) {
    this.game = game;
    try {
      input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
      output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
      output.println("WELCOME");
      output.println("MESSAGE:Waiting for second player to connect");

    } catch (IOException e) {
      Logger.error("Player died: " + e);
    }
  }

  /**
   * This thread is only started after all 2 players connect.
   */
  public void run() {
    output.println("MESSAGE:All players connected");
    if (game.currentPlayer.equals(this))
      output.println("FIRST:yes");
    else output.println("FIRST:not");
      while (true) {
        try {
          String command = input.readLine();
          if (command == null) {
            break;
          } else if (command.startsWith("MOVE")) {
            System.out.println(command);
            game.waitingPlayer.output.println(command);
            game.currentPlayer.output.println("MESSAGE:Wait a moment, please. Now your opponent's turn.");
            game.changeTurn();
          } else if (command.startsWith("END")) {
            System.out.println(command);
            game.currentPlayer.output.println("STOP");
            game.waitingPlayer.output.println("WIN" + command.substring(3));
            game.waitingPlayer.output.println("STOP");
          } else if (command.startsWith("FINISH")) {
            System.out.println(command);
            output.println("CLOSE");
          } else if (command.startsWith("HIT")){
            System.out.println(command);
            game.waitingPlayer.output.println(command);
          }
        } catch (IOException e) {
          Logger.error("Player died: " + e);
        }
      }
  }
}