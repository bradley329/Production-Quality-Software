package ConnectFour.View;

import ConnectFour.Model.Move;

public class ConnectFourLogger implements ConnectFourListener {
  StringBuilder sb = new StringBuilder();

  @Override
  public void gameStarted() {
    sb = new StringBuilder();
    sb.append("game started");
    sb.append("\n");
  }

  @Override
  public void gameWonBy(Move move) {
    sb.append("game won by: " + "{" + move + "}");
    sb.append("\n");
  }

  @Override
  public void gameTied(Move move) {
    sb.append("game tied by: " + "{" + move + "}");
    sb.append("\n");
  }

  @Override
  public void moveAdded(Move move, boolean computerTurn) {
    sb.append("move added: " + "{" + move + "}");
    sb.append("\n");
  }

  public String getLog() {
    return sb.toString();
  }
}
