package academy.konrad.group.battleships.client.serverconnection;

import academy.konrad.group.battleships.client.communication.Message;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TransferQueue;

public class ServerConnectionAPI {

    private final MessageSender messageSender;

    private ServerConnectionAPI(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    public void sendMessageToServer(Message message){
        messageSender.sendToServer(message);
    }


    public static ServerConnectionAPI create(String hostName, int portNumber, TransferQueue<Message> messagesFromServer) throws IOException {
        ConnectionHandler connectionHandler = ConnectionHandler.create(hostName, portNumber);
        Listener listener = Listener.create(connectionHandler.openInputStream(), messagesFromServer);
        Executors.newSingleThreadExecutor().execute(listener);
        MessageSender messageSender = MessageSender.create(connectionHandler.openOutputStream());
        return new ServerConnectionAPI(messageSender);
    }
}
