package academy.konrad.group.battleships.server;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.testng.Assert.assertEquals;

public class LoggedClientTest {

  @Test
  public void shouldInformClientWithGivenMessage() throws IOException {
    // given
    String whyItFailed = "not writing data to output stream";
    Socket socket = Mockito.mock(Socket.class);
    DataInputStream inputStream = new DataInputStream(System.in);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream outputStream = new DataOutputStream(byteArrayOutputStream);
    LoggedClientsSet clients = new LoggedClientsSet();
    LoggedClient loggedClient = new LoggedClient(socket, inputStream, outputStream, clients);
    String testMessage = "test message";

    // when
    loggedClient.inform(testMessage);

    // then
    assertEquals(byteArrayOutputStream.toString().substring(2), testMessage);
  }

}