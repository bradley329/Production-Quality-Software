package Canvas.Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import CanvasView.CanvasListener;

public class CanvasModel {
  private static CanvasModel INSTANCE = new CanvasModel();
  private List<CanvasListener> listeners = new ArrayList<CanvasListener>();

  private CanvasModel() {}

  /**
   * This is the singleton pattern since only one instance of model is allowed.
   * 
   * @return the singleton instance of CanvasModel.
   */
  public static CanvasModel getInstance() {
    return INSTANCE;
  }

  /**
   * Add listener to the model.
   * 
   * @param listener the listener to be added.
   */
  public void addListener(CanvasListener listener) {
    listeners.add(listener);
  }

  /**
   * Start drawing.
   */
  public void start() {
    fireDrawingStartedEvent();
  }

  private void fireDrawingStartedEvent() {
    for (CanvasListener listener : listeners) {
      listener.drawStarted();
    }
  }

  /**
   * Called when a line is to be drawn.
   * 
   * @param startPoint the start point of line.
   * @param endPoint the end point of line.
   */
  public void drawLine(Point startPoint, Point endPoint) {
    // Check if points in bound.
    if (!inBound(startPoint)) {
      firePointOutOfBoundEvent(startPoint);
      return;
    }
    if (!inBound(endPoint)) {
      firePointOutOfBoundEvent(endPoint);
      return;
    }

    fireDrawingAddedEvent(startPoint, endPoint);
  }

  private boolean inBound(Point point) {
    if (point.x < 0 || point.y < 0 || point.x >= Size.WIDTH || point.y >= Size.LENGTH) {
      return false;
    }
    return true;
  }

  private void firePointOutOfBoundEvent(Point startPoint) {
    for (CanvasListener listener : listeners) {
      listener.outOfBound();
    }
  }

  private void fireDrawingAddedEvent(Point startPoint, Point endPoint) {
    for (CanvasListener listener : listeners) {
      listener.drawLine(startPoint, endPoint);
    }
  }
}
