package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ClientConnection{
    private Socket socket;

    ClientConnection(Socket socket) {

        this.socket = socket;
    }

    InputStream openInputStream() throws IOException {
        return socket.getInputStream();
    }

    OutputStream openOutputStream() throws IOException {
        return socket.getOutputStream();
    }
}
