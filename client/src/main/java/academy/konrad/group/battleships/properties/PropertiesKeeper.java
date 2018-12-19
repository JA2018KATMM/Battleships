package academy.konrad.group.battleships.properties;

import java.util.Locale;
import java.util.Properties;

class PropertiesKeeper {
  private Properties gameProperties;

  PropertiesKeeper(String defaultPropertiesPath) throws CannotLoadConfigurationFileException {
    gameProperties = DefaultPropertiesLoader.loadPropertiesFromFile(defaultPropertiesPath);
  }

  Locale getDefaultLocale() {
    return new Locale(gameProperties.getProperty("locale"));
  }

  int getBoardSize() throws CannotReadPropertyException {
    return PropertyConverter.convertToInt(gameProperties.getProperty("boardSize"));
  }

  void setNewBoardSize(int newBoardSize) {
    gameProperties.setProperty("boardSize", PropertyConverter.convertToString(newBoardSize));
  }
}
