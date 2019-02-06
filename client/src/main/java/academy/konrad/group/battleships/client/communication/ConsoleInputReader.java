package academy.konrad.group.battleships.communication;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class ConsoleInputReader implements UsersInputReader {

    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
