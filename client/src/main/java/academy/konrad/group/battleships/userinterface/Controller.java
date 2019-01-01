package academy.konrad.group.battleships.userinterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

  private Board enemyBoard;
  private Board playerBoard;

  @FXML
  private Pane local;

  @FXML
  private Button connect;

  @FXML
  private HBox table;

  @FXML
  private TextArea console;

  @FXML
  private void start() {

    connect.setDisable(true);
    BattleshipClient client = new BattleshipClient();

    enemyBoard = new Board(event -> {

    });
    enemyBoard.fillBoard(100);

    playerBoard = new Board(event -> {
      Field field = (Field) event.getSource();
      field.setFill(Color.RED);
      field.setDisable(true);
      playerBoard.setDisable(true);
      client.shot(field.getId());
    });
    playerBoard.fillBoard(100);
    playerBoard.setDisable(true);

    HBox.setHgrow(enemyBoard, Priority.ALWAYS);
    HBox.setHgrow(playerBoard, Priority.ALWAYS);
    table.getChildren().addAll(enemyBoard, playerBoard);

    client.play(console, playerBoard, enemyBoard);

//      try {
//        Sender.send(new FieldNumber(field.getId()));
//      } catch (IOException exception) {
//        exception.printStackTrace();
//      }
//      updateEnemyBoard();
//      this.playerBoard.setDisable(false);
//    });
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