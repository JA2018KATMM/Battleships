package academy.konrad.group.battleships.properties;

import org.pmw.tinylog.Logger;

class CannotLoadConfigurationFileException extends RuntimeException {

  {
    Logger.error(this.getClass().getName());
  }
}
