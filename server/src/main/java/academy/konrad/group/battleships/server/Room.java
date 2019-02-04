package academy.konrad.group.battleships.server;

class Room {

    private Clients clients;
    private int roomCapacity;

    Room(int roomCapacity) {
        this.roomCapacity = roomCapacity;
        this.clients = new Clients(roomCapacity);
    }

    void connect(Client client) {
        this.clients.add(client);
    }

    boolean isFull() {
        return roomCapacity == clients.size();
    }

}
