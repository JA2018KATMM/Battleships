package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.domain.Fleet;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.pmw.tinylog.Logger;


/**
* Zarzada flota i informuje kontrole UI o zmianach
 */
public class MessageHandler {

  private final Controller controller;
  private final Fleet fleet;
  private final Sender sender = new Sender();

  public MessageHandler(Controller controller, Fleet fleet) {
    this.controller = controller;
    this.fleet = fleet;
  }

  void doFirst(String answer) {
    if (answer.equals("yes")) {
      String message = Connection.getMessage("firstTurn");
      Logger.info(message);
      Platform.runLater(() -> {
        this.controller.getConsole().appendText(message + "\n");
        this.controller.getPlayerBoard().setDisable(false);
      });
    } else {
      String message = Connection.getMessage("secondTurn");
      Logger.info(message);
      Platform.runLater(() -> this.controller.getConsole().appendText(message + "\n"));
    }
  }

   void doHit(String fieldHit) {
    String message = Connection.getMessage("enemyShipHit");
    Logger.info(message);

    Rectangle field = (Rectangle)this.controller.getPlayerBoard().getChildren().filtered(f -> f.getId().equals(fieldHit)).get(0);
    Platform.runLater(() -> {
      field.setFill(Color.YELLOW);
      this.controller.getConsole().appendText(message + "\n");
    });
  }

   void showMessageOnTextArea(String text){
    Logger.info(text);
    Platform.runLater(() -> this.controller.getConsole().appendText(text + "\n"));
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
    if (this.fleet.getShips().contains(Integer.parseInt(fieldShot))) {
      Logger.info(message3 + "\n" + message2 + "\n" + message1);
      Rectangle field = (Rectangle) controller.getEnemyBoard().getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
      Platform.runLater(() -> {
        field.setFill(Color.YELLOW);
        controller.getConsole().appendText(message3 + "\n"
            + message2 + "\n"
            + message1 + "\n");
      });
      fleet.getShips().remove(Integer.parseInt(fieldShot));
      this.sender.send("HIT:" + fieldShot);
      if (fleet.getShips().isEmpty()) {
        String message4 = Connection.getMessage("lastShip");
        Logger.info(message4);
        Platform.runLater(() -> controller.getConsole().appendText(message4 + "\n"));
        sender.send("END");
      } else Platform.runLater(() -> this.controller.getPlayerBoard().setDisable(false));
    } else {
      Logger.info(message3 + "\n" + message1);
      Rectangle field = (Rectangle) this.controller.getEnemyBoard().getChildren().filtered(f -> f.getId().equals(fieldShot)).get(0);
      Platform.runLater(() -> {
        field.setFill(Color.RED);
        this.controller.getConsole().appendText(message3 + "\n"
            + message1 + "\n");
        this.controller.getPlayerBoard().setDisable(false);
      });
    }

  }

}
