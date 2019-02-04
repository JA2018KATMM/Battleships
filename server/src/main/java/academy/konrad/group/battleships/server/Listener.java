package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TransferQueue;

class Listener implements Runnable {
    private final TransferQueue<Client> clientsWaitingRoom;
    private final ServerSocket serverSocket;


    private Listener(TransferQueue<Client> clientsWaitingRoom, ServerSocket serverSocket) {
        this.clientsWaitingRoom = clientsWaitingRoom;
        this.serverSocket = serverSocket;
    }

    static Listener createListener(TransferQueue<Client> clientsWaitingRoom) throws IOException {
        return new Listener(clientsWaitingRoom, new ServerSocket());
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);
                clientsWaitingRoom.add(client);
            } catch (IOException e) {
                System.err.println("Client server threw exception during acceptance of client connection.");
            }
        }
    }
}
