package academy.konrad.group.battleships;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class Controller {

  @FXML
  private Button main_button;

  @FXML
  private Label container;

  @FXML
  public void doMainButton() throws Exception {

    if(Listener.getListener().isConnected()){

      String text = Listener.getListener().listen();
      main_button.setText(text);
    }
  }


}
