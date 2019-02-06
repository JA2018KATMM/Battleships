package academy.konrad.group.battleships.communication;

/**
 * Reads input from user
 */
public interface UsersInputReader {

    String readLine();

    static UsersInputReader newConsoleReader() {
        return new ConsoleInputReader();
    }
}
