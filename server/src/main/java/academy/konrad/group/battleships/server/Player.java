package academy.konrad.group.battleships.server;

import academy.konrad.group.battleships.userinterface.Message;
import academy.konrad.group.battleships.userinterface.MessageHandler;
import org.pmw.tinylog.Logger;

import java.io.*;
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
      input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
      output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
      output.println("WELCOME");
      output.println("MESSAGE:awaitPlayer");

    } catch (IOException e) {
      Logger.error("Player died: " + e);
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
            String title = MessageHandler.getMessageTitle(command);
            String content = MessageHandler.getMessageContent(command);
            Optional<Message> option = MessageHandler.findChosenOption(title);
            Message message;
            if (option.isPresent()) {
              message = option.get();
              runServerOption(message, content);
            }
          }
        } catch (IOException e) {
          Logger.error("Player died: " + e);
          isGame = false;
        }
      }

  }

  private void runServerOption(Message message, String content) {
    switch(message) {
      case MOVE:
        game.waitingPlayer.output.println(message + ":" + content);
        game.currentPlayer.output.println("WAIT");
        game.changeTurn();
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

    }
  }

  private void beforeGame() {
    output.println("MESSAGE:all");
    if (game.currentPlayer.equals(this))
      output.println("FIRST:yes");
    else output.println("FIRST:not");
  }
}