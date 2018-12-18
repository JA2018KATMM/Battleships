package academy.konrad.group.battleships.userinterface;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

class Board extends TilePane {

  private EventHandler<? super MouseEvent> handler;

  Board(EventHandler<? super MouseEvent> handler) {
    this.handler = handler;
    setPrefTileHeight(30);
    setPrefTileWidth(30);
    setMaxSize(300, 300);
  }

  void fillBoard(int fieldNumber) {
    for (int i = 0; i < fieldNumber; i++) {
      Field field = new Field();
      field.setId(String.valueOf(i));
      field.setOnMouseClicked(handler);
      getChildren().addAll(field);
    }
  }
}