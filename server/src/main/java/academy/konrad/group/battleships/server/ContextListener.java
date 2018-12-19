package academy.konrad.group.battleships.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

  private Thread listenerThread = null;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    listenerThread = new ListenerThread();
    listenerThread.start();
    System.out.println("Watek");
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    listenerThread.interrupt();
  }
}