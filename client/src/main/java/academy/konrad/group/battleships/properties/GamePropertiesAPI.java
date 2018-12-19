package academy.konrad.group.battleships.properties;

import java.util.Locale;
import java.util.ResourceBundle;

public class GamePropertiesAPI {
  private PropertiesKeeper propertiesKeeper;
  private ResourceBundle resourceBundle;

  public GamePropertiesAPI(String propertiesPath) throws CannotLoadConfigurationFileException {
    propertiesKeeper = new PropertiesKeeper(propertiesPath);
  }

  public void setDefaultLanguage() {
    resourceBundle = ResourceBundle.getBundle("Language", propertiesKeeper.getDefaultLocale());
  }

  public void changeLanguage(String locale) {
    resourceBundle = ResourceBundle.getBundle("Language", new Locale(locale));
  }

  public ResourceBundle getCurrentBundle(){
    return this.resourceBundle;
  }

  public int getBoardSize() throws CannotReadPropertyException {
    return propertiesKeeper.getBoardSize();
  }

  public void setNewBoardSize(int newBoardSize){
    propertiesKeeper.setNewBoardSize(newBoardSize);
  }
}

