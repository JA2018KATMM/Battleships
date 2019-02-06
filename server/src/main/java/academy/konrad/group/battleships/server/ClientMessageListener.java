package academy.konrad.group.battleships.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TransferQueue;

class ClientMessageListener implements Runnable {
    private final BufferedReader bufferedReader;
    private final TransferQueue<Message> roomClientsMessagesQueue;

    private ClientMessageListener(InputStream inputStream, TransferQueue<Message> roomClientsMessagesQueue) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.roomClientsMessagesQueue = roomClientsMessagesQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                roomClientsMessagesQueue.add(new Message(line));
            }
        } catch (IOException e) {
            System.err.println("ClientMessageListener's bufferedReader cannot read.");
        } finally {
            System.err.println("Client left the room");
            Thread.currentThread().interrupt();
            try {
                bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    static ClientMessageListener createMessageListener(ClientConnection clientConnection, TransferQueue<Message> roomClientsMessagesQueue) throws IOException {
        return new ClientMessageListener(clientConnection.openInputStream(), roomClientsMessagesQueue);
    }
}
