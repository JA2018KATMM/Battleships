package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.util.concurrent.*;

class ServerAdministrator {
    private final TransferQueue<Client> clientsWaitingRoom = new LinkedTransferQueue<>();
    private final Rooms rooms = new Rooms();

    void runServer() {
        Listener listener = null;
        try {
            listener = Listener.createListener(clientsWaitingRoom);
        } catch (IOException e) {
            System.err.println("Server Socket threw exception during initialization");
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(listener);

        while(!Thread.currentThread().isInterrupted()){
            try {
                Client client = clientsWaitingRoom.take();
                rooms.assignRoom(client);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
