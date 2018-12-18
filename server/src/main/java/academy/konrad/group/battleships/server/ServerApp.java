package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Initiates Server App.
 */
public class ServerApp extends HttpServlet {

  /**
   * Initializes threads which clients use to communicate with server.
   */
  public void init() {
    Thread listenerThread = new ListenerThread();
    listenerThread.start();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    PrintWriter out = response.getWriter();
    out.print("Battleship says hello!");
  }
}