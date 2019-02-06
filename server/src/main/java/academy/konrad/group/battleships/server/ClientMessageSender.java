package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

class ClientMessageSender {
    private final PrintWriter printWriter;

    private ClientMessageSender(OutputStream outputStream) {
        this.printWriter = new PrintWriter(outputStream, true);
    }

    void send(Message message) {
        printWriter.format("%s\r", message.toString());
    }

    void close() {
        printWriter.close();
    }

    static ClientMessageSender createMessageSender(ClientConnection clientConnection) throws IOException {
        return new ClientMessageSender(clientConnection.openOutputStream());
    }
}
