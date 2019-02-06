package academy.konrad.group.battleships.client.serverconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class Listener implements Runnable {

    private final Socket clientSocket;
    private final TransferQueue<Message> messagesFromServer = new LinkedTransferQueue<>();
    private final ConnectionHandler connectionHandler;

    private Listener(Socket clientSocket, ConnectionHandler connectionHandler){
        this.clientSocket = clientSocket;
        this.connectionHandler = connectionHandler;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                try (BufferedReader serverSocketReader = new BufferedReader(new InputStreamReader(connectionHandler.openInputStream()))) {
                    Message messageFromServer = new Message(serverSocketReader.readLine());
                    messagesFromServer.add(messageFromServer);
                }
            } catch (IOException e) {
                System.err.println("Listener thread voicing concerns about bufferedReader.");
            }
        }
    }


    static Listener create(Socket clientSocket, ConnectionHandler connectionHandler){
        return new Listener(clientSocket, connectionHandler);
    }
}
