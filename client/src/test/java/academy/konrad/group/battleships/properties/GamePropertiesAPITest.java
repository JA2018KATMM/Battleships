package academy.konrad.group.battleships.properties;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GamePropertiesAPITest {

  @Test
  public void shouldReturnDefaultBoardSize() throws Exception {
      //given
      String whyItFailed = "default board size was not loaded properly.";
    String propertiesPath = "src/main/resources/default.properties";
    GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI(propertiesPath);
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
    String propertiesPath = "src/main/resources/default.properties";
    GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI(propertiesPath);
    gamePropertiesAPI.setNewBoardSize(20);
    //when
    int expectedBoardSize = 20;
    int actualBoardSize = gamePropertiesAPI.getBoardSize();
    //then
    assertEquals(actualBoardSize, expectedBoardSize, whyItFailed);
  }

  @Test(expectedExceptions = CannotLoadConfigurationFileException.class)
  public void shouldThrowExceptionWhenFilePathIsBroken() throws Exception {
    //given
    String whyItFailed = "Wrong class path doesn't produce expected exception";
    String wrongPropertiesPath = "src/main/resources/wrong.properties";
    GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI(wrongPropertiesPath);
    //when
    int expectedBoardSize = 10;
    int actualBoardSize = gamePropertiesAPI.getBoardSize();
    //then
    assertEquals(actualBoardSize, expectedBoardSize, whyItFailed);
  }

  @Test(expectedExceptions = CannotReadPropertyException.class)
  public void shouldThrowExceptionWhenPropertyNotNumber() throws Exception {
    //given
    String whyItFailed = "Somehow a word was attempted to convert to int and no exception was thrown";
    String testPropertiesPath = "src/main/test/resources/test.properties";
    GamePropertiesAPI gamePropertiesAPI = new GamePropertiesAPI(testPropertiesPath);
    //when
    int expectedBoardSize = 10;
    int actualBoardSize = gamePropertiesAPI.getBoardSize();
    //then
    assertEquals(actualBoardSize, expectedBoardSize, whyItFailed);
  }

}