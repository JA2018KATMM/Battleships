package academy.konrad.group.battleships.client.view;

import academy.konrad.group.battleships.client.communication.Message;

/**
 * Displays user interface and messages
 */
public interface ChatView {

    void displayMessage(Message message);

    static ChatView newConsoleView() {
        return new ConsoleView();
    }
}
