package academy.konrad.group.battleships.client.communication;

import academy.konrad.group.battleships.client.serverconnection.ServerConnectionAPI;

public class InputListener implements Runnable{
    private final UsersInputReader usersInputReader;
    private final ServerConnectionAPI serverConnectionAPI;

    public InputListener(UsersInputReader usersInputReader, ServerConnectionAPI serverConnectionAPI){
        this.usersInputReader = usersInputReader;
        this.serverConnectionAPI = serverConnectionAPI;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            serverConnectionAPI.sendMessageToServer(new Message(usersInputReader.readLine()));
        }
    }
}
