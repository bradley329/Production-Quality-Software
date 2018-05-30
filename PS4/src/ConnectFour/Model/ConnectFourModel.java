package ConnectFour.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ConnectFour.View.ConnectFourListener;

public class ConnectFourModel {
  private final int NUM_ROWS = 6;
  private final int NUM_COLUMNS = 7;

  private final Player player1;
  private final Player player2;
  private List<ConnectFourListener> listeners;

  // this is the part which would be initialized after start game.
  private Player currentPlayer;
  private boolean vsComputer;
  private Board board;

  /**
   * Constructor for ConnectFourModel.
   */
  public ConnectFourModel() {
    this.player1 = Player.RED;
    this.player2 = Player.YELLOW;
    this.listeners = new ArrayList<>();
  }

  /**
   * Bind listeners to the model.
   * 
   * @param listener the listener to bind.
   */
  public void addListeners(ConnectFourListener listener) {
    listeners.add(listener);
  }

  /**
   * Start a new game and player1(human) moves first.
   * 
   * @param anotherPlayer indicates player2 is human or computer.
   */
  public void startGameVs(char anotherPlayer) {
    this.board = new Board();
    currentPlayer = player1;
    if (anotherPlayer == 'C') {
      vsComputer = true;
    } else {
      vsComputer = false;
    }

    fireGameStartedEvent();
  }

  private void fireGameStartedEvent() {
    for (ConnectFourListener listener : listeners) {
      listener.gameStarted();
    }
  }

  /**
   * Add a move to the board. Players take turns one by one.
   * 
   * @param column the column index where to place a move.
   * @throws IllegalStateException if the column is full or out of bounds.
   */
  public void addMove(int column) throws IllegalStateException {
    // when trying to add move to the board, it might throw exception.
    Move move = board.addMove(currentPlayer, column);
    currentPlayer = currentPlayer.equals(player1) ? player2 : player1;

    if (board.gameOver()) {
      Player winner = board.getWinner();
      if (winner == null) {
        fireGameTiedEvent(move);
      } else {
        fireGameWonEvent(move);
      }
    } else {
      fireMoveAddedEvent(move);
    }
  }

  private void fireMoveAddedEvent(Move move) {
    boolean computerTurn = false;
    if (vsComputer && currentPlayer.equals(player2) && !board.gameOver()) {
      computerTurn = true;
    }
    for (ConnectFourListener listener : listeners) {
      listener.moveAdded(move, computerTurn);
    }
  }

  private void fireGameWonEvent(Move move) {
    for (ConnectFourListener listener : listeners) {
      listener.gameWonBy(move);
    }
  }

  private void fireGameTiedEvent(Move move) {
    for (ConnectFourListener listener : listeners) {
      listener.gameTied(move);
    }
  }

  /**
   * For computer, select a move which results in a win. Otherwise, return a random column index.
   * 
   * @return the column index, or -1 is no move available.
   */
  public int getNextMoveForComputer() {
    List<Integer> possibleColumns = new ArrayList<Integer>();

    // whether the human player has won the game?
    if (board.gameOver()) {
      return -1;
    }

    // try out all possible columns
    for (int i = 0; i < NUM_COLUMNS; i++) {

      Board boardCopy = board.createCopy();

      try {
        boardCopy.addMove(currentPlayer, i);
      } catch (IllegalStateException e) {
        continue;
      }

      if (boardCopy.gameOver()) {
        return i;
      } else {
        possibleColumns.add(i);
      }
    }

    Collections.shuffle(possibleColumns);

    return possibleColumns.get(0);
  }
}
