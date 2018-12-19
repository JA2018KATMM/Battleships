package academy.konrad.group.battleships.properties;

public class GamePropertiesAPI {
  private PropertiesKeeper propertiesKeeper;

  public GamePropertiesAPI(String propertiesPath) {
    propertiesKeeper = new PropertiesKeeper(propertiesPath);
  }

  public GamePropertiesAPI() {
    this.propertiesKeeper = new PropertiesKeeper();
  }

  public int getIntValueByKey(String key) {
    return propertiesKeeper.getIntValueByKey(key);
  }

  public String getValueByKey(String key) {
    return propertiesKeeper.getValueByKey(key);
  }

  public void setNewBoardSize(int newBoardSize){
    propertiesKeeper.setNewBoardSize(newBoardSize);
  }

}
