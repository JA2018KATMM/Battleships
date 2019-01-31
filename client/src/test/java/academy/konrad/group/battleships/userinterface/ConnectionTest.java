package academy.konrad.group.battleships.userinterface;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.testng.Assert.*;

public class ConnectionTest {

    @Test
    public void shouldHandle10_000client(){
        for(int i = 0; i < 100; i++){
            initialize();
        }
    }

    private void initialize()  {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress
                    ("localhost", 8081);
            new Socket().connect(socketAddress);
        } catch (IOException e) {
        }
    }

}