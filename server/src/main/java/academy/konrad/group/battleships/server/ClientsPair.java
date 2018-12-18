package academy.konrad.group.battleships.server;

import academy.konrad.group.battleships.userinterface.FieldNumber;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientsPair implements Runnable {
  private Socket firstClientSocket;
  private Socket secondClientSocket;

  ClientsPair(Socket firstClientSocket, Socket secondClientSocket) {
    this.firstClientSocket = firstClientSocket;
    this.secondClientSocket = secondClientSocket;

  }

  @Override
  public void run() {

    while (true) {
      try {
        System.out.println("Watek na serwerze");
        ObjectInputStream firstInputStream =
            new ObjectInputStream(this.firstClientSocket.getInputStream());
        FieldNumber firstFieldNumber = (FieldNumber) firstInputStream.readObject();
        System.out.println(firstFieldNumber);
        ObjectOutputStream firstOutputStream =
            new ObjectOutputStream(this.secondClientSocket.getOutputStream());
        firstOutputStream.writeObject(firstFieldNumber);

        ObjectInputStream secondInputStream =
            new ObjectInputStream(this.secondClientSocket.getInputStream());
        FieldNumber fieldNumber = (FieldNumber) secondInputStream.readObject();
        System.out.println(fieldNumber);
        ObjectOutputStream secondOutputStream =
            new ObjectOutputStream(this.firstClientSocket.getOutputStream());
        secondOutputStream.writeObject(fieldNumber);

      } catch (IOException exception) {
        exception.printStackTrace();
        return;
      } catch (ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
  }

}