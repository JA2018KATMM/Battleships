package academy.konrad.group.battleships.userinterface;

import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class Updater {

    private final FieldNumber fieldNumber;
    private final TilePane boardToUpdate;

    public Updater(FieldNumber fieldNumber, TilePane boardToUpdate) {
        this.fieldNumber = fieldNumber;
        this.boardToUpdate = boardToUpdate;
    }

    public Runnable getRunnable() {

        Runnable updater = new Runnable() {

            @Override
            public void run() {
                for (Node elem : boardToUpdate.getChildren()) {
                    if (elem.getId().equals(fieldNumber.getFieldId())) {
                        Field field = (Field) elem;
                        field.setFill(Color.RED);
                        return;
                    }
                }
            }
        };
        return updater;
    }

}
