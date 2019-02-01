package academy.konrad.group.battleships.game_elements;

import javafx.scene.layout.TilePane;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BoardFactoryTest {

  @Test
  public void enemyBoardHasNoHandler(){
    int numberOfFields = 10;

    Board enemyType = (Board) BoardFactory.getEnemyBoard(numberOfFields);

    assertNull(enemyType.getHandler());

  }

}