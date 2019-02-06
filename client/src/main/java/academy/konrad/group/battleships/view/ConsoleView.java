package academy.konrad.group.battleships.view;

import java.io.PrintStream;

class ConsoleView implements ChatView {

    private final PrintStream out = System.out;

    @Override
    public void displayMessage(Message message) {
        out.println(message);
    }
}
