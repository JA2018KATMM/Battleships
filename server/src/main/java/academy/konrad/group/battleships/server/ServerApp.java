package academy.konrad.group.battleships.server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp extends HttpServlet {

  private static final int PORT_NUMBER = 8082;
  private static final LoggedClientsSet LOGGED_CLIENTS_SET = new LoggedClientsSet();

  public void init() {
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(PORT_NUMBER);
    } catch (IOException exception) {
      System.out.println("Exception caught when trying to listen on port "
          + PORT_NUMBER + " or listening for a connection");
      exception.printStackTrace();
    }

    boolean shouldContinue = true;

    while (shouldContinue) {
      try {
        Socket clientSocket = serverSocket.accept();
        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

        LoggedClient loggedClient = new LoggedClient(clientSocket, dis, dos, LOGGED_CLIENTS_SET);
        loggedClient.inform("Witaj w grze statki!");
        LOGGED_CLIENTS_SET.addClient(loggedClient);

        Thread thread = new Thread(loggedClient);
        thread.start();
      } catch (IOException exception) {
        exception.printStackTrace();
        shouldContinue = false;
      }
    }
  }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.print("Battleship says hello!");
    }
}