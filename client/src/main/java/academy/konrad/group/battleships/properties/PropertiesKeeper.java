package academy.konrad.group.battleships.properties;

import java.util.Properties;

class PropertiesKeeper {
  private Properties gameProperties;
  private static final String PATH_TO_PROPERTIES_FILE = "src/main/resources/default.properties";

  PropertiesKeeper() throws CannotLoadConfigurationFileException {
    this.gameProperties = DefaultPropertiesLoader.loadPropertiesFromFile(PATH_TO_PROPERTIES_FILE);
  }

  PropertiesKeeper(String defaultPropertiesPath) {
    gameProperties = DefaultPropertiesLoader.loadPropertiesFromFile(defaultPropertiesPath);
  }

  int getBoardSize(){
    return PropertyConverter.convertToInt(gameProperties.getProperty("boardSize"));
  }


  public void setNewBoardSize(int newBoardSize) {
    gameProperties.setProperty("boardSize", PropertyConverter.convertToString(newBoardSize));
  }

  public String getValueByKey(String key) {
    return gameProperties.getProperty(key);
  }
}
