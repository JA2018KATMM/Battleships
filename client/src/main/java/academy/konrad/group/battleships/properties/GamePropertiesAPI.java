package academy.konrad.group.battleships.properties;

public class GamePropertiesAPI {
  private final String defaultPropertiesPath = "src/main/resources/default.properties";
  private PropertiesKeeper propertiesKeeper;

  public GamePropertiesAPI() throws CannotLoadConfigurationFileException {
    propertiesKeeper = new PropertiesKeeper(defaultPropertiesPath);
  }

  public int getBoardSize() throws CannotReadPropertyException {
    return propertiesKeeper.getBoardSize();
  }

  public void setNewBoardSize(int newBoardSize){
    propertiesKeeper.setNewBoardSize(newBoardSize);
  }

}
