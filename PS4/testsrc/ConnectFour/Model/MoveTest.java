package ConnectFour.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MoveTest {
  @Test(expected = IllegalArgumentException.class)
  public void testConstructmove_withNullPlayer() {
    Move move = new Move(null, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructmove_withNegativeColumn() {
    Move move = new Move(Player.RED, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructmove_withNegativeRow() {
    Move move = new Move(Player.RED, -1, 0);
  }

  @Test
  public void testConstructorAndGetter() {
    Move move = new Move(Player.RED, 3, 3);
    assertEquals(Player.RED, move.getPlayer());
    assertEquals(3, move.getRowIdx());
    assertEquals(3, move.getColumnIdx());
  }

  @Test
  public void testToString() {
    Move move = new Move(Player.RED, 3, 3);
    String moveString = move.toString();

    assertTrue(moveString.contains("Red"));
    assertTrue(moveString.contains("3"));
    assertTrue(moveString.contains("3"));
  }
}
