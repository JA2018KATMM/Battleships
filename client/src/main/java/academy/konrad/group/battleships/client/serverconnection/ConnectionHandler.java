package academy.konrad.group.battleships.client.serverconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ConnectionHandler {

    private final String hostName;
    private final int portNumber;
    private Socket clientSocket;

    private ConnectionHandler(String hostName, int portNumber){
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    Socket connectToServerSocket() throws IOException {
        return new Socket(hostName, portNumber);
    }

    InputStream openInputStream() throws IOException {
        return clientSocket.getInputStream();
    }

    OutputStream openOutputStream() throws IOException {
        return clientSocket.getOutputStream();
    }



    static ConnectionHandler create(String hostName, int portNumber){
        return new ConnectionHandler(hostName, portNumber);
    }
}
