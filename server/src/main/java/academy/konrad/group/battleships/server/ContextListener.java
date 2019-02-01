package academy.konrad.group.battleships.server;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.FileWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

  private Thread listenerThread = null;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    loggerSetup();
    Logger.info("Aplikacje serwerowa została uruchomiona");
    listenerThread = new ListenerThread();
    listenerThread.start();
  }

  private void loggerSetup() {
    Configurator.defaultConfig().writer(new FileWriter(System.getProperty("user.home")
        + "/logi/info_serwer.txt"), Level.INFO)
            .addWriter(new FileWriter(System.getProperty("user.home")
                + "/logi/bledy_serwer.txt"), Level.ERROR).activate();
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    listenerThread.interrupt();
  }
}