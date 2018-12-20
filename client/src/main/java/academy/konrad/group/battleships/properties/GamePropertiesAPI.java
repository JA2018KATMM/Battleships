package academy.konrad.group.battleships.properties;

import java.util.Locale;
import java.util.ResourceBundle;

public class GamePropertiesAPI {
  private PropertiesKeeper propertiesKeeper;
  private ResourceBundle resourceBundle;

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

  public void setDefaultLanguage() {
    resourceBundle = ResourceBundle.getBundle("Language", propertiesKeeper.getDefaultLocale());
  }

  public void changeLanguage(String locale) {
    resourceBundle = ResourceBundle.getBundle("Language", new Locale(locale));
  }

  public ResourceBundle getCurrentBundle() {
    return this.resourceBundle;
  }

  public void setNewBoardSize(int newBoardSize) {
    propertiesKeeper.setNewBoardSize(newBoardSize);
  }
}

