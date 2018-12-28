package academy.konrad.group.battleships.server;

import academy.konrad.group.battleships.userinterface.FieldNumber;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class SingleGame implements Runnable {
  private final ObjectInputStream firstInputStream;
  private final ObjectInputStream secondInputStream;
  private final ObjectOutputStream firstOutputStream;
  private final ObjectOutputStream secondOutputStream;

  SingleGame(Socket firstClientSocket, Socket secondClientSocket) throws IOException {
    firstInputStream = new ObjectInputStream(firstClientSocket.getInputStream());
    secondInputStream = new ObjectInputStream(secondClientSocket.getInputStream());
    firstOutputStream = new ObjectOutputStream(firstClientSocket.getOutputStream());
    secondOutputStream = new ObjectOutputStream(secondClientSocket.getOutputStream());
  }

  @Override
  public void run() {
    boolean shouldContinue = true;
    Logger.info("Gra rozpoczęta");
    while (shouldContinue) {
      try {
        FieldNumber firstFieldNumber = (FieldNumber) firstInputStream.readObject();
        secondOutputStream.writeObject(firstFieldNumber);
        Logger.info(firstFieldNumber.toString());
        FieldNumber fieldNumber = (FieldNumber) secondInputStream.readObject();
        firstOutputStream.writeObject(fieldNumber);
        Logger.info(fieldNumber.toString());

        Thread.sleep(1000);
      } catch (InterruptedException | IOException | ClassNotFoundException exception) {
        Logger.error(exception.getMessage());
        shouldContinue = false;
      }
    }
  }
}