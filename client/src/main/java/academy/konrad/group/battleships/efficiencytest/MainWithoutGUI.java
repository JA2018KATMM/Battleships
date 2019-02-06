package academy.konrad.group.battleships.efficiencytest;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.FileWriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainWithoutGUI {

    public static void main(String args) {
        loggerSetup();
        ExecutorService executors = Executors.newCachedThreadPool();
        Integer numberOfThreads = Integer.valueOf(args);
        for (int i = 1; i < numberOfThreads; i++) {
            executors.execute(new Client(i));
        }
        System.out.println("Zakończono tworzenie klientów");
        Logger.info("Zakończono tworzenie klientów");
    }

    private static void loggerSetup() {
        Configurator.defaultConfig().writer(new org.pmw.tinylog.writers.FileWriter(System.getProperty("user.home")
                + "/logi/info_client.txt", true, true), Level.INFO)
                .addWriter(new FileWriter(System.getProperty("user.home")
                        + "/logi/bledy_klient.txt"), Level.ERROR).activate();
    }
}