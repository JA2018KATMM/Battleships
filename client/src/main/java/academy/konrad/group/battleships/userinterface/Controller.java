package academy.konrad.group.battleships.userinterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

//  private TilePane enemyBoard;
//  private TilePane playerBoard;

  @FXML
  private BorderPane borderPane;

  @FXML
  private Button connect;

  @FXML
  private Label message;

  @FXML
  private TextArea console;

  @FXML
  private void start() {

    connect.setDisable(true);
    BattleshipClient client = new BattleshipClient();
    client.play(console);
//    enemyBoard = new Board(event -> {
//
//    });
//    ((Board) this.enemyBoard).fillBoard(100);
//
//    playerBoard = new Board(event -> {
//      Field field = (Field) event.getSource();
//      field.setFill(Color.RED);
//      field.setDisable(true);
//      try {
//        Sender.send(new FieldNumber(field.getId()));
//      } catch (IOException exception) {
//        exception.printStackTrace();
//      }
//      updateEnemyBoard();
//      this.playerBoard.setDisable(false);
//    });
//    ((Board) this.playerBoard).fillBoard(100);
//
//    VBox vbox = new VBox(50, enemyBoard, playerBoard);
//    vbox.setAlignment(Pos.CENTER);
//
//    this.borderPane.setCenter(vbox);
  }

//  private void updateEnemyBoard() {
//
//    this.playerBoard.setDisable(true);
//    FieldNumber fieldNumber = null;
//    try {
//      fieldNumber = (FieldNumber) Listener.listen();
//    } catch (IOException | ClassNotFoundException e) {
//      e.printStackTrace();
//    }
//    String fieldToMark = null;
//    if (fieldNumber != null) {
//      fieldToMark = fieldNumber.getFieldId();
//    }
//    for (Node elem : this.enemyBoard.getChildren()) {
//      if (elem.getId().equals(fieldToMark)) {
//        Field field = (Field) elem;
//        field.setFill(Color.RED);
//        return;
//      }
//    }
//  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.console.setText("Press Connect to START the game\n");
  }
}