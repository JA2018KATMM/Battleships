package academy.konrad.group.battleships.server;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class RoomAssigner {

    private Room currentRoom;
    private static final int ROOM_CAPACITY = 2;
    private final ThreadsManager threadsManager;

    private RoomAssigner(ExecutorService executorService) {
        this.threadsManager = new ThreadsManager(executorService);
    }

    void assignRoom(ClientConnection clientConnection) {
        if (currentRoom == null || currentRoom.isFull()) {
            currentRoom = createRoom();
        }
        try {
            //design flaw. Client should be connected first before being assigned to the room.
            currentRoom.connect(clientConnection);
        } catch (IOException e) {
            System.err.println("Cannot connect client to server.");
        }
    }

    private Room createRoom() {
        Room newRoom = Room.createRoom(ROOM_CAPACITY);
        threadsManager.execute(newRoom);
        return newRoom;
    }

    static RoomAssigner createAssigner() {
        ExecutorService executorService = Executors.newCachedThreadPool(new ServerThreadsFactory("Room"));
        return new RoomAssigner(executorService);
    }
}
