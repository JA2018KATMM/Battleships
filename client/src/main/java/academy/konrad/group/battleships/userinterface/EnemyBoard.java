package academy.konrad.group.battleships.userinterface;

import javafx.scene.layout.TilePane;

public class EnemyBoard extends TilePane {

  public EnemyBoard() {

    setPrefTileHeight(30);
    setPrefTileWidth(30);
    setMaxSize(300, 300);
  }

  void fillBoard(int fieldNumber) {
    for (int i = 0; i < fieldNumber; i++) {
      Field field = new Field();
      field.setId(String.valueOf(i));
      getChildren().addAll(field);
    }
  }
}
