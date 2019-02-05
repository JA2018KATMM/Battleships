package academy.konrad.group.battleships.efficiencytest;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.FileWriter;

public class MainWithoutGUI {

    public static void main(String args) {
        loggerSetup();
        Client client = new Client(args);
        client.play();
    }

    private static void loggerSetup() {
        Configurator.defaultConfig().writer(new org.pmw.tinylog.writers.FileWriter(System.getProperty("user.home")
                + "/logi/info_client.txt", true, true), Level.INFO)
                .addWriter(new FileWriter(System.getProperty("user.home")
                        + "/logi/bledy_klient.txt"), Level.ERROR).activate();
    }
}