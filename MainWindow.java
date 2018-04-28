import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame implements ActionListener {

  final static Color Collab = new Color(32, 191, 241);
  final static Color Defect = new Color(252, 42, 28);

  final int BUTTONHEIGHT = 30;
  final int BUTTONWIDTH = 60;

  private JPanel[] gridRows; // see design sheet
  private JButton[][] gridVertices;
  private int num_row, num_col;

  public JButton[][] getGridVertices() {
    return gridVertices;
  }

  private JPanel controlButtons;
  private JButton[] controls; // 0: start 1: stop 2: next

  private JButton end;

  Control ct;

  /**
   * this method take in a grid and put the information of the vertex to the corresponding JButton
   * 
   * @param g the current grid should be
   */
  public void setTheBoard(Grid original, Grid g) {
    DecimalFormat formatter = new DecimalFormat("#0.000");
    for (int i = 0; i < num_row; i++) {
      for (int j = 0; j < num_col; j++) {
        if (original == null || !original.getVertices()[i][j].equals(g
            .getVertices()[i][j])) {
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
            //setBackground(Collab);
            // setForeground(Color.GRAY);
            setPreferredSize(new Dimension(BUTTONWIDTH, BUTTONHEIGHT));
          }
        };
        gridVertices[i][j].setText("");//new JButton(""+i+","+j);
        //setPreferredSize(new Dimension(5, 5));gridVertices[i][j].setPreferredSize(new Dimension(5, 5));
        //gridVertices[i][j].setBackground(Color.GRAY);
        gridVertices[i][j].addActionListener(this);
        gridRows[i].add(gridVertices[i][j]);
      }

      add(gridRows[i]);
    }

    controlButtons = new JPanel();
    controls = new JButton[3];
    controls[0] = new JButton("Start");
    controls[1] = new JButton("Prev");
    controls[2] = new JButton("Next");
    for (JButton a : controls) {
      controlButtons.add(a);
      a.addActionListener(this);
    }
    end = new JButton("End");
    end.addActionListener(this);
    controlButtons.add(end);

    add(controlButtons);

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
   * 
   * find button location
   * change side
   * 
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source == controls[0]) {
      ct.initialTheGame();
      System.out.println("HERE");
      return;
    }
    if (source == end) {
      ct.reinit();
      this.dispose();
      return;
    }
    if (source == this.controls[1]) {
      ct.lastStep();
      return;
    }
    if (source == this.controls[2]) {
      ct.toNextStep();
      return;
    }

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

    gridVertices[row][col].setBackground((gridVertices[row][col].getBackground().equals(
        Collab)) ? Defect : Collab);
  }

}
