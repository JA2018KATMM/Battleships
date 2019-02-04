package academy.konrad.group.battleships.efficiencytest;

import academy.konrad.group.battleships.message.Message;
import academy.konrad.group.battleships.message.MessageParser;
import academy.konrad.group.battleships.userinterface.Connection;
import org.pmw.tinylog.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Client {
    private final BufferedReader in;
    private boolean isRun = true;

    Client() {
        this.in = new BufferedReader(new InputStreamReader(Connection.getInputStream(), StandardCharsets.UTF_8));

    }


    void play() {

        Thread game = new Thread(() -> {
            try {
                while (isRun) {
                    String fromServer = in.readLine();
                    String title = MessageParser.getMessageTitle(fromServer);
                    String content = MessageParser.getMessageContent(fromServer);
                    Optional<Message> option = MessageParser.findChosenOption(title);
                    Message message;
                    if (option.isPresent()) {
                        message = option.get();
                        runOption(message, content);
                    }
                }
            } catch (IOException exception) {
                Logger.error(exception.getMessage());
                isRun = false;
            }
        });
        game.setName("Gra");
        game.start();
    }

    private void runOption(Message option, String content) {
        switch (option) {
            case MOVE:
                this.messageHandler.doMove(content);
                break;
            default:
                throw new IllegalStateException();

        }
}
