package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.game_elements.BoardFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.pmw.tinylog.Logger;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;
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

  @FXML
  private void finish(){
    this.client.close();
  }

  @FXML
  private void start() {
    establishConnection();
    this.connect.setDisable(true);
    this.client = new BattleshipClient();
    setUpBoards();
    this.client.play(this.console, this.playerBoard, this.enemyBoard);
    Logger.info("Start aplikacji");
  }

  private void establishConnection() {
    if(Connection.initialize()) {
//      out = new PrintWriter(new OutputStreamWriter(Connection.getOutputStream(), StandardCharsets.UTF_8), true);
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
    int location = this.client.getShipLocation();
    Rectangle ship = (Rectangle) this.enemyBoard.getChildren().filtered(field -> field.getId().equals(String.valueOf(location))).get(0);
    ship.setFill(Color.LIMEGREEN);
  }

  private void setUpPlayerBoard() {
    this.playerBoard = BoardFactory.getPlayerBoard((event -> {
      Rectangle field = (Rectangle) event.getSource();
      field.setFill(Color.RED);
      field.setDisable(true);
      this.playerBoard.setDisable(true);
      this.client.shot(field.getId());
    }),100);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.console.setText("Press Connect to START the game\n");
  }
}