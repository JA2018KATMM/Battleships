package academy.konrad.group.battleships.properties;

import org.pmw.tinylog.Logger;

class CannotReadPropertyException extends RuntimeException {

  {
    Logger.error(this.getClass().getName());
  }
}
