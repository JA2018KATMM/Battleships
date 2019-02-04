package academy.konrad.group.battleships;

import academy.konrad.group.battleships.efficiencytest.MainWithoutGUI;
import academy.konrad.group.battleships.userinterface.MainWithGUI;

public class App {

    public static void main(String[] args) {
        if (args[0].equals("-ng") || args[0].equals("--nogui"))
            MainWithoutGUI.main();
        else {
            MainWithGUI.main(args);
        }
    }
}