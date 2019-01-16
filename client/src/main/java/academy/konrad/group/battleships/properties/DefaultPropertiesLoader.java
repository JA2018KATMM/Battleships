package academy.konrad.group.battleships.properties;

import org.pmw.tinylog.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class DefaultPropertiesLoader {
  private DefaultPropertiesLoader() {
  }

  /**
   * poprawiona metoda wczytująca properties file
   *
   */
  static Properties loadPropertiesFromFile() {
    Properties properties = new Properties();
    try (InputStream inputStream = DefaultPropertiesLoader.class.getClassLoader().getResourceAsStream("default.properties")) {
      properties.load(inputStream);
      return properties;
    } catch (FileNotFoundException e) {
      throw new CannotLoadConfigurationFileException();
    } catch (IOException exception) {
      Logger.error(exception.getMessage());
      throw new CannotLoadConfigurationFileException();
    }

  }


  /**
   * stara metoda wczytująca properties file pozostawiona do czasu refaktoryzacji testów
   * ponieważ 33% testów od niej zależy
   *
   */
  static Properties loadPropertiesFromFile(String defaultPropertiesPath) {
    Properties properties = new Properties();
    try (FileInputStream inputStream = new FileInputStream(defaultPropertiesPath)) {
      properties.load(inputStream);
      return properties;
    } catch (FileNotFoundException e) {
      throw new CannotLoadConfigurationFileException();
    } catch (IOException exception) {
      Logger.error(exception.getMessage());
      throw new CannotLoadConfigurationFileException();
    }

  }
}
