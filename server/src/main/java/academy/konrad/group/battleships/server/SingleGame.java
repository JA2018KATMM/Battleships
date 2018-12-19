package academy.konrad.group.battleships.server;

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
      sendToClient(this.firstClientSocket, Boolean.TRUE);
      sendToClient(this.secondClientSocket, Boolean.FALSE);

      while (true) {

        System.out.println("Watek na serwerze");
        Object receivedFromFirst = receiveFromClient(this.firstClientSocket);
        Thread.sleep(1000);
        sendToClient(this.secondClientSocket, receivedFromFirst);
        Thread.sleep(1000);
        Object receivedFromSecond = receiveFromClient(this.secondClientSocket);
        Thread.sleep(1000);
        sendToClient(this.firstClientSocket, receivedFromSecond);
        Thread.sleep(1000);

      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void sendToClient(Socket client, Object objectToSend) {
    try {
      client.setSoTimeout(5000);
      this.objectOutputStream = new ObjectOutputStream(client.getOutputStream());
      this.objectOutputStream.writeObject(objectToSend);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  private Object receiveFromClient(Socket client) {
    try {
      client.setSoTimeout(5000);
      this.objectInputStream = new ObjectInputStream(client.getInputStream());
      Object object = this.objectInputStream.readObject();
      return object;
    } catch (IOException | ClassNotFoundException exception) {
      exception.printStackTrace();
    }
    //TODO custom wyjatek braku polaczenia
    throw new IllegalStateException();
  }

}