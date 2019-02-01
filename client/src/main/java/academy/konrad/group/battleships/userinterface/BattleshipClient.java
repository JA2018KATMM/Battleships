package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.domain.Fleet;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.pmw.tinylog.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

class BattleshipClient {

  private final BufferedReader in;
  private final Fleet fleet = new Fleet();
  private final PrintWriter out;

  BattleshipClient() {
    in = new BufferedReader(new InputStreamReader(Connection.getInputStream(), StandardCharsets.UTF_8));
    out = new PrintWriter(new OutputStreamWriter(Connection.getOutputStream(), StandardCharsets.UTF_8), true);
  }

  Fleet getFleetLocation() {
    return fleet;

  }

  void play(TextArea textArea, TilePane playerBoard, TilePane enemyBoard) {

    Thread t = new Thread(() -> {
      String fromServer;

      try {
        while (!(fromServer = in.readLine()).equals(Options.CLOSE.name())) {
          Logger.info("Server: " + fromServer);
          if (fromServer.startsWith(Options.WAIT.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("wait");
            Logger.info(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith(Options.MESSAGE.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString(fromServer.substring(8));
            Logger.info(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith(Options.STOP.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("finish");
            Logger.info(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith(Options.WIN.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("winner");
            Logger.info(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith(Options.HIT.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("enemyShipHit");
            Logger.info(message);
            String fieldHit = fromServer.substring(3);
            Rectangle field = (Rectangle) playerBoard.getChildren().filtered(f -> f.getId().equals(fieldHit)).get(0);
            Platform.runLater(() -> {
              field.setFill(Color.YELLOW);
              textArea.appendText(message + "\n");
            });
          } else if (fromServer.startsWith(Options.WELCOME.name())) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("welcomeMessage");
            Logger.info(message + "\n "
                + "Initial ships location: "
                + fleet.getShips() + "\n");
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith(Options.FIRST.name())) {
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
          } else if (fromServer.startsWith(Options.MOVE.name())) {
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
                out.println(Options.END.name());
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
      } catch (IOException e) {
        Logger.error(e.getMessage());
      }
    });
    t.start();
  }

  public void shot(String id) {
    out.println("MOVE" + id);
  }

  public void close() {
    out.println("FINISH");
  }

}