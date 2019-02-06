package academy.konrad.group.battleships.client.view;

import academy.konrad.group.battleships.client.communication.Message;

import java.io.PrintStream;

class ConsoleView implements ChatView {

    private final PrintStream out = System.out;

    @Override
    public void displayMessage(Message message) {
        out.println(message);
    }
}
