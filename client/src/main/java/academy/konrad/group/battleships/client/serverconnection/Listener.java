package academy.konrad.group.battleships.client.serverconnection;

import academy.konrad.group.battleships.client.communication.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TransferQueue;

class Listener implements Runnable {

    private final BufferedReader serverSocketReader;
    private final TransferQueue<Message> messagesFromServer;

    private Listener(BufferedReader serverSocketReader, TransferQueue<Message> messagesFromServer){
        this.serverSocketReader = serverSocketReader;
        this.messagesFromServer = messagesFromServer;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                messagesFromServer.add(new Message(serverSocketReader.readLine()));
            } catch (IOException e) {
                System.err.println("Listener thread voicing concerns about bufferedReader.");
            }
        }
    }

    static Listener create(InputStream inputStream, TransferQueue<Message> messagesFromServer) {
        return new Listener(new BufferedReader(new InputStreamReader(inputStream)), messagesFromServer);
    }
}
