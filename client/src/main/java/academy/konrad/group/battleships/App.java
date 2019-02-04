package academy.konrad.group.battleships;

import academy.konrad.group.battleships.efficiencytest.TestMain;
import academy.konrad.group.battleships.userinterface.GuiMain;

public class App {

  public static void main(String[] args) {
      if(args[0].equals("-ng") || args[0].equals("--nogui"))
          TestMain.main();
      GuiMain.main(args);
  }
}