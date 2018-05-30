package ConnectFour.View;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ConnectFour.Model.ConnectFourModel;
import ConnectFour.Model.Move;
import ConnectFour.Model.Player;

public class ConnectFourView implements ConnectFourListener {

  private final int NUM_ROWS = 6;
  private final int NUM_COLUMNS = 7;
  private JFrame frame = new JFrame("ConnectFour");
  private JPanel boardPanel;
  private JButton[][] piecesGrid;
  private ConnectFourModel model;

  public ConnectFourView(ConnectFourModel model) {
    this.model = model;
    model.addListeners(this);

    boardPanel = new JPanel(new GridLayout(NUM_ROWS, NUM_COLUMNS));
    piecesGrid = new JButton[NUM_ROWS][NUM_COLUMNS];

    initBoardAndPieces();

    frame.getContentPane().add(boardPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 600);
    frame.setVisible(true);

    promptToStart("Please choose how you wanna play.");
  }

  private void initBoardAndPieces() {
    for (int i = 0; i < NUM_ROWS; i++) {
      for (int j = 0; j < NUM_COLUMNS; j++) {
        JButton piece = createPiece(j);
        piece.setBackground(Color.WHITE);
        boardPanel.add(piece);
        piecesGrid[i][j] = piece;
      }
    }
  }

  private JButton createPiece(int column) {
    JButton piece = new JButton();

    piece.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        addMove(column);
      }
    });

    return piece;
  }

  private void addMove(int column) {
    try {
      model.addMove(column);
    } catch (IllegalStateException e) {
      JOptionPane.showMessageDialog(frame, e.getMessage());
    }
  }

  private void promptToStart(String msg) {
    String[] options = {"Play vs another player", "Play vs computer", "Quit"};
    int selection = JOptionPane.showOptionDialog(frame, msg, null, JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

    switch (selection) {
      case 0:
        model.startGameVs('P');
        break;
      case 1:
        model.startGameVs('C');
        break;
      case 2:
        System.exit(0);
        break;
      default:
        System.exit(0);
        break;
    }
  }

  @Override
  public void gameStarted() {
    clearPieces();
  }

  private void clearPieces() {
    for (int i = 0; i < NUM_ROWS; i++) {
      for (int j = 0; j < NUM_COLUMNS; j++) {
        piecesGrid[i][j].setBackground(Color.WHITE);
      }
    }
  }

  private void displayMove(Move move) {
    // since in the GUI, the top row has index 0
    // so we have to invert the row index
    int rowIdx = (NUM_ROWS - 1) - move.getRowIdx();
    int columnIdx = move.getColumnIdx();
    Player player = move.getPlayer();

    piecesGrid[rowIdx][columnIdx].setBackground(player.getColor());
  }

  @Override
  public void moveAdded(Move move, boolean computerTurn) {
    displayMove(move);

    // trigger computer move
    if (computerTurn) {
      int column = model.getNextMoveForComputer();
      if (column == -1) {
        throw new IllegalStateException("No move available for computer.");
      }
      addMove(column);
    }
  }

  @Override
  public void gameWonBy(Move move) {
    displayMove(move);
    promptToStart(move.getPlayer() + " win the game!");
  }

  @Override
  public void gameTied(Move move) {
    displayMove(move);
    promptToStart("It's a tie!");
  }
}
