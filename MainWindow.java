import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame implements ActionListener {

  final static Color Collab = new Color(32, 191, 241);
  final static Color Defect = new Color(252, 42, 28);

  int BUTTONHEIGHT = 30;
  int BUTTONWIDTH = 60;

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

  public void setTheBoard(Grid g) {
    for (int i = 0; i < num_row; i++) {
      for (int j = 0; j < num_col; j++) {
        //gridVertices[i][j].setSize(new Dimension(100, 100));
        gridVertices[i][j].setText("" + g.grid[i][j].powerIndex);
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
            setBackground(Collab);
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
    controls[1] = new JButton("");
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
   * end: end this window and back to menu
   * 
   * find button location
   * change side
   * 
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    Object source = e.getSource();
    if (source == controls[0]) {
      ct.initialTheGame();
      System.out.println("HERE");
      return;
    }

    if (source == end) {
      ct.reinit();
      return;
    }

    int row = 0, col = 0;
    boolean found = false;

    // TODO change side simotenously on grides and here
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
