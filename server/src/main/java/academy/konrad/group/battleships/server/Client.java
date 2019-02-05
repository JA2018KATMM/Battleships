package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.util.concurrent.TransferQueue;

class Client {

    private final ClientMessageSender clientMessageSender;

    private Client(ClientMessageSender clientMessageSender) {
        this.clientMessageSender = clientMessageSender;
    }

    void sendMessage(Message message) {
        clientMessageSender.send(message);
    }

    static Client createClient(ClientConnection clientConnection, TransferQueue<Message> roomClientsMessagesQueue, ThreadsManager threadsManager) throws IOException {
        ClientMessageListener listener = ClientMessageListener.createMessageListener(clientConnection, roomClientsMessagesQueue);
        threadsManager.execute(listener);
        ClientMessageSender clientMessageSender = ClientMessageSender.createMessageSender(clientConnection);
        return new Client(clientMessageSender);
    }
}
