package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.domain.Fleet;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import academy.konrad.group.battleships.message.Message;
import academy.konrad.group.battleships.message.MessageHandler;
import org.pmw.tinylog.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

class BattleshipClient {

  private final BufferedReader in;
  private final Fleet fleet = new Fleet();
  private final PrintWriter out;
  private boolean isRun = true;
  private TextArea textArea;
  private TilePane playerBoard;
  private TilePane enemyBoard;

  BattleshipClient() {
    this.in = new BufferedReader(new InputStreamReader(Connection.getInputStream(), StandardCharsets.UTF_8));
    this.out = new PrintWriter(new OutputStreamWriter(Connection.getOutputStream(), StandardCharsets.UTF_8), true);

  }

  Fleet getFleetLocation() {
    return fleet;
  }

  void play(TextArea textArea, TilePane playerBoard, TilePane enemyBoard) {
    this.textArea = textArea;
    this.playerBoard = playerBoard;
    this.enemyBoard = enemyBoard;

    Thread t = new Thread(() -> {
      try {
        while (isRun) {
          String fromServer = in.readLine();
          String title = MessageHandler.getMessageTitle(fromServer);
          String content = MessageHandler.getMessageContent(fromServer);
          Optional<Message> option = MessageHandler.findChosenOption(title);
          Message message;
          if (option.isPresent()) {
            message = option.get();
            runOption(message, content);
          }
        }
      } catch (IOException e) {
        Logger.error(e.getMessage());
        isRun = false;
      }
    });
    t.start();
  }


  private void runOption(Message option, String content) {
    switch(option) {
      case MOVE:
        doMove(content);
        break;
      case WELCOME:
        String messageStart = getMessage("welcomeMessage");
        logStart(messageStart);
        showMessageOnTextArea(messageStart);
        break;
      case CLOSE:
        this.isRun = false;
        break;
      case WAIT:
        break;
      case MESSAGE:
        String message = getMessage(content);
        showMessageOnTextArea(message);
        break;
      case STOP:
        String textStop = getMessage("finish");
        showMessageOnTextArea(textStop);
        break;
      case WIN:
        String text = getMessage("winner");
        showMessageOnTextArea(text);
        break;
      case HIT:
        doHit(content);
        break;
      case FIRST:
        doFirst(content);
        break;
        default:
          throw new IllegalStateException();
        
    }

  }

  private void doFirst(String answer) {
    if (answer.equals("yes")) {
      String message = getMessage("firstTurn");
      Logger.info(message);
      Platform.runLater(() -> {
        textArea.appendText(message + "\n");
        playerBoard.setDisable(false);
      });
    } else {
      String message = getMessage("secondTurn");
      Logger.info(message);
      Platform.runLater(() -> textArea.appendText(message + "\n"));
    }
  }

  private void doHit(String fieldHit) {
    String message = getMessage("enemyShipHit");
    Logger.info(message);
    
    Rectangle field = (Rectangle) playerBoard.getChildren().filtered(f -> f.getId().equals(fieldHit)).get(0);
    Platform.runLater(() -> {
      field.setFill(Color.YELLOW);
      textArea.appendText(message + "\n");
    });
  }

  private void showMessageOnTextArea(String text){
    Logger.info(text);
    Platform.runLater(() -> textArea.appendText(text + "\n"));
  }

  private void logStart(String message) {

    Logger.info(message + "\n "
        + "Initial ships location: "
        + fleet.getShips() + "\n");
  }

  private void doMove(String fieldShot) {
    String message1 = getMessage("yourTurn");
    String message2 = getMessage("yourShipHit");
    String message3 = getMessage("fieldShoot") + fieldShot;
    if (this.fleet.getShips().contains(Integer.parseInt(fieldShot))) {
      Logger.info(message3 + "\n" + message2 + "\n" + message1);
      Rectangle field = (Rectangle) this.enemyBoard.getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
      Platform.runLater(() -> {
        field.setFill(Color.YELLOW);
        textArea.appendText(message3 + "\n"
            + message2 + "\n"
            + message1 + "\n");
      });
      fleet.getShips().remove(Integer.parseInt(fieldShot));
      out.println("HIT:" + fieldShot);
      if (fleet.getShips().isEmpty()) {
        String message4 = getMessage("lastShip");
        Logger.info(message4);
        Platform.runLater(() -> textArea.appendText(message4 + "\n"));
        out.println("END");
      } else Platform.runLater(() -> playerBoard.setDisable(false));
    } else {
      Logger.info(message3 + "\n" + message1);
      Rectangle field = (Rectangle) enemyBoard.getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
      Platform.runLater(() -> {
        field.setFill(Color.RED);
        textArea.appendText(message3 + "\n"
            + message1 + "\n");
        playerBoard.setDisable(false);
      });
    }
    
  }

  private String getMessage(String key) {
    return Connection.getGamePropertiesAPI().getCurrentBundle().getString(key);
  }

  void shot(String id) {
    out.println("MOVE:" + id);
  }

  void close() {
    out.println("FINISH");
  }

}