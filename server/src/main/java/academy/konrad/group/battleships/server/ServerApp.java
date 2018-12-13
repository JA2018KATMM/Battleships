package academy.konrad.group.battleships.server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerApp extends HttpServlet {

    public void init() {
      Thread listenerThread = new ListenerThread();
      listenerThread.start();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.print("Battleship says hello!");
    }
}