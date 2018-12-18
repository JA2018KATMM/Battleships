package academy.konrad.group.battleships.userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Field extends Rectangle {

  Field() {
    super(30, 30);
    setFill(Color.LIGHTGRAY);
    setStroke(Color.BLACK);
  }
}