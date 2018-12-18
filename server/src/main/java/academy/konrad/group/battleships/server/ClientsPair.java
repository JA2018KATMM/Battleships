package academy.konrad.group.battleships.server;

import academy.konrad.group.battleships.userinterface.FieldNumber;

import java.io.*;
import java.net.Socket;

class ClientsPair implements Runnable {
  private Socket firstClientSocket;
  private Socket secondClientSocket;
  //private DataInputStream dis;
  //private DataOutputStream dos;
  //private final LoggedClientsSet loggedClientsSet;

  ClientsPair(Socket firstClientSocket, Socket secondClientSocket) {
    this.firstClientSocket = firstClientSocket;
    this.secondClientSocket = secondClientSocket;
    //this.loggedClientsSet = clients;
  }

  @Override
  public void run(){

    while (true) {
      try {
        System.out.println("Watek na serwerze");
        ObjectInputStream firstOutoutStream = new ObjectInputStream(this.firstClientSocket.getInputStream());
        FieldNumber firstFieldNumber = (FieldNumber) firstOutoutStream.readObject();
        System.out.println(firstFieldNumber);
        ObjectOutputStream firstOutputStream = new ObjectOutputStream(this.secondClientSocket.getOutputStream());
        firstOutputStream.writeObject(firstFieldNumber);

        ObjectInputStream secondInputStream = new ObjectInputStream(this.secondClientSocket.getInputStream());
        FieldNumber fieldNumber = (FieldNumber) secondInputStream.readObject();
        System.out.println(fieldNumber);
        ObjectOutputStream secondOutputStream = new ObjectOutputStream(this.firstClientSocket.getOutputStream());
        secondOutputStream.writeObject(fieldNumber);

      } catch (IOException exception) {
        exception.printStackTrace();
        return;
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  /*
  void close() {
    try {
      this.dis.close();
      this.dos.close();
      clientSocket.close();
      loggedClientsSet.removeClient(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  */
}