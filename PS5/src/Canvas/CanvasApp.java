package Canvas;

import Canvas.Model.CanvasModel;
import CanvasView.CanvasView;

public class CanvasApp {
  void start() {
    CanvasModel model = CanvasModel.getInstance();

    new CanvasView(model);
    new CanvasView(model);
    new CanvasView(model);
  }

  public static void main(String[] args) {
    CanvasApp app = new CanvasApp();
    app.start();
  }
}
