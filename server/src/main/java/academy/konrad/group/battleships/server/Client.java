package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.util.concurrent.TransferQueue;

class Client {

    private final ClientMessageListener messageListener;
    private final ClientMessageSender clientMessageSender;
    private final ClientConnection clientConnection;

    private Client(ClientMessageListener messageListener, ClientMessageSender clientMessageSender, ClientConnection clientConnection) {
        this.messageListener = messageListener;
        this.clientMessageSender = clientMessageSender;
        this.clientConnection = clientConnection;
    }

    void sendMessage(Message message) {
        clientMessageSender.send(message);
    }

    boolean isDisconnected() {
        return !clientConnection.isConnected();
    }

    void closeConnection(){
        clientMessageSender.close();
        try {
            clientConnection.close();
        } catch (IOException e) {
            System.err.println("Could not close client connection!");
        }
    }


    static Client createClient(ClientConnection clientConnection, TransferQueue<Message> roomClientsMessagesQueue, ThreadsManager threadsManager) throws IOException {
        ClientMessageListener messageListener = ClientMessageListener.createMessageListener(clientConnection, roomClientsMessagesQueue);
        threadsManager.execute(messageListener);
        ClientMessageSender clientMessageSender = ClientMessageSender.createMessageSender(clientConnection);
        return new Client(messageListener, clientMessageSender, clientConnection);
    }
}
