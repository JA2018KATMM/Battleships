package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TransferQueue;

class Listener implements Runnable {
    private final TransferQueue<ClientConnection> clientsWaitingRoom;
    private final ServerSocket serverSocket;


    private Listener(TransferQueue<ClientConnection> clientsWaitingRoom, ServerSocket serverSocket) {
        this.clientsWaitingRoom = clientsWaitingRoom;
        this.serverSocket = serverSocket;
    }

    static Listener createListener(TransferQueue<ClientConnection> clientsWaitingRoom) throws IOException {
        return new Listener(clientsWaitingRoom, new ServerSocket(8081));
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                ClientConnection clientConnection = new ClientConnection(socket);
                clientsWaitingRoom.add(clientConnection);
            } catch (IOException e) {
                System.err.println("ClientConnection server threw exception during acceptance of client connection.");
            }
        }
    }
}
