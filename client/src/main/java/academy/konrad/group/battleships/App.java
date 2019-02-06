package academy.konrad.group.battleships;

import academy.konrad.group.battleships.communication.UsersInputReader;
import academy.konrad.group.battleships.view.ChatView;

public class App {

  public static void main(String[] args) {
      Thread.currentThread().setName("ClientMainThread");
      ChatView view = ChatView.newConsoleView();
      UsersInputReader inputReader = UsersInputReader.newConsoleReader();
  }
}