package academy.konrad.group.battleships.userinterface;

import java.io.Serializable;

public class FieldNumber implements Serializable {

  private String fieldId;
  private static final long serialVersionUID = 1L;
  public FieldNumber(String fieldId) {
    this.fieldId = fieldId;
  }

  public String getFieldId() {
    return fieldId;
  }

  public void setFieldId(String fieldId) {
    this.fieldId = fieldId;
  }

  @Override
  public String toString() {
    return "FieldNumber{" +
        "fieldId='" + fieldId + '\'' +
        '}';
  }
}
