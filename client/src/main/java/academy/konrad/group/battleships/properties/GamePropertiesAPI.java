package academy.konrad.group.battleships.properties;

public class GamePropertiesAPI {
  private PropertiesKeeper propertiesKeeper;

  public GamePropertiesAPI(String propertiesPath) throws CannotLoadConfigurationFileException {
    propertiesKeeper = new PropertiesKeeper(propertiesPath);
  }

  public int getBoardSize() throws CannotReadPropertyException {
    return propertiesKeeper.getBoardSize();
  }

  public void setNewBoardSize(int newBoardSize){
    propertiesKeeper.setNewBoardSize(newBoardSize);
  }

}
