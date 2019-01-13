package academy.konrad.group.battleships.properties;

import java.util.Locale;
import java.util.Properties;

class PropertiesKeeper {
  private Properties gameProperties;
  private static final String PATH_TO_PROPERTIES_FILE = "./client/src/main/resources/default.properties";

  PropertiesKeeper() throws CannotLoadConfigurationFileException {
    this.gameProperties = DefaultPropertiesLoader.loadPropertiesFromFile(PATH_TO_PROPERTIES_FILE);
  }

  PropertiesKeeper(String defaultPropertiesPath) {
    gameProperties = DefaultPropertiesLoader.loadPropertiesFromFile(defaultPropertiesPath);
  }

  Locale getDefaultLocale() {
    return new Locale(gameProperties.getProperty("locale"));
  }

  void setNewBoardSize(int newBoardSize) {
    gameProperties.setProperty("boardSize", PropertyConverter.convertToString(newBoardSize));
  }

  public String getValueByKey(String key) {
    return gameProperties.getProperty(key);
  }

  public int getIntValueByKey(String key) {
    return PropertyConverter.convertToInt(gameProperties.getProperty(key));
  }
}
