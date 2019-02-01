package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.domain.Fleet;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.pmw.tinylog.Logger;


/**
* Zarzada flota i informuje kontrole UI o zmianach
 */
class MessageHandler {

  private final Controller controller;
  private final Fleet fleet;
  private final Sender sender = new Sender();

  MessageHandler(Controller controller, Fleet fleet) {
    this.controller = controller;
    this.fleet = fleet;
  }

  void manageTurn(String answer) {
    if (answer.equals("yes")) {
      doFirstTurn();
    } else {
      doSecondTurn();
    }
  }

  private void doSecondTurn() {
    String message = Connection.getMessage("secondTurn");
    Logger.info(message);
    this.controller.updateConsole(message + "\n");

  }

  private void doFirstTurn() {
    String message = Connection.getMessage("firstTurn");
    Logger.info(message);
    this.controller.updateConsole(message + "\n");
    Platform.runLater(() -> {
      this.controller.getPlayerBoard().setDisable(false);
    });
  }

  void doHit(String fieldHit) {
    String message = Connection.getMessage("enemyShipHit");
    Logger.info(message);

    Rectangle field = (Rectangle)this.controller.getPlayerBoard().getChildren().filtered(f -> f.getId().equals(fieldHit)).get(0);
    Platform.runLater(() -> {
      field.setFill(Color.YELLOW);
    });
    this.controller.updateConsole(message + "\n");
  }

   void showMessageOnTextArea(String text){
    Logger.info(text);
    this.controller.updateConsole(text + "\n");
  }

  void logStart(String message) {
    Logger.info(message + "\n "
        + "Initial ships location: "
        + fleet.getShips() + "\n");
  }

   void doMove(String fieldShot) {
    String message1 = Connection.getMessage("yourTurn");
    String message2 = Connection.getMessage("yourShipHit");
    String message3 = Connection.getMessage("fieldShoot") + fieldShot;
    boolean isShipShot = this.fleet.getShips().contains(Integer.parseInt(fieldShot));
    if (isShipShot) {
      shotShip(message3, message2, message1, fieldShot);
      checkIfGameFinished();
    } else {
      shotWater(message3, message1, fieldShot);
    }

  }

  private void shotWater(String message3, String message1, String fieldShot) {
    Logger.info(message3 + "\n" + message1);
    Rectangle field = (Rectangle) this.controller.getEnemyBoard().getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
    Platform.runLater(() -> {
      field.setFill(Color.RED);
      this.controller.getPlayerBoard().setDisable(false);
    });
    this.controller.updateConsole(message3 + "\n"
        + message1 + "\n");
  }

  private void checkIfGameFinished() {
    if (fleet.getShips().isEmpty()) {
      String message4 = Connection.getMessage("lastShip");
      Logger.info(message4);
      this.controller.updateConsole(message4 + "\n");
      sender.send("END");
    } else Platform.runLater(() -> this.controller.getPlayerBoard().setDisable(false));
  }

  private void shotShip(String message3, String message2, String message1, String fieldShot) {
    Logger.info(message3 + "\n" + message2 + "\n" + message1);
    Rectangle field = (Rectangle) controller.getEnemyBoard().getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
    Platform.runLater(() -> {
      field.setFill(Color.YELLOW);
    });
    this.controller.updateConsole(message3 + "\n"
        + message2 + "\n"
        + message1 + "\n");
    fleet.getShips().remove(Integer.parseInt(fieldShot));
    this.sender.send("HIT:" + fieldShot);
  }

}
