package academy.konrad.group.battleships.client.serverconnection;

import academy.konrad.group.battleships.client.communication.Message;

import java.io.OutputStream;
import java.io.PrintWriter;

class MessageSender {
    private final PrintWriter printWriter;

    private MessageSender(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    void sendToServer(Message message){
        printWriter.format("%s\r", message);
    }

    static MessageSender create(OutputStream outputStream) {
        return new MessageSender(new PrintWriter(outputStream, true));
    }
}
