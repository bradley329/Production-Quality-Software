package ConnectFour.Model;

import static org.junit.Assert.assertEquals;
import java.awt.Color;
import org.junit.Test;

public class PlayerTest {
  @Test
  public void testConstuctorAndGetter() {
    Player player = Player.RED;
    assertEquals(Color.RED, player.getColor());
    assertEquals("Red", player.name());
  }

  @Test
  public void testToString() {
    Player player = Player.RED;
    assertEquals("Red", player.toString());
  }
}
