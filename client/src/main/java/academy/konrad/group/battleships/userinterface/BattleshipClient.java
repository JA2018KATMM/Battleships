package academy.konrad.group.battleships.userinterface;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BattleshipClient {

  private static int PORT = 6666;
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;

  public BattleshipClient() {
    try {
      this.socket = new Socket("localhost", PORT);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
      out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void play(TextArea textArea, Board playerBoard, Board enemyBoard) {

    Thread t = new Thread(() -> {
      String fromServer;

      try {
        while ((fromServer = in.readLine()) != null) {

          if (fromServer.startsWith("MESSAGE")) {
            System.out.println("Server: " + fromServer);
            String message = fromServer;
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("WELCOME")) {
            String message = "Welome to the Battleship game!";
            System.out.println(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
          } else if (fromServer.startsWith("TURN")) {
            String message = "Now your turn! Please shot";
            System.out.println(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
            Platform.runLater(() -> playerBoard.setDisable(false));
          } else if (fromServer.startsWith("MOVE")) {
            String fieldShot = fromServer.substring(4);
            String message = "Opponent's shot: " + fieldShot;
            System.out.println(message);
            Platform.runLater(() -> textArea.appendText(message + "\n"));
            Field field = (Field) enemyBoard.getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
            Platform.runLater(() -> field.setFill(Color.RED));
          } else if (fromServer.equals("Bye."))
            break;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    t.start();
  }

  void shot(String id) {
    out.println("MOVE" + id);
  }
}