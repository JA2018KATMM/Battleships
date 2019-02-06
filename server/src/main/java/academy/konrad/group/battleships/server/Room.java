package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

class Room implements Runnable{

    private Clients clients;
    private int roomCapacity;
    private final TransferQueue<Message> roomClientsMessagesQueue = new LinkedTransferQueue<>();
    private final ThreadsManager threadsManager;

    private Room(int roomCapacity, ExecutorService executorService) {
        this.roomCapacity = roomCapacity;
        this.clients = new Clients(roomCapacity);
        this.threadsManager = new ThreadsManager(executorService);
    }

    void connect(ClientConnection clientConnection) throws IOException {
        Client client = Client.createClient(clientConnection, roomClientsMessagesQueue, threadsManager);
        this.clients.add(client);
    }

    boolean isFull() {
        return roomCapacity == clients.size();
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                clients.closeDisconnectedClients();
                if(isFull()) {
                    clients.messageAll(roomClientsMessagesQueue.take());
                }
            } catch (InterruptedException e) {
                System.err.println("Room interrupted while reading message from clients");
            }
        }
    }

    static Room createRoom(int roomCapacity) {
        //size of pool is roomCapacity + 1 as each client has a listening thread and only one shared thread is sending messages to clients.
        ExecutorService executorService = Executors.newFixedThreadPool(roomCapacity + 1, new ServerThreadsFactory("ClientThread"));
        return new Room(roomCapacity, executorService);
    }
}