import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame implements ActionListener {

  private JPanel[] gridRows; // see design sheet
  private JButton[][] gridVertices;

  private JPanel controlButtons;

  public MainWindow(int row, int col) {
    gridRows = new JPanel[row];
    gridVertices = new JButton[row][col];

    setLayout(new GridLayout(row + 1, 0));

    for (int i = 0; i < row; i++) {
      gridRows[i] = new JPanel();

      for (int j = 0; j < col; j++) {
        gridVertices[i][j] = new ColorButton() {
          {
           // setBackground(Color.red);
           // setForeground(Color.GRAY);
            setPreferredSize(new Dimension(10, 10)); 
          }
        };//new JButton(""+i+","+j);
         //setPreferredSize(new Dimension(5, 5));gridVertices[i][j].setPreferredSize(new Dimension(5, 5));
        //gridVertices[i][j].setBackground(Color.GRAY);
        gridRows[i].add(gridVertices[i][j]);
      }

      add(gridRows[i]);
    }

    controlButtons = new JPanel();
    controlButtons.add(new JButton("ab"));
    controlButtons.add(new JButton("ab"));
    controlButtons.add(new JButton("ab"));
    add(controlButtons);

    setTitle("Power Index Game");
    setSize(col*12+300, row*12+500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub

  }

}
