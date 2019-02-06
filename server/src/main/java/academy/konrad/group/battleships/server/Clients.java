package academy.konrad.group.battleships.server;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.ArrayList;
import java.util.List;

class Clients {

    private final List<Client> clientsList;

    Clients(int capacity) {
        this.clientsList = Collections.synchronizedList(new ArrayList<>(capacity));
    }

    void add(Client client) {
        this.clientsList.add(client);
    }

    int size() {
        return this.clientsList.size();
    }

    void messageAll(Message message) {
        clientsList.forEach(client -> client.sendMessage(message));
    }

    void closeDisconnectedClients() {
        clientsList.forEach(client -> {
            if(client.isDisconnected()) {
                client.closeConnection();
                clientsList.remove(client);
            }
        });
    }
}
