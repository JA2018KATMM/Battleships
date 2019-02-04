package academy.konrad.group.battleships.board;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Board extends TilePane {

  private EventHandler<? super MouseEvent> handler;

  Board(EventHandler<? super MouseEvent> handler) {
    this.handler = handler;
  }

  Board() {
  }

  EventHandler<? super MouseEvent> getHandler() {
    return handler;
  }

  void fillBoard(int fieldNumber) {
    for (int i = 0; i < fieldNumber; i++) {
      Rectangle rectangle = new Rectangle(30, 30);
      rectangle.setFill(Color.LIGHTGRAY);
      rectangle.setStroke(Color.BLACK);
      rectangle.setId(String.valueOf(i));
      if (handler != null) {
        rectangle.setOnMouseClicked(handler);
      }
      getChildren().addAll(rectangle);
    }
  }


  void setSize() {
    setPrefTileHeight(30);
    setPrefTileWidth(30);
    setMaxSize(300, 300);
  }
}