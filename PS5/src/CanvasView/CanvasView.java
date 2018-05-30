package CanvasView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Canvas.Model.CanvasModel;
import Canvas.Model.Size;

public class CanvasView implements CanvasListener {
  private JFrame frame = new JFrame();
  private JPanel panel = new JPanel();
  private final CanvasModel model;
  private JButton resetBtn = new JButton("Reset");
  private Point startPoint;
  private Point endPoint;

  /**
   * Constructor that set up the view and bind model to the view.
   * 
   * @param model the model which will be binded to the view.
   */
  public CanvasView(CanvasModel model) {
    this.model = model;
    model.addListener(this);
    resetBtn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        resetCanvas();
      }

    });
    JPanel btnPanel = new JPanel(new BorderLayout());
    btnPanel.add(resetBtn, BorderLayout.CENTER);
    panel.add(btnPanel, BorderLayout.SOUTH);
    panel.setBackground(Color.LIGHT_GRAY);
    panel.setSize(Size.WIDTH, Size.LENGTH);
    panel.addMouseListener(new MouseAdapter() {

      @Override
      public void mousePressed(MouseEvent e) {
        startPoint = new Point(e.getX(), e.getY());
      }

    });

    panel.addMouseMotionListener(new MouseAdapter() {

      @Override
      public void mouseDragged(MouseEvent e) {
        endPoint = new Point(e.getX(), e.getY());
        model.drawLine(startPoint, endPoint);
        startPoint = endPoint;
      }

    });
    frame.getContentPane().add(panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Size.WIDTH + 20, Size.LENGTH + 20);
    frame.setVisible(true);
    model.start();
  }

  private void resetCanvas() {
    model.start();
  }

  private void clearCanvas() {
    Graphics graphics = panel.getGraphics();
    graphics.setColor(Color.LIGHT_GRAY);
    graphics.fillRect(0, 0, Size.WIDTH, Size.LENGTH);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void drawStarted() {
    clearCanvas();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void drawLine(Point st, Point end) {
    Graphics graphics = panel.getGraphics();
    graphics.setColor(Color.RED);
    graphics.drawLine(st.x, st.y, end.x, end.y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void outOfBound() {
    JOptionPane.showMessageDialog(frame, "Please do not draw outside the canvas.", "Warning",
        JOptionPane.WARNING_MESSAGE);
  }
}
