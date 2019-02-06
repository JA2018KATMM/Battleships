package academy.konrad.group.battleships.view;

/**
 * Displays user interface and messages
 */
public interface ChatView {

    void displayMessage(Message message);

    static ChatView newConsoleView() {
        return new ConsoleView();
    }
}
