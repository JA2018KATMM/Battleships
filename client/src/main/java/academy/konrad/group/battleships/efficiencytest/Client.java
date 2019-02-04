package academy.konrad.group.battleships.efficiencytest;

import academy.konrad.group.battleships.domain.Fleet;

import java.io.*;
import java.nio.charset.StandardCharsets;

class Client {

    private BufferedReader in;
    private PrintWriter out;
    private Fleet fleet = new Fleet();

    Client() {
        Connection.initialize();
        in = new BufferedReader(new InputStreamReader(Connection.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintWriter(new OutputStreamWriter(Connection.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    void play() {

        Thread t = new Thread(() -> {
            String fromServer;

            try {
                while ((fromServer = in.readLine()) != null) {

                    if (fromServer.startsWith("MESSAGE")) {
                        System.out.println("Server: " + fromServer);
                        String message = fromServer;
                    } else if (fromServer.startsWith("WELCOME")) {
                        String message = "Welcome to the Battleship game!";
                        System.out.println(message);
                    } else if (fromServer.startsWith("MOVE")) {
                        String fieldShot = fromServer.substring(4);
                        String message = "Opponent's fleet: " + fieldShot;
                        System.out.println(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    void sendFleet() {
        System.out.println("Players fleet: " + fleet.getShips());
        out.println("MOVE:" + fleet.getShips());
    }
}