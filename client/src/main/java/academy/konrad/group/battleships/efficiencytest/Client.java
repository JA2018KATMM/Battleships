package academy.konrad.group.battleships.efficiencytest;

import academy.konrad.group.battleships.domain.Fleet;
import org.pmw.tinylog.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

class Client {

    private BufferedReader in;
    private PrintWriter out;
    private Fleet fleet = new Fleet();

    Client(String args) {
        Connection.initialize();
        in = new BufferedReader(new InputStreamReader(Connection.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintWriter(new OutputStreamWriter(Connection.getOutputStream(), StandardCharsets.UTF_8), true);
        Thread.currentThread().setName("Player number: " + args);
    }

    void play() {

        Logger.info("Client started with his own fleet: " + fleet.getShips());
        String fromServer;

        try {
            while ((fromServer = in.readLine()) != null) {

                if (fromServer.startsWith("MESSAGE")) {
                    if (fromServer.endsWith("all")) {
                        Logger.info("All players connected game started");
                        sendFleet();
                    } else Logger.info("Awaiting opponent to connect");
                } else if (fromServer.startsWith("WELCOME")) {
                    String message = "Welcome to the Battleship game!";
                    Logger.info(message);
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

    private void sendFleet() {
        Logger.info("Fleet " + fleet.getShips() + " has been sent to opponent");
        out.println("MOVE:" + fleet.getShips());
    }
}