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

  public Fleet getFleetLocation() {
    return fleet;
  }

  void play(TextArea textArea, TilePane playerBoard, TilePane enemyBoard) {

    Thread t = new Thread(() -> {
      String fromServer;

      try {
        while (!(fromServer = in.readLine()).equals("CLOSE")) {
          System.out.println("Server: " + fromServer);
          if (fromServer.startsWith("MESSAGE")) {
            String message = fromServer.substring(8);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("STOP")) {
            String message = "Game finished. Press finish to close";
            System.out.println(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("WIN")) {
            String message = "The enemy ship was hit. YOU WON!";
            System.out.println(message);
            String winField = fromServer.substring(3);
            Rectangle field = (Rectangle) playerBoard.getChildren().filtered(f -> f.getId().equals(winField)).get(0);
            Platform.runLater(() -> field.setFill(Color.YELLOW));
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("HIT")) {
            String message = "You've hit one of enemy's ships";
            System.out.println(message);
            String winField = fromServer.substring(3);
            Rectangle field = (Rectangle) playerBoard.getChildren().filtered(f -> f.getId().equals(winField)).get(0);
            Platform.runLater(() -> field.setFill(Color.YELLOW));
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("WELCOME")) {
            String message = Connection.getGamePropertiesAPI().getCurrentBundle().getString("welcomeMessage");
            Logger.info(message + "\n "
                + "Initial ships location: "
                + fleet.getShips() + "\n");
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("FIRST")) {
            if (fromServer.substring(6).equals("yes")) {
              String message = "You are first so now it's your turn! Please shot";
              System.out.println(message);
              Platform.runLater(() -> textArea.appendText(message + "\n"));
              Platform.runLater(() -> playerBoard.setDisable(false));
            } else {
              String message = "Your opponent starts the game. Wait a moment for his first move.";
              System.out.println(message);
              Platform.runLater(() -> textArea.appendText(message + "\n"));
            }
          } else if (fromServer.startsWith("MOVE")) {
            String fieldShot = fromServer.substring(4);
            String message1 = "Now your turn! Please shot";
            if (fleet.getShips().contains(Integer.parseInt(fieldShot))) {
              String message2 = "Your opponent shoot field number: " + fieldShot;
              String message3 = "Your ship was hit!";
              System.out.println(message2);
              Rectangle field = (Rectangle) enemyBoard.getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
              Platform.runLater(() -> {
                field.setFill(Color.YELLOW);
                textArea.appendText(message2 + "\n");
                textArea.appendText(message3 + "\n");
                textArea.appendText(message1 + "\n");
              });
              fleet.getShips().remove(Integer.parseInt(fieldShot));
              out.println("HIT" + fieldShot);
              if (fleet.getShips().isEmpty()) {
                Platform.runLater(() -> textArea.appendText("It was your last ship. YOU LOST! :(\n"));
                out.println("END" + fieldShot);
              } else Platform.runLater(() -> playerBoard.setDisable(false));
            } else {
              String message3 = "Your opponent has shoot field number: " + fieldShot;
              System.out.println(message3);
              System.out.println(message1);
              Platform.runLater(() -> textArea.appendText(message3 + "\n"));
              Rectangle field = (Rectangle) enemyBoard.getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
              Platform.runLater(() -> {
                field.setFill(Color.RED);
                playerBoard.setDisable(false);
                textArea.appendText(message1 + "\n");
              });
            }
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
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