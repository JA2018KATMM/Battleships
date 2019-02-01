package academy.konrad.group.battleships.properties;

class PropertyConverter {

  private PropertyConverter() {

  }

  static int convertToInt(String property) throws CannotReadPropertyException {
    try {
      return Integer.parseInt(property);
    } catch (NumberFormatException exception) {
      throw new CannotReadPropertyException();
    }
  }

  static String convertToString(int property) {
    return String.valueOf(property);
  }
}
