package academy.konrad.group.battleships.domain;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Fleet {

  private final Set<Integer> ships = new HashSet<>();

  public Fleet(){
    this.allocateFleet();
  }

  public Set<Integer> getShips() {
    return ships;
  }

  private void allocateFleet() {
    for (int i = 0; i < 4; i++) {
      ships.add(new Random().nextInt(100));
    }
  }
}
