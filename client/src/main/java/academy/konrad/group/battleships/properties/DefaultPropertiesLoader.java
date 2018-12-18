package academy.konrad.group.battleships.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class DefaultPropertiesLoader{
  private DefaultPropertiesLoader() {
  }

  static Properties loadPropertiesFromFile(String defaultPropertiesPath) throws CannotLoadConfigurationFileException {
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(defaultPropertiesPath));
    } catch (IOException e) {
      throw new CannotLoadConfigurationFileException();
    }
    return properties;
  }
}
