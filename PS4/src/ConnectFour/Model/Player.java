package ConnectFour.Model;

import java.awt.Color;

/**
 * Enum class for defining player.
 * 
 * @author Bradley
 *
 */
public enum Player {
  RED(Color.RED, "Red"), YELLOW(Color.YELLOW, "Yellow");

  private final Color color;
  private final String name;

  /**
   * Constructor for player.
   * 
   * @param color the color of the pieces the player place.
   * @param name String representation of color.
   */
  private Player(Color color, String name) {
    this.color = color;
    this.name = name;
  }

  /**
   * Getter got color.
   * 
   * @return color the color of this player's piece.
   */
  public Color getColor() {
    return color;
  }

  /**
   * @return String representation of player.
   */
  @Override
  public String toString() {
    return name;
  }
}
