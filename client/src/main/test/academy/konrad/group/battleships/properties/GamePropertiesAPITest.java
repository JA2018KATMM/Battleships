package academy.konrad.group.battleships.properties;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GamePropertiesAPITest {

  @Test
  public void shouldReturnDefaultBoardSize() throws Exception {
      //given
      String whyItFailed = "default board size was not loaded properly.";
      GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI();
      //when
      int expectedBoardSize = 10;
      int actualBoardSize = gamePropertiesAPI.getBoardSize();
      //then
      assertEquals(actualBoardSize, expectedBoardSize, whyItFailed);
  }

  @Test
  public void shouldChangeBoardSizeToDifferentValue() throws Exception {
    //given
    String whyItFailed = "default board size was not loaded properly.";
    GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI();
    gamePropertiesAPI.setNewBoardSize(20);
    //when
    int expectedBoardSize = 20;
    int actualBoardSize = gamePropertiesAPI.getBoardSize();
    //then
    assertEquals(actualBoardSize, expectedBoardSize, whyItFailed);
  }
}