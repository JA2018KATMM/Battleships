package academy.konrad.group.battleships.client.serverconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ConnectionHandler {

    private final Socket clientSocket;

    private ConnectionHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    InputStream openInputStream() throws IOException {
        return clientSocket.getInputStream();
    }

    OutputStream openOutputStream() throws IOException {
        return clientSocket.getOutputStream();
    }

    static ConnectionHandler create(String hostName, int portNumber) throws IOException {
        return new ConnectionHandler(new Socket(hostName, portNumber));
    }
}
