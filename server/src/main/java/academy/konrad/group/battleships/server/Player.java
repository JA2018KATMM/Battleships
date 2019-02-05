package academy.konrad.group.battleships.server;

import academy.konrad.group.battleships.message.Message;
import academy.konrad.group.battleships.message.MessageParser;
import org.pmw.tinylog.Logger;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Player extends Thread {

  private BufferedReader input;
  private PrintWriter output;
  private Game game;

  //TODO resource bundle
  Player(Socket socket, Game game) {
    this.game = game;
    try {
      input = new BufferedReader
          (new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
      output = new PrintWriter
          (new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
      output.println("WELCOME");
      output.println("MESSAGE:awaitPlayer");

    } catch (IOException exception) {
      Logger.error("Player died: " + exception);
    }
  }

  /**
   * This thread is only started after all 2 players connect.
   */
  public void run() {
    beforeGame();
    boolean isGame = true;

    while (isGame) {
      try {
        String command = input.readLine();

        if (command != null) {
          String title = MessageParser.getMessageTitle(command);
          String content = MessageParser.getMessageContent(command);
          Optional<Message> option = MessageParser.findChosenOption(title);
          Message message;
          if (option.isPresent()) {
            message = option.get();
            runServerOption(message, content);
          }
        }
      } catch (IOException exception) {
        Logger.error("Player died: " + exception);
        isGame = false;
      }
    }

  }

  private void runServerOption(Message message, String content) {
    switch (message) {
      case MOVE:
        if (game.currentPlayer.equals(this)) game.waitingPlayer.output.println(message + ":" + content);
        else game.currentPlayer.output.println(message + ":" + content);
//        game.changeTurn();
        break;
      case CLOSE:
        break;
      case WAIT:
        break;
      case FINISH:
        output.println("CLOSE");
        break;
      case END:
        game.currentPlayer.output.println("STOP");
        game.waitingPlayer.output.println("WIN:" + content);
        game.waitingPlayer.output.println("STOP");
        break;
      case HIT:
        game.waitingPlayer.output.println(message + ":" + content);
        break;
      default:
        throw new IllegalStateException();

    }
  }

  private void beforeGame() {
    output.println("MESSAGE:all");
    if (game.currentPlayer.equals(this)) {
      output.println("FIRST:yes");
    } else {
      output.println("FIRST:not");
    }
  }
}