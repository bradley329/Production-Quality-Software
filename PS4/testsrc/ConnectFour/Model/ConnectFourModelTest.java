package ConnectFour.Model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import ConnectFour.View.ConnectFourLogger;

public class ConnectFourModelTest {
  @Test
  public void testConstructGameSetsDefaultConstants() {
    ConnectFourModel model = new ConnectFourModel();

    assertNotNull(model);
  }

  @Test
  public void testGameStarted() {
    ConnectFourModel model = new ConnectFourModel();
    ConnectFourLogger logger = new ConnectFourLogger();
    model.addListeners(logger);

    String expectedMessage = "game started";
    System.out.println(logger.getLog());

    assertFalse(logger.getLog().contains(expectedMessage));

    model.startGameVs('P');

    assertTrue(logger.getLog().contains(expectedMessage));
  }

  @Test
  public void testMoveAdded() {
    ConnectFourModel model = new ConnectFourModel();
    ConnectFourLogger logger = new ConnectFourLogger();
    model.addListeners(logger);

    String expectedMessage = "move added: {Red: (0, 0)}";

    assertFalse(logger.getLog().contains(expectedMessage));
    model.startGameVs('P');
    model.addMove(0);

    assertTrue(logger.getLog().contains(expectedMessage));
  }

  @Test
  public void testGetNextMoveForComputer() {
    ConnectFourModel model = new ConnectFourModel();
    model.startGameVs('C');
    model.addMove(0);
    int nextMove = model.getNextMoveForComputer();
    assertTrue(nextMove >= 0 && nextMove < 7);
  }

  @Test
  public void testGameWonBy() {
    ConnectFourModel model = new ConnectFourModel();
    ConnectFourLogger logger = new ConnectFourLogger();
    model.addListeners(logger);

    String expectedMessage = "game won by: {Red: (3, 0)}";

    assertFalse(logger.getLog().contains(expectedMessage));
    model.startGameVs('P');

    model.addMove(0);
    model.addMove(1);
    model.addMove(0);
    model.addMove(1);
    model.addMove(0);
    model.addMove(1);
    model.addMove(0);

    assertTrue(logger.getLog().contains(expectedMessage));
  }

  @Test
  public void testGameTiedBy() {
    ConnectFourModel model = new ConnectFourModel();
    ConnectFourLogger logger = new ConnectFourLogger();
    model.addListeners(logger);

    String expectedMessage = "game tied by: {Yellow: (5, 6)}";

    assertFalse(logger.getLog().contains(expectedMessage));
    model.startGameVs('P');

    for (int column : getMovesForTiedGame()) {
      model.addMove(column);
    }

    assertTrue(logger.getLog().contains(expectedMessage));
  }

  private List<Integer> getMovesForTiedGame() {
    // moves record column indices
    List<Integer> allMoves = new LinkedList<>();
    List<Integer> lastMoves = new LinkedList<>();

    for (int col = 0; col < 7; col++) {
      for (int row = 0; row < 6; row++) {

        // dont care about the row index
        if (col > 0 && col % 2 == 0 && row == 5) {
          lastMoves.add(col);
          continue;
        }
        allMoves.add(col);
      }
    }

    for (int column : lastMoves) {
      allMoves.add(column);
    }

    return allMoves;
  }
}
