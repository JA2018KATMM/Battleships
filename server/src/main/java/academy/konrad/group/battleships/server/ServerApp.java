package academy.konrad.group.battleships.server;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * Initiates Server App.
 */
public class ServerApp extends HttpServlet {


  @Override
  /**
   * Initializes threads which clients use to communicate with server.
   */
  public void init() {
    Thread listenerThread = new ListenerThread();
    listenerThread.start();
  }


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    request.getRequestDispatcher("index.jsp").forward(request, response);
  }
}