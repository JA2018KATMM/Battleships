package academy.konrad.group.battleships.server;

import java.util.ArrayList;
import java.util.List;

class Clients {

    private final List<Client> clientsList;

    Clients(int capacity) {
        this.clientsList = new ArrayList<>(capacity);
    }

    void add(Client client) {
        this.clientsList.add(client);
    }

    int size() {
        return this.clientsList.size();
    }
}
