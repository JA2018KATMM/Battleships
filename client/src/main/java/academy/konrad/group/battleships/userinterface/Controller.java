package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.game_elements.BoardFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

  private TilePane enemyBoard;
  private TilePane playerBoard;

  @FXML
  private BorderPane borderPane;

  @FXML
  private Button test;

  @FXML
  private Label message;

  private static ObjectOutputStream objectOutputStream;
  private static ObjectInputStream objectInputStream;

  @FXML
  private void start() {

    Thread thread = new Thread(() -> {
      boolean shouldContinue = true;
      while (shouldContinue){
        if(objectInputStream == null){
          try {
            objectInputStream = new ObjectInputStream(Connection.getInputStream());
          } catch (IOException exception) {
            Logger.error(exception.getMessage());
          }
        }

        FieldNumber fieldNumber = null;
        try {
          fieldNumber = (FieldNumber) objectInputStream.readObject();
        }catch (ClassNotFoundException | IOException exception){
          Logger.error(exception.getMessage());
          shouldContinue = false;
        }
        if(fieldNumber != null){
          Platform.runLater(new Updater(fieldNumber, enemyBoard).getRunnable());
        }
      }
    });
    // don't let thread prevent JVM shutdown
    thread.setDaemon(true);
    thread.start();

    setUpBoards();

  }

  private void setUpBoards() {
    setUpEnemyBoard();
    setUpPlayerBoard();
    VBox vbox = new VBox(50, enemyBoard, playerBoard);
    vbox.setAlignment(Pos.CENTER);

    this.borderPane.setCenter(vbox);
  }

  private void setUpEnemyBoard() {
    this.enemyBoard = BoardFactory.getEnemyBoard(100);
  }

  private void setUpPlayerBoard() {
    this.playerBoard = BoardFactory.getPlayerBoard((event -> {
      Rectangle field = (Rectangle) event.getSource();
      field.setFill(Color.RED);
      field.setDisable(true);
      try {
        if(objectOutputStream == null){
          objectOutputStream = new ObjectOutputStream(Connection.getOutputStream());
        }
        objectOutputStream.writeObject(new FieldNumber(field.getId()));
        objectOutputStream.flush();
      } catch (IOException exception) {
        Logger.error(exception.getMessage());
      }
      this.playerBoard.setDisable(false);
    }),100);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
