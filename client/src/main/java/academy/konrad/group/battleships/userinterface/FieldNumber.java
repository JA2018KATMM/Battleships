package academy.konrad.group.battleships.userinterface;

import java.io.Serializable;

public class FieldNumber implements Serializable {

  private static final long serialVersionUID = 1L;
  private String fieldId;

  FieldNumber(String fieldId) {
    this.fieldId = fieldId;
  }

  String getFieldId() {
    return fieldId;
  }

  @Override
  public String toString() {
    return "FieldNumber{"
        + "fieldId='" + fieldId + '\''
        + '}';
  }
}
