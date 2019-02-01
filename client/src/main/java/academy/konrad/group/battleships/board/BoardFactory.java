package academy.konrad.group.battleships.board;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.TilePane;

/**
 * Tworzy wypełnioną planszę, jeśli jest to plansza przeciwnika nie reaguje ona na klikanie,
 * klikanie po planszy graczy ma określone zachowanie (EvenHandler).
 */
public class BoardFactory {

  public static TilePane getPlayerBoard(EventHandler<Event> handler, int numberOfFields) {
    return setUpBoard(new Board(handler), numberOfFields);
  }

  public static TilePane getEnemyBoard(int numberOfFields) {
    return setUpBoard(new Board(), numberOfFields);
  }

  private static Board setUpBoard(Board board, int numberOfFields) {
    board.setSize();
    board.fillBoard(numberOfFields);
    board.setDisable(true);
    return board;
  }

}
