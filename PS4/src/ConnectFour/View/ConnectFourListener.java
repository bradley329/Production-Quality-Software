package ConnectFour.View;

import ConnectFour.Model.Move;

/**
 * The listener interface for the view and logger. Note that we do not use the logger when playing
 * the game. The logger is used for tests.
 * 
 * @author Bradley
 *
 */
public interface ConnectFourListener {
  /**
   * Invoked right after start the game.
   */
  void gameStarted();

  /**
   * Invoked right after a move wan the game.
   * 
   * @param move the move that won the game
   */
  void gameWonBy(Move move);

  /**
   * Invoked right after a move tied the game.
   * 
   * @param move the move that tied the game.
   */
  void gameTied(Move move);

  /**
   * Invoked right after a new move is placed on board.
   * 
   * @param move the move that was placed.
   */
  void moveAdded(Move move, boolean computerTurn);
}
