package academy.konrad.group.battleships.server;

import academy.konrad.group.battleships.userinterface.FieldNumber;
import org.pmw.tinylog.Logger;

import java.io.*;
import java.net.Socket;

public class SingleApp implements Runnable {

    private final ObjectInputStream inputStream;
    private final ObjectOutput outputStream;


    public SingleApp(Socket client) throws IOException {
        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
    }

    @Override
    public void run() {
        boolean shouldContinue = true;
        while (shouldContinue) {
            try {
                FieldNumber toSend = (FieldNumber) inputStream.readObject();
                Logger.error(toSend.toString());
                outputStream.writeObject(toSend);
                Logger.error(toSend.toString());
                Thread.sleep(1000);
            } catch (InterruptedException | IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
                shouldContinue = false;
            }
        }
    }
}
