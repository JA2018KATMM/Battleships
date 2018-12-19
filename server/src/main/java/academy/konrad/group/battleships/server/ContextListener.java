package academy.konrad.group.battleships.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    Thread listenerThread = new SocketListenerThread();
    listenerThread.start();
    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
  }
}