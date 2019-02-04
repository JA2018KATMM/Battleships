package academy.konrad.group.battleships.server;

import java.net.Socket;

class Client {
    private Socket socket;

    Client(Socket socket) {

        this.socket = socket;
    }
}
