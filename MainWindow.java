import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameWindow extends JFrame implements ActionListener {

  final static Color Collab = new Color(32, 191, 241);
  final static Color Defect = new Color(252, 42, 28);

  final int BUTTONHEIGHT = 30;
  final int BUTTONWIDTH = 60;

  private JPanel[] gridRows; // see design sheet
  private JButton[][] gridVertices;
  private int num_row, num_col;

  private JPanel controlButtons;
  private ArrayList<JButton> controls; // 0: start 1: stop 2: next

  private JButton shiftLeft, shiftRight;

  private JButton end;

  private JButton calculateCycle;
  private JLabel cycleInfo;

  Control ct;

  /**
   * 
   * @param original the original to find the change
   * @param g the current grid should be
   */
  public void setTheBoard(Grid original, Grid g) {
    DecimalFormat formatter = new DecimalFormat("#0.000");
    for (int i = 0; i < num_row; i++) {
      for (int j = 0; j < num_col; j++) {
        if (original == null
            || !original.getVertices()[i][j].equals(g.getVertices()[i][j])) {
          gridVertices[i][j].setText("" + formatter.format(g.grid[i][j].powerIndex));
          setButtonSide(i, j, g.getVertices()[i][j].side);
        }
      }
    }
  }

  public GameWindow(int row, int col) {
    gridRows = new JPanel[row];
    gridVertices = new ColorButton[row][col];
    num_row = row;
    num_col = col;
    setLayout(new GridLayout(row + 1, 0)); // number of rows + control panel

    for (int i = 0; i < row; i++) {
      gridRows[i] = new JPanel();

      for (int j = 0; j < col; j++) {
        gridVertices[i][j] = new ColorButton() {
          {
            setPreferredSize(new Dimension(BUTTONWIDTH, BUTTONHEIGHT));
            setText("");
          }
        };
        gridVertices[i][j].addActionListener(this);
        gridRows[i].add(gridVertices[i][j]);
      }
      add(gridRows[i]);
    }
    //-----------------------------------------------------------------------------------------------------------------------------
    controlButtons = new JPanel();
    controls = new ArrayList<JButton>();
    controls.add(new JButton("Start"));
    controls.add(new JButton("Prev"));
    controls.add(new JButton("Next"));

    end = new JButton("End");
    controls.add(end);
    shiftLeft = new JButton("<");
    shiftRight = new JButton(">");
    controls.add(shiftLeft);
    controls.add(shiftRight);

    calculateCycle = new JButton("Calculate cycle");
    cycleInfo = new JLabel();
    cycleInfo.setVisible(false);
    controls.add(calculateCycle);

    for (JButton a : controls) {
      controlButtons.add(a);
      a.addActionListener(this);
    }
    controlButtons.add(cycleInfo);

    add(controlButtons);
    //-----------------------------------------------------------------------------------------------------------------------------------------
    setTitle("Power Index Game");
    setSize(col * BUTTONWIDTH + 200, row * BUTTONHEIGHT + 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  /**
   * action finding (in order)
   * start:control[0]: undefined 4/25
   * prev: last state of the state
   * next: to update from this state
   * end: end this window and back to menu
   * shiftToLeft: as the name
   * shiftToRight: as above
   * calculate the cycle
   * 
   * find button location
   * change side
   * 
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source == controls.get(0)) {
      ct.initialTheGame();
      System.out.println("HERE");
      return;
    }
    if (source == end) {
      ct.reinit();
      this.dispose();
      return;
    }
    if (source == controls.get(1)) {
      ct.showLastState();
      return;
    }
    if (source == controls.get(2)) {
      ct.showNextState();
      return;
    }
    if (source == this.shiftLeft) {
      ct.shiftTo(-1);
      return;
    }
    if (source == this.shiftRight) {
      ct.shiftTo(1);
      return;
    }
    if (source == this.calculateCycle) {
      ct.toCalcTheCycle();
      return;
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    int row = 0, col = 0;
    boolean found = false;

    for (int i = 0; !found && i < num_row; i++) {
      for (int j = 0; !found && j < num_col; j++) {
        if (source == gridVertices[i][j]) {
          row = i;
          col = j;
          found = true;
        }
      }
    }
    if (found) ct.broadcastchangeSide(row, col);
  }

  /**
   * 
   * @param row
   * @param col
   * @param s
   */
  public void setButtonText(int row, int col, String s) {
    gridVertices[row][col].setText(s);
  }

  /**
   * 
   * @param row row number
   * @param col column number
   * @param Type the side of vertex
   */
  public void setButtonSide(int row, int col, char Type) {
    gridVertices[row][col].setBackground((Type == 'D') ? Defect : Collab);
  }

  /**
   * Method changes the color (side) of the button
   * 
   * @param row row number of the button
   * @param col column number of the button
   */
  public void changeButtonSide(int row, int col) {
    gridVertices[row][col].setBackground((gridVertices[row][col].getBackground()
        .equals(Collab)) ? Defect : Collab);
  }

  public void setCycleMessage(String stringMessage) {
    cycleInfo.setText(stringMessage);
    cycleInfo.setVisible(true);
  }

}
