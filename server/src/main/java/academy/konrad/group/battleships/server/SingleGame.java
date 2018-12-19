package academy.konrad.group.battleships.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class SingleGame implements Runnable {
  private final ObjectInputStream firstIS;
  private final ObjectInputStream secondIS;
  private final ObjectOutputStream firstOS;
  private final ObjectOutputStream secondOS;

  SingleGame(Socket firstClientSocket, Socket secondClientSocket) throws IOException {
    firstIS = new ObjectInputStream(firstClientSocket.getInputStream());
    secondIS = new ObjectInputStream(secondClientSocket.getInputStream());
    firstOS = new ObjectOutputStream(firstClientSocket.getOutputStream());
    secondOS = new ObjectOutputStream(secondClientSocket.getOutputStream());
  }

  @Override
  public void run() {

    try {
      while (true) {
          FieldNumber firstFieldNumber = (FieldNumber) firstIS.readObject();
          secondOS.writeObject(firstFieldNumber);

          FieldNumber fieldNumber = (FieldNumber) secondIS.readObject();
          firstOS.writeObject(fieldNumber);

          firstOS.flush();
          secondOS.flush();
      }
    } catch (IOException|ClassNotFoundException  exception) {
      exception.printStackTrace();
    }
    return null;
  }

}