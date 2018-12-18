package academy.konrad.group.battleships.userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Field extends Rectangle {

  public Field() {
    super(30, 30);
    setFill(Color.LIGHTGRAY);
    setStroke(Color.BLACK);
  }
}
