package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ListenerThread extends Thread {

    private static final int PORT_NUMBER = 8081;
    private static final LoggedClientsSet LOGGED_CLIENTS_SET = new LoggedClientsSet();

    public void run() {
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
                Socket firstClientSocket = serverSocket.accept();
                Socket secondClientSocket = serverSocket.accept();
                if(firstClientSocket != null && secondClientSocket !=null) {
                    System.out.println("Jest klient");
                    ClientsPair clientsPair = new ClientsPair(firstClientSocket, secondClientSocket);
                    //LOGGED_CLIENTS_SET.addClient(loggedClient);

                    Thread thread = new Thread(clientsPair);
                    thread.start();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                shouldContinue = false;
            }
        }
    }
}
