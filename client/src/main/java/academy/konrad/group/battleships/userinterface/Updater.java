package academy.konrad.group.battleships.userinterface;

import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Updater {
  private final FieldNumber fieldNumber;
  private final TilePane boardToUpdate;

  Updater(FieldNumber fieldNumber, TilePane boardToUpdate) {
    this.fieldNumber = fieldNumber;
    this.boardToUpdate = boardToUpdate;
  }

  Runnable getRunnable() {

    return () -> {
      for (Node elem : boardToUpdate.getChildren()) {
        if (elem.getId().equals(fieldNumber.getFieldId())) {
          Rectangle field = (Rectangle) elem;
          field.setFill(Color.RED);
          return;
        }
      }
      throw new IllegalArgumentException("Nie ma takiego pola");
    };
  }

}
