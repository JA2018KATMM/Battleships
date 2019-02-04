package academy.konrad.group.battleships.server;


import java.util.LinkedList;
import java.util.List;

class Rooms {

    private final List<Room> roomList = new LinkedList<>();
    private Room currentRoom;
    private static final int ROOM_CAPACITY = 2;

    void assignRoom(Client client) {
        if (currentRoom == null || currentRoom.isFull()) {
            currentRoom = createRoom();
        }
        currentRoom.connect(client);
    }

    private Room createRoom() {
        Room newRoom = new Room(ROOM_CAPACITY);
        roomList.add(newRoom);
        return newRoom;
    }
}
