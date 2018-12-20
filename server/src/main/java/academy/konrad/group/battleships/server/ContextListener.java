package academy.konrad.group.battleships.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

  private Thread listenerThread = null;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    listenerThread = new ListenerThread();
    listenerThread.start();
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    listenerThread.interrupt();
  }
}