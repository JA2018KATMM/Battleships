package academy.konrad.group.battleships.server;

class Game {

  Player currentPlayer;
  Player waitingPlayer;

  void changeTurn() {
    Player temp = currentPlayer;
    currentPlayer = waitingPlayer;
    waitingPlayer = temp;
  }
}