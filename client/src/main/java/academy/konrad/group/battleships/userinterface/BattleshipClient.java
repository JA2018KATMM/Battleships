package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.domain.Fleet;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.commons.lang.StringUtils;
import org.pmw.tinylog.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
    in = new BufferedReader(new InputStreamReader(Connection.getInputStream(), StandardCharsets.UTF_8));
    out = new PrintWriter(new OutputStreamWriter(Connection.getOutputStream(), StandardCharsets.UTF_8), true);
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
          System.out.println(fromServer);
          String title = getMessageTitle(fromServer);
          String content = getMessageContent(fromServer);
          Optional<Message> option = findChosenOption(title);
          Message message;
          if (option.isPresent()) {
            message = option.get();
            runOption(message, content);
          }
        }
        /*
        while (!(fromServer = in.readLine()).equals(Message.CLOSE.name())) {
          Logger.info("Server: " + fromServer);
          if (fromServer.startsWith(Message.WAIT.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("wait");
            Logger.info(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith(Message.MESSAGE.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString(fromServer.substring(8));
            Logger.info(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith(Message.STOP.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("finish");
            Logger.info(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith(Message.WIN.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("winner");
            Logger.info(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith(Message.HIT.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("enemyShipHit");
            Logger.info(message);
            String fieldHit = fromServer.substring(3);
            Rectangle field = (Rectangle) playerBoard.getChildren().filtered(f -> f.getId().equals(fieldHit)).get(0);
            Platform.runLater(() -> {
              field.setFill(Color.YELLOW);
              textArea.appendText(message + "\n");
            });
          } else if (fromServer.startsWith(Message.WELCOME.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("welcomeMessage");
            Logger.info(message + "\n "
                + "Initial ships location: "
                + fleet.getShips() + "\n");
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith(Message.FIRST.name())) {
            if (fromServer.substring(6).equals("yes")) {
              String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("firstTurn");
              Logger.info(message);
              Platform.runLater(() -> {
                textArea.appendText(message + "\n");
                playerBoard.setDisable(false);
              });
            } else {
              String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("secondTurn");
              Logger.info(message);
              Platform.runLater(() -> textArea.appendText(message + "\n"));
            }
          } else if (fromServer.startsWith(Message.MOVE.name())) {
            String fieldShot = fromServer.substring(4);
            String message1 = Connection.getGamePropertiesAPI().getCurrentBundle().getString("yourTurn");
            String message2 = Connection.getGamePropertiesAPI().getCurrentBundle().getString("yourShipHit");
            String message3 = Connection.getGamePropertiesAPI().getCurrentBundle().getString("fieldShoot") + fieldShot;
            if (fleet.getShips().contains(Integer.parseInt(fieldShot))) {
              Logger.info(message3 + "\n" + message2 + "\n" + message1);
              Rectangle field = (Rectangle) enemyBoard.getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
              Platform.runLater(() -> {
                field.setFill(Color.YELLOW);
                textArea.appendText(message3 + "\n"
                    + message2 + "\n"
                    + message1 + "\n");
              });
              fleet.getShips().remove(Integer.parseInt(fieldShot));
              out.println("HIT" + fieldShot);
              if (fleet.getShips().isEmpty()) {
                String message4 = Connection.getGamePropertiesAPI().getCurrentBundle().getString("lastShip");
                Logger.info(message4);
                Platform.runLater(() -> textArea.appendText(message4 + "\n"));
                out.println(Message.END.name());
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


        }
        */
      } catch (IOException e) {
        Logger.error(e.getMessage());
      }
    });
    t.start();
  }

  private String getMessageContent(String fromServer) {
    return StringUtils.substringAfter(fromServer, ":");
  }

  private String getMessageTitle(String fromServer) {
    return StringUtils.substringBefore(fromServer, ":");
  }

  private void runOption(Message option, String content) {
    switch(option) {
      case MOVE:
        doMove(content);
        break;
      case WELCOME:
        doWelcome();
        break;
      case CLOSE:
        break;
      case WAIT:
        break;
      case MESSAGE:
        String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString(content);
        doMessage(message);
        break;
      case STOP:
        doStop();
        break;
      case WIN:
        doWin();
        break;
      case HIT:
        doHit(content);
        break;
      case FIRST:
        doFirst(content);
        break;
        
    }

}

  private void doFirst(String answer) {
    if (answer.equals("yes")) {
      String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("firstTurn");
      Logger.info(message);
      Platform.runLater(() -> {
        textArea.appendText(message + "\n");
        playerBoard.setDisable(false);
      });
    } else {
      String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("secondTurn");
      Logger.info(message);
      Platform.runLater(() -> textArea.appendText(message + "\n"));
    }
  }

  private void doHit(String fieldHit) {
    String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("enemyShipHit");
    Logger.info(message);
    
    Rectangle field = (Rectangle) playerBoard.getChildren().filtered(f -> f.getId().equals(fieldHit)).get(0);
    Platform.runLater(() -> {
      field.setFill(Color.YELLOW);
      textArea.appendText(message + "\n");
    });
  }

  private void doWin() {
    String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("winner");
    Logger.info(message);
    Platform.runLater(() -> textArea.appendText(message + "\n"));
  }

  private void doStop() {
    String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("finish");
    Logger.info(message);
    Platform.runLater(() -> textArea.appendText(message + "\n"));
  }

  private void doMessage(String fromServer) {
    
    Logger.info(fromServer);
    Platform.runLater(() -> textArea.appendText(fromServer + "\n"));
  }


  private void doWelcome() {
    String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("welcomeMessage");
    Logger.info(message + "\n "
        + "Initial ships location: "
        + fleet.getShips() + "\n");
    Platform.runLater(() -> this.textArea.appendText(message + "\n"));
  }

  private void doMove(String fieldShot) {
    String message1 = Connection.getGamePropertiesAPI().getCurrentBundle().getString("yourTurn");
    String message2 = Connection.getGamePropertiesAPI().getCurrentBundle().getString("yourShipHit");
    String message3 = Connection.getGamePropertiesAPI().getCurrentBundle().getString("fieldShoot") + fieldShot;
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
      out.println("HIT" + fieldShot);
      if (fleet.getShips().isEmpty()) {
        String message4 = Connection.getGamePropertiesAPI().getCurrentBundle().getString("lastShip");
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

  private Optional<Message> findChosenOption(String fromServer) {
    return Arrays.stream(Message.values()).filter(e -> e.name().equals(fromServer)).findFirst();
  }

  public void shot(String id) {
    out.println("MOVE:" + id);
  }

  public void close() {
    out.println("FINISH");
  }

}