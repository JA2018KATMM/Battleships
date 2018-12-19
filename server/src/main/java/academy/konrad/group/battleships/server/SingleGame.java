package academy.konrad.group.battleships.server;

import academy.konrad.group.battleships.userinterface.FieldNumber;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class SingleGame implements Runnable {
  private Socket firstClientSocket;
  private Socket secondClientSocket;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream objectInputStream;

  SingleGame(Socket firstClientSocket, Socket secondClientSocket) {
    this.firstClientSocket = firstClientSocket;
    this.secondClientSocket = secondClientSocket;

  }

  @Override
  public void run() {

    try {
      this.objectOutputStream = new ObjectOutputStream(this.firstClientSocket.getOutputStream());
      this.objectOutputStream.writeObject(Boolean.getBoolean("true"));
      this.objectOutputStream = new ObjectOutputStream(this.secondClientSocket.getOutputStream());
      this.objectOutputStream.writeObject(Boolean.getBoolean("true"));


      while (true) {

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

      }
    }catch (IOException exception) {
        exception.printStackTrace();
        return;
      } catch (ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }

}