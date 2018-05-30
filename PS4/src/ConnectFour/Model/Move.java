package ConnectFour.Model;

/**
 * This is the class for placing any move.
 * 
 * @author Bradley
 *
 */
public class Move {
  private final Player player;
  private final int rowIdx;
  private final int columnIdx;

  /**
   * Constructor for move.
   * 
   * @param player the owner of this move.
   * @param row the row index for this move.
   * @param column the column index for this move.
   */
  public Move(Player player, int row, int column) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (row < 0 || column < 0) {
      throw new IllegalArgumentException("Row and column index cannot be negative.");
    }

    this.player = player;
    this.rowIdx = row;
    this.columnIdx = column;
  }

  /**
   * The row index for this move in the board.
   * 
   * @return row index
   */
  public int getRowIdx() {
    return rowIdx;
  }

  /**
   * The column index for this move the board.
   * 
   * @return column index
   */
  public int getColumnIdx() {
    return columnIdx;
  }

  /**
   * The player to whom this piece belongs.
   * 
   * @return the owner of this piece.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * The string representation of the player.
   * 
   * @return the string representation of player
   */
  public String toString() {
    return String.format("%s: (%d, %d)", player, rowIdx, columnIdx);
  }
}
