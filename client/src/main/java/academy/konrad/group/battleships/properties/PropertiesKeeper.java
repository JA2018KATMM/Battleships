package academy.konrad.group.battleships.properties;

import java.util.Properties;

class PropertiesKeeper {
  private Properties gameProperties;

  PropertiesKeeper(String defaultPropertiesPath) throws CannotLoadConfigurationFileException {
    gameProperties = DefaultPropertiesLoader.loadPropertiesFromFile(defaultPropertiesPath);
  }

  int getBoardSize() throws CannotReadPropertyException {
    return PropertyConverter.convertToInt(gameProperties.getProperty("boardSize"));
  }

  public void setNewBoardSize(int newBoardSize) {
    gameProperties.setProperty("boardSize", PropertyConverter.convertToString(newBoardSize));
  }
}
