package academy.konrad.group.battleships;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class Controller {

  @FXML
  private Label container;

  @FXML
  public void doMainButton() {
    System.out.println("Main button clicked");
  }

  @FXML
  private void loadTextFromServer() {
    String text = "Test";
    this.container.setText(text);
  }
}
