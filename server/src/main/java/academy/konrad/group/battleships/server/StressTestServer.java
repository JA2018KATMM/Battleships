package academy.konrad.group.battleships.server;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.FileWriter;

public class StressTestServer {

    public static void main(String[] args) {

        ListenerThread listenerThread = new ListenerThread();

        loggerSetup();
        Logger.info("Aplikacje serwerowa została uruchomiona");
        listenerThread.setName("WĄTEK SERWERA");
        listenerThread.start();
    }

    private static void loggerSetup() {
        Configurator.defaultConfig().writer(new FileWriter(System.getProperty("user.home")
                + "/logi/info_serwer.txt"), Level.INFO)
                .addWriter(new FileWriter(System.getProperty("user.home")
                        + "/logi/bledy_serwer.txt"), Level.ERROR).activate();
    }
}