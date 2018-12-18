package academy.konrad.group.battleships.userinterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

  private Board enemyBoard;
  private Board playerBoard;

  @FXML
  private BorderPane borderPane;

  @FXML
  private Button test;

  @FXML
  private void startTwo() {
    enemyBoard = new Board(event -> {

      CustomRectangle customRectangle = (CustomRectangle) event.getSource();
      customRectangle.setFill(Color.RED);
    });

    playerBoard = new Board(event -> {

    });

    VBox vbox = new VBox(50, enemyBoard, playerBoard);
    vbox.setAlignment(Pos.CENTER);

    this.borderPane.setCenter(vbox);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
