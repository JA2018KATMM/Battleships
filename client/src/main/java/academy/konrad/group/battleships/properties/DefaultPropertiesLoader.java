package academy.konrad.group.battleships.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

class DefaultPropertiesLoader {
  private DefaultPropertiesLoader() {
  }

  static Properties loadPropertiesFromFile(String defaultPropertiesPath) {
    Properties properties = new Properties();
    try (FileInputStream fileInputStream = new FileInputStream(defaultPropertiesPath)){
      properties.load(fileInputStream);
      return properties;
    }catch (FileNotFoundException e){
      throw new CannotLoadConfigurationFileException();
    } catch (IOException e) {
      e.printStackTrace();
      throw new CannotLoadConfigurationFileException();
    }

  }
}
