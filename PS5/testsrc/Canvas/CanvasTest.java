package Canvas;

import java.awt.Point;
import org.junit.Test;
import Canvas.Model.CanvasModel;
import CanvasView.CanvasView;

public class CanvasTest {
  @Test
  public void testGameStarted() {
    CanvasModel model = CanvasModel.getInstance();
    new CanvasView(model);
    model.start();
  }

  @Test
  public void testCanvasModel() {
    CanvasModel model = CanvasModel.getInstance();
    new CanvasView(model);
    model.drawLine(new Point(-1, 0), new Point(2, 2));
    model.drawLine(new Point(0, -1), new Point(2, 2));
    model.drawLine(new Point(2, 2), new Point(-1, 0));
    model.drawLine(new Point(2, 2), new Point(0, -1));
    model.drawLine(new Point(1, 1), new Point(2, 2));
  }
}
