package academy.konrad.group.battleships.client;

import academy.konrad.group.battleships.client.communication.InputListener;
import academy.konrad.group.battleships.client.communication.Message;
import academy.konrad.group.battleships.client.communication.UsersInputReader;
import academy.konrad.group.battleships.client.serverconnection.ServerConnectionAPI;
import academy.konrad.group.battleships.client.view.ChatView;
import academy.konrad.group.battleships.client.view.OutputProvider;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class App {

    public static void main(String[] args) {
        Thread.currentThread().setName("ClientMainThread");
        ChatView view = ChatView.newConsoleView();
        UsersInputReader inputReader = UsersInputReader.newConsoleReader();
        final TransferQueue<Message> messagesFromServer = new LinkedTransferQueue<>();
        try {
            ServerConnectionAPI serverConnectionAPI = ServerConnectionAPI.create("localhost", 8081, messagesFromServer);
            Executors.newSingleThreadExecutor().execute(new InputListener(inputReader, serverConnectionAPI));
            Executors.newSingleThreadExecutor().execute(new OutputProvider(messagesFromServer, view));
        } catch (IOException e) {
            System.err.println("Socket connection was disrupted!");
        }
    }
}