package academy.konrad.group.battleships.client.communication;

/**
 * Reads input from user
 */
public interface UsersInputReader{

    String readLine();

    static UsersInputReader newConsoleReader() {
        return new ConsoleInputReader();
    }
}
