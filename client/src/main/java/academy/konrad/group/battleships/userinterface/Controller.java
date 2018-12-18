package academy.konrad.group.battleships.userinterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
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
  private Button read;

  @FXML
  private void startTwo() {
    enemyBoard = new Board(event -> {

    });
    ((Board) this.enemyBoard).fillBoard(100);

    playerBoard = new Board(event -> {
      Field field = (Field) event.getSource();
      field.setFill(Color.RED);
      try {
        new Sender().send(new FieldNumber(field.getId()));
      } catch (IOException e) {
        e.printStackTrace();
      }
      updateEnemyBoard();

    });
    ((Board) this.playerBoard).fillBoard(100);

    VBox vbox = new VBox(50, enemyBoard, playerBoard);
    vbox.setAlignment(Pos.CENTER);

    this.borderPane.setCenter(vbox);
  }

  private void updateEnemyBoard() {

    FieldNumber fieldNumber = (FieldNumber) new Listener().listen();
    String fieldToMark = fieldNumber.getFieldId();
    for(Node elem : this.enemyBoard.getChildren()){
      if(elem.getId().equals(fieldToMark)){
        Field field = (Field) elem;
        field.setFill(Color.RED);
        return;
      }
    }
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
