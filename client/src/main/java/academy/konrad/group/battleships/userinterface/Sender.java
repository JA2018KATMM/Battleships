package academy.konrad.group.battleships.userinterface;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Sender {
  private final PrintWriter out = new PrintWriter(new OutputStreamWriter(Connection.getOutputStream(), StandardCharsets.UTF_8), true);

  void shot(String id) {
    out.println("MOVE:" + id);
  }

  void close() {
    out.println("FINISH");
  }
}
