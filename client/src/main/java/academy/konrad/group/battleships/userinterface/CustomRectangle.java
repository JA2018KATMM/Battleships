package academy.konrad.group.battleships.userinterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomRectangle extends Rectangle {

  private int id;

  public CustomRectangle(int id) {
    super(30, 30);
    this.id = id;
    setFill(Color.LIGHTGRAY);
    setStroke(Color.BLACK);
  }

}
