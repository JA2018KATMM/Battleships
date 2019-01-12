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
          } else if (fromServer.startsWith("WELCOME")) {
            String message = "Welome to the Battleship game!";
            System.out.println(message);
            System.out.println("Ship location: " + shipLocation);
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
            if (fieldShot.equals(String.valueOf(this.shipLocation))) {
              String message2 = "Your opponent shoot field number: " + fieldShot;
              String message3 = "Your ship was hit! YOU LOST!";
              System.out.println(message2);
              Platform.runLater(() -> {
                textArea.appendText(message2 + "\n");
                textArea.appendText(message3 + "\n");
              });
              Rectangle field = (Rectangle) enemyBoard.getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
              Platform.runLater(() -> field.setFill(Color.YELLOW));
              out.println("END" + fieldShot);
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