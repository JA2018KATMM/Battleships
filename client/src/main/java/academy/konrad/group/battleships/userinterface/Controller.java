package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.domain.Fleet;
import academy.konrad.group.battleships.board.BoardFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;
import org.pmw.tinylog.Logger;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Odpowiada za interakcje z użytkownikiem.
 */
public class Controller implements Initializable {

  private TilePane enemyBoard;
  private TilePane playerBoard;
  private BattleshipClient client;

  @FXML
  private Pane local;

  @FXML
  private Button connect;

  @FXML
  private HBox table;

  @FXML
  private TextArea console;

  @FXML
  private Button end;

  private Fleet fleet = new Fleet();

  @FXML
  private void finish() {
    new Sender().send("FINISH");
    Stage stage = (Stage) end.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void start() {
    establishConnection();
    this.connect.setDisable(true);
    setUpBoards();
    MessageHandler messageHandler = new MessageHandler(this, this.fleet );
    this.client = new BattleshipClient(messageHandler);
    this.client.play();
    Logger.info("Start aplikacji");
  }

  void updateConsole(String text) {
    Platform.runLater(() -> this.console.appendText(text));
  }

  void setBoardAccess(boolean flag) {
    Platform.runLater(() -> this.playerBoard.setDisable(flag));
  }

  private void establishConnection() {
    if (Connection.initialize()) {
      return;
    }
    this.console.appendText("No connection");
  }

  private void setUpBoards() {
    setUpEnemyBoard();
    setUpPlayerBoard();
    HBox.setHgrow(this.enemyBoard, Priority.ALWAYS);
    HBox.setHgrow(this.playerBoard, Priority.ALWAYS);
    this.table.getChildren().addAll(enemyBoard, playerBoard);
  }

  private synchronized void setUpEnemyBoard() {
    this.enemyBoard = BoardFactory.getEnemyBoard(100);
    for (Integer location : fleet.getShips()) {
      Optional<Node> ship = this.enemyBoard.getChildren().stream().
          filter(field -> field.getId().equals(String.valueOf(location))).findFirst();
      if (ship.isPresent()) {
        Rectangle rectangleShip = (Rectangle) ship.get();
        rectangleShip.setFill(Color.LIMEGREEN);
      }
    }

  }

  private void setUpPlayerBoard() {
    this.playerBoard = BoardFactory.getPlayerBoard((event -> {
      Rectangle field = (Rectangle) event.getSource();
      field.setFill(Color.RED);
      field.setDisable(true);
      this.playerBoard.setDisable(true);
      new Sender().send("MOVE:" + field.getId());
    }), 100);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.console.setText(Connection.getGamePropertiesAPI().getCurrentBundle().getString("initial"));
    this.connect.setText(Connection.getGamePropertiesAPI().getCurrentBundle().
        getString("connectButton"));
    this.end.setText(Connection.getGamePropertiesAPI().getCurrentBundle().getString("endButton"));

  }

  void changeFieldColorOnPlayerBoard(String fieldHit, Color color) {
    Optional<Node> field = this.playerBoard.getChildren().stream().filter(f -> f.getId().equals(fieldHit)).
        findFirst();
    if(field.isPresent()) {
      Rectangle rectangleField = (Rectangle) field.get();
      Platform.runLater(() -> rectangleField.setFill(color));
    }
  }

  void changeFieldColorOnEnemyBoard(String fieldHit, Color color) {
    Optional<Node> field = this.enemyBoard.getChildren().stream().filter(f -> f.getId().equals(fieldHit)).
        findFirst();
    if(field.isPresent()) {
      Rectangle rectangleField = (Rectangle) field.get();
      Platform.runLater(() -> rectangleField.setFill(color));
    }
  }

}