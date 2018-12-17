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
    for (int y = 0; y < 10; y++) {
      HBox row = new HBox();
      for (int x = 0; x < 10; x++) {
        CustomRectangle customRectangle = new CustomRectangle(x + y);
        customRectangle.setOnMouseClicked(handler);
        row.getChildren().add(customRectangle);
      }

      rows.getChildren().add(row);
    }

    getChildren().add(rows);
  }
}
