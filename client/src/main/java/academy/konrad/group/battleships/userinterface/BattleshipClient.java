package academy.konrad.group.battleships.userinterface;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class BattleshipClient {

  private static int PORT = 6666;
  private Socket socket;
  private BufferedReader in;
//  private PrintWriter out;

  public BattleshipClient() {
    try {
      this.socket = new Socket("localhost", PORT);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
//      out = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void play(TextArea textArea) {

    Thread t = new Thread(() -> {
      String fromServer;

      try {
        while ((fromServer = in.readLine()) != null) {
          System.out.println("Server: " + fromServer);
          String message = fromServer;
          Platform.runLater(() -> textArea.appendText(message + "\n"));

          if (fromServer.equals("Bye."))
            break;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    t.start();
  }
}