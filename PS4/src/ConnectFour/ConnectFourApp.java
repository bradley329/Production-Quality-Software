package ConnectFour;

import ConnectFour.Model.ConnectFourModel;
import ConnectFour.View.ConnectFourView;

public class ConnectFourApp {
  private static final ConnectFourApp INSTANCE = new ConnectFourApp();

  private ConnectFourApp() {

  }

  private static ConnectFourApp getInstance() {
    return INSTANCE;
  }

  private void startGame() {
    ConnectFourModel model = new ConnectFourModel();
    new ConnectFourView(model);
  }

  public static void main(String[] args) {
    ConnectFourApp.getInstance().startGame();
  }
}
