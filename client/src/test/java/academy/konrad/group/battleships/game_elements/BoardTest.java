package academy.konrad.group.battleships.game_elements;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BoardTest {

  @Test
  public void checkIfNumberOfFieldsIsCorrect(){
    int numberOfFields = 10;
    Board board = new Board();

    board.fillBoard(numberOfFields);

    assertEquals(board.getChildren().size(), numberOfFields);
  }

}