package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.util.concurrent.*;

class ServerAdministrator {
    private final TransferQueue<ClientConnection> clientConnectionsWaitingRoom = new LinkedTransferQueue<>();
    private final RoomAssigner roomAssigner = RoomAssigner.createAssigner();

    void runServer() {
        Listener listener = null;
        try {
            listener = Listener.createListener(clientConnectionsWaitingRoom);
        } catch (IOException e) {
            System.err.println("Server Socket threw exception during initialization");
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(listener);

        while(!Thread.currentThread().isInterrupted()){
            try {
                ClientConnection clientConnection = clientConnectionsWaitingRoom.take();
                roomAssigner.assignRoom(clientConnection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
