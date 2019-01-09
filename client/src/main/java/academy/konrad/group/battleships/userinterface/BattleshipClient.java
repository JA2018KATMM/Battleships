package academy.konrad.group.battleships.userinterface;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

class BattleshipClient {

  private final BufferedReader in;
  private final int shipLocation = new Random().nextInt(100);
  private final PrintWriter out;

  BattleshipClient() {
    in = new BufferedReader(new InputStreamReader(Connection.getInputStream(), StandardCharsets.UTF_8));
    out = new PrintWriter(new OutputStreamWriter(Connection.getOutputStream(), StandardCharsets.UTF_8), true);
  }

  public int getShipLocation() {
    return shipLocation;
  }

  void play(TextArea textArea, TilePane playerBoard, TilePane enemyBoard) {

    Thread t = new Thread(() -> {
      String fromServer;

      try {
        while (!(fromServer = in.readLine()).equals("CLOSE")) {

          if (fromServer.startsWith("MESSAGE")) {
            System.out.println("Server: " + fromServer);
            String message = fromServer;
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("STOP")) {
            String message = "Game finished. Press finish to close";
            System.out.println(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("WIN")) {
            String message = "The enemy ship was hit. YOU WON!";
            System.out.println(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("WELCOME")) {
            String message = "Welome to the Battleship game!";
            System.out.println(message);
            System.out.println("Ship location: " + shipLocation);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("TURN")) {
            String message = "Now your turn! Please shot";
            System.out.println(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
            Platform.runLater(() -> playerBoard.setDisable(false));
          } else if (fromServer.startsWith("MOVE")) {
            String fieldShot = fromServer.substring(4);
            String message = "Now your turn! Please shot";
            System.out.println(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
            if (fieldShot.equals(String.valueOf(this.shipLocation))) {
              String message2 = "Your ship was hit! YOU LOST!";
              String message3 = "Opponent's shot: " + fieldShot;
              System.out.println(message2);
              Platform.runLater(() -> {
                textArea.appendText(message2 + "\n");
                textArea.appendText(message3 + "\n");
              });
              Rectangle field = (Rectangle) enemyBoard.getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
              Platform.runLater(() -> field.setFill(Color.YELLOW));
              out.println("END");
            } else {
              String message3 = "Opponent's shot: " + fieldShot;
              System.out.println(message3);
              Platform.runLater(() -> textArea.appendText(message3 + "\n"));
              Rectangle field = (Rectangle) enemyBoard.getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
              Platform.runLater(() -> field.setFill(Color.RED));
              Platform.runLater(() -> playerBoard.setDisable(false));
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