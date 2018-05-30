package ConnectFour.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This separate board class is for reuse when the Computer trying to decide which move to make.
 * 
 * @author Bradley
 *
 */
public class Board {
  private final int NUM_ROWS = 6;
  private final int NUM_COLUMNS = 7;
  private Move[][] moves;
  private List<Move> moveHistory;
  private Player winner;
  private boolean gameIsOver;

  public Board() {
    moves = new Move[NUM_ROWS][NUM_COLUMNS];
    moveHistory = new LinkedList<>();
    winner = null;
    gameIsOver = false;
  }

  public Move addMove(Player currentPlayer, int column) throws IllegalStateException {
    if (column >= NUM_COLUMNS || column < 0) {
      throw new IllegalStateException("Column out of range.");
    }

    if (moves[NUM_ROWS - 1][column] != null) {
      throw new IllegalStateException("Column is full.");
    }

    // locate the right place where to put the piece
    int row = findRowIdxInColumn(column);

    Move move = new Move(currentPlayer, row, column);

    moves[row][column] = move;
    moveHistory.add(move);

    if (gameWouldWonBy(move)) {
      winner = move.getPlayer();
      gameIsOver = true;
    } else if (boardIsFull()) {
      gameIsOver = true;
    }

    return move;
  }

  private boolean boardIsFull() {
    for (int i = 0; i < NUM_COLUMNS; i++) {
      if (moves[NUM_ROWS - 1][i] == null) {
        return false;
      }
    }

    return true;
  }

  private int findRowIdxInColumn(int column) {
    int row = -1;

    for (int i = 0; i < NUM_ROWS; i++) {
      if (moves[i][column] == null) {
        row = i;
        break;
      }
    }

    return row;
  }

  private boolean gameWouldWonBy(Move move) {
    if (verticalWonBy(move) || horizontalWonBy(move) || diagonalWonBy(move)) {
      return true;
    }

    return false;
  }

  private boolean verticalWonBy(Move move) {
    if (move.getRowIdx() < 3) {
      return false;
    }

    int column = move.getColumnIdx();
    int numConsecutiveMoves = 1;

    // go through all moves below
    for (int i = move.getRowIdx() - 1; i >= 0; i--) {
      Move moveBelow = moves[i][column];

      if (moveBelow.getPlayer().equals(move.getPlayer())) {
        numConsecutiveMoves++;
      } else {
        // TODO: make sure it works
        return false;
      }

      if (numConsecutiveMoves == 4) {
        return true;
      }
    }

    return false;
  }

  private boolean horizontalWonBy(Move move) {
    int row = move.getRowIdx();
    int numConsecutiveMoves = 0;

    // simply go through all the moves in the row
    for (int i = 0; i < NUM_COLUMNS; i++) {
      Move lastMove = moves[row][i];

      if (lastMove == null || !move.getPlayer().equals(lastMove.getPlayer())) {
        numConsecutiveMoves = 0;
      } else {
        numConsecutiveMoves++;
      }

      if (numConsecutiveMoves == 4) {
        return true;
      }
    }

    return false;
  }

  private boolean diagonalWonBy(Move move) {
    int numConsecutiveMoves = 0;

    // when flag == 0, check from SW to NE; when flag == 1, check from NW to SE
    for (int flag = 0; flag < 2; flag++) {
      for (int offset = -3; offset <= 3; offset++) {

        int rowOffset = 0;
        int colOffset = offset;
        if (flag == 0) {
          rowOffset = offset;
        } else {
          rowOffset = -offset;
        }

        int row = move.getRowIdx() + rowOffset;
        int column = move.getColumnIdx() + colOffset;

        // if either index is out of bounds
        if (row >= NUM_ROWS || column >= NUM_COLUMNS || row < 0 || column < 0) {
          numConsecutiveMoves = 0;
          continue;
        }

        Move lastMove = moves[row][column];
        if (lastMove == null || !move.getPlayer().equals(lastMove.getPlayer())) {
          numConsecutiveMoves = 0;
        } else {
          numConsecutiveMoves++;
        }

        if (numConsecutiveMoves == 4) {
          return true;
        }
      }
    }

    return false;
  }

  private List<Move> getMoveHistory() {
    List<Move> moveHistoryCopy = new ArrayList<Move>(moveHistory.size());
    for (Move move : moveHistory) {
      moveHistoryCopy.add(move);
    }
    return moveHistoryCopy;
  }

  private Move[][] getMoves() {
    Move[][] movesCopy = new Move[NUM_ROWS][NUM_COLUMNS];
    for (int i = 0; i < NUM_ROWS; i++) {
      for (int j = 0; j < NUM_COLUMNS; j++) {
        movesCopy[i][j] = moves[i][j];
      }
    }
    return movesCopy;
  }

  public boolean gameOver() {
    return gameIsOver;
  }

  public Player getWinner() {
    return winner;
  }

  public Board createCopy() {
    Board board = new Board();
    board.moveHistory = getMoveHistory();
    board.moves = getMoves();
    return board;
  }
}
