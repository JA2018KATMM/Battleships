package academy.konrad.group.battleships.userinterface;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.Serializable;

class Board extends Parent implements Serializable {

  private VBox rows = new VBox();

  Board(EventHandler<? super MouseEvent> handler) {
    for (int i = 0; i < 10; i++) {
      HBox row = new HBox();
      for (int j = 0; j < 10; j++) {
        CustomRectangle customRectangle = new CustomRectangle();
        customRectangle.setId(String.valueOf((i * 10) + j + 1));
        customRectangle.setOnMouseClicked(handler);
        row.getChildren().add(customRectangle);
      }

      rows.getChildren().add(row);
    }

    getChildren().add(rows);
  }
}
