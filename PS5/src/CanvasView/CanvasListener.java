package CanvasView;

import java.awt.Point;

public interface CanvasListener {

  /**
   * Called when game started.
   */
  public void drawStarted();

  /**
   * Called when drawing a line.
   * 
   * @param st the start point.
   * @param end the end point.
   */
  public void drawLine(Point st, Point end);

  /**
   * Warn the user when drawing a line outside the canvas.
   */
  public void outOfBound();
}
