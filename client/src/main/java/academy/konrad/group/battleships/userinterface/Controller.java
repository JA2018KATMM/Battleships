package academy.konrad.group.battleships.userinterface;

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

  @FXML
  private Button connect;

  @FXML
  private void connect(){
    if(Connection.getConnection().isConnected()){
      secondClient();
    }else {
      this.message.setText("Nie ma połączenia");
    }
  }

  private void secondClient(){
    Object object = new Listener().listen();
    try {
      Boolean isSecondClient = (Boolean) object;
      if(isSecondClient){
        startAsFirstPlayer();
      }else{
        startAsSecondPlayer();
      }
    }catch (NullPointerException | ClassCastException exception){
      this.message.setText("Nie ma drugiego gracza");
    }
  }


  private void startAsFirstPlayer() {
    this.message.setText("");
    enemyBoard = new Board(event -> {

    });
    ((Board) this.enemyBoard).fillBoard(100);

    playerBoard = new Board(event -> {
      Field field = (Field) event.getSource();
      field.setFill(Color.RED);
      field.setDisable(true);
      this.message.setText("Czekam na drugiego gracza");
      sendField(field.getId());
      updateEnemyBoard();
      this.playerBoard.setDisable(false);
    });
    ((Board) this.playerBoard).fillBoard(100);

    VBox vbox = new VBox(50, enemyBoard, playerBoard);
    vbox.setAlignment(Pos.CENTER);

    this.borderPane.setCenter(vbox);
  }

  private void startAsSecondPlayer() {
    this.message.setText("");
    enemyBoard = new Board(event -> {

    });
    ((Board) this.enemyBoard).fillBoard(100);

    playerBoard = new Board(event -> {
      updateEnemyBoard();
      this.playerBoard.setDisable(false);
      Field field = (Field) event.getSource();
      field.setFill(Color.RED);
      field.setDisable(true);
      this.message.setText("Czekam na drugiego gracza");
      sendField(field.getId());

    });
    ((Board) this.playerBoard).fillBoard(100);

    VBox vbox = new VBox(50, enemyBoard, playerBoard);
    vbox.setAlignment(Pos.CENTER);

    this.borderPane.setCenter(vbox);
  }

  private void updateEnemyBoard() {

    this.playerBoard.setDisable(true);
    FieldNumber fieldNumber = (FieldNumber) new Listener().listen();
    String fieldToMark = fieldNumber.getFieldId();
    for (Node elem : this.enemyBoard.getChildren()) {
      if (elem.getId().equals(fieldToMark)) {
        Field field = (Field) elem;
        field.setFill(Color.RED);
        return;
      }
    }
  }

  private void sendField(String fieldId){

    try {
      new Sender().send(new FieldNumber(fieldId));
    } catch (IOException exception) {
      exception.printStackTrace();
    }

  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
