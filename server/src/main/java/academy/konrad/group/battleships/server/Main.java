package academy.konrad.group.battleships.server;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.FileWriter;


public class Main {
    public static void main(String[] args) {
        Thread.currentThread().setName("MainThread");
        loggerSetup();
        new ServerAdministrator().runServer();
    }

    private static void loggerSetup() {
        Configurator.defaultConfig().writer(new FileWriter(System.getProperty("user.home") +"/logi/info_serwer.txt"), Level.INFO)
                .addWriter(new FileWriter(System.getProperty("user.home") +"/logi/bledy_serwer.txt"), Level.ERROR).activate();
    }

}
