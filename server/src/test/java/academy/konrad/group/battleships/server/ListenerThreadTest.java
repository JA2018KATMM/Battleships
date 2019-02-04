package academy.konrad.group.battleships.server;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.FileWriter;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class ListenerThreadTest {
    @Test
    public void efficiencyTest() throws InterruptedException {
        loggerSetup();
        ListenerThread listenerThread = new ListenerThread("WątekNasłuchującegoSerwera");
        listenerThread.start();
        for(int i = 0; i < 10000; i++){
            Socket socket = new Socket();
            try {
                InetSocketAddress socketAddress =
                        new InetSocketAddress("localhost", 8081);
                socket.connect(socketAddress, 1000);
            } catch (IOException exception) {
                Logger.error(exception.getMessage());
            }
        }
        listenerThread.sleep(5000);
    }

    private void loggerSetup() {
        Configurator.defaultConfig().writer(new FileWriter(System.getProperty("user.home")
                + "/logi/info_serwer.txt"), Level.INFO)
                .addWriter(new FileWriter(System.getProperty("user.home")
                        + "/logi/bledy_serwer.txt"), Level.ERROR).activate();
    }

}