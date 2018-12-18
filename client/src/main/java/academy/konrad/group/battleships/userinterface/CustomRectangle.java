package academy.konrad.group.battleships.userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomRectangle extends Rectangle {

  public CustomRectangle() {
    super(30, 30);
    setFill(Color.LIGHTGRAY);
    setStroke(Color.BLACK);
  }
}
