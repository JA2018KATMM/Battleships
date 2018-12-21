package academy.konrad.group.battleships.userinterface;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

public class Controller implements Initializable {

  TilePane enemyBoard;
  private TilePane playerBoard;

  @FXML
  private BorderPane borderPane;

  @FXML
  private Button test;

  @FXML
  private Label message;

  private static ObjectOutputStream objectOutputStream;
  //private static ObjectInputStream objectInputStream;

  @FXML
  private void start() {

    enemyBoard = new EnemyBoard();
    ((EnemyBoard) this.enemyBoard).fillBoard(100);

    Thread thread = new Thread(new Runnable() {

      @Override
      public void run() {
        Runnable updater = new Runnable() {

          private int i = 0;

          @Override
          public void run() {
            for(Node elem : enemyBoard.getChildren()){
              if(elem.getId().equals(String.valueOf(i))) {
                Field field = (Field) elem;
                field.setFill(Color.RED);
                i++;
                return;
              }
            }
          }
        };

        while (true) {
          try {
            Thread.sleep(3000);
          } catch (InterruptedException ex) {
          }

          // UI update is run on the Application thread
          Platform.runLater(updater);
        }
      }

    });
    // don't let thread prevent JVM shutdown
    thread.setDaemon(true);
    thread.start();

    playerBoard = new Board(event -> {
      Field field = (Field) event.getSource();
      field.setFill(Color.RED);
      field.setDisable(true);
      try {
        if(objectOutputStream == null){
          objectOutputStream = new ObjectOutputStream(Connection.getOutputStream());
        }
        objectOutputStream.writeObject(new FieldNumber(field.getId()));
        objectOutputStream.flush();
      } catch (IOException exception) {
        exception.printStackTrace();
      }
      this.playerBoard.setDisable(false);
    });
    ((Board) this.playerBoard).fillBoard(100);

    VBox vbox = new VBox(50, enemyBoard, playerBoard);
    vbox.setAlignment(Pos.CENTER);

    this.borderPane.setCenter(vbox);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
