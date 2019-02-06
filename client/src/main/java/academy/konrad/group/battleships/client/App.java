package academy.konrad.group.battleships.client;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.FileWriter;

public class App {

    public static void main(String[] args) {
        Thread.currentThread().setName("ClientMainThread");
        loggerSetup();
        if (args.length > 0 && args[0].equals("-t")) {
            for (int i = 0; i < 10000; i++) {
                new ClientAdministrator().startClient();
            }
        } else
            new ClientAdministrator().startClient();
    }

    private static void loggerSetup() {
        Configurator.defaultConfig().writer(new FileWriter(System.getProperty("user.home") + "/logi/info_klient.txt"), Level.INFO)
                .addWriter(new FileWriter(System.getProperty("user.home") + "/logi/bledy_klient.txt"), Level.ERROR).activate();
    }
}