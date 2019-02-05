package academy.konrad.group.battleships.efficiencytest;

import academy.konrad.group.battleships.domain.Fleet;
import org.pmw.tinylog.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

class Client implements Runnable {

    private BufferedReader in;
    private PrintWriter out;
    private Fleet fleet = new Fleet();
    private int clientNumber;

    Client(int clientNumber) {
        Connection connection = new Connection();
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8), true);
        this.clientNumber = clientNumber;
    }

    private void sendFleet() {
        Logger.info("Fleet " + fleet.getShips() + " has been sent to opponent");
        out.println("MOVE:" + fleet.getShips());
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Player number: " + clientNumber);
        Logger.info("Client started with his own fleet: " + fleet.getShips());
        String fromServer;

        try {
            while ((fromServer = in.readLine()) != null) {

                if (fromServer.startsWith("MESSAGE")) {
                    if (fromServer.endsWith("all")) {
                        sendFleet();
                    }
                } else if (fromServer.startsWith("MOVE")) {
                    String fieldShot = fromServer.substring(4);
                    String message = "Opponent's fleet received: " + fieldShot;
                    Logger.info(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}