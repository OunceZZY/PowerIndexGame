import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InfoMenu extends JFrame implements ActionListener {
  JTextField strategyChoice, gridType, ppn; // positive power number

  JTextField gridHeight, gridWidth;
  JButton heightInc, heightDec, widthInc, widthDec;

  JButton start;

  Control ct;

  public InfoMenu(Control control) {
    ct = control;
    setLayout(new GridLayout(3, 0));// size of grid

    JPanel strategyPanel = new JPanel();
    strategyPanel.add(new JLabel("Strategy choice:"));
    strategyChoice = new JTextField("1", 20);
    strategyPanel.add(new JLabel("Grid type choice:"));
    gridType = new JTextField("1", 20);
    strategyPanel.add(new JLabel("Positive power number:"));
    ppn = new JTextField("0.66", 20);
    strategyPanel.add(strategyChoice);
    strategyPanel.add(gridType);
    strategyPanel.add(ppn);

    add(strategyPanel); //TODO: add strategy info to DiffusionStrategy

    JPanel adjustGridSize = new JPanel();
    gridHeight = new JTextField("4", 5);

    gridWidth = new JTextField("20", 5);

    heightInc = new JButton("^");
    heightDec = new JButton("v");
    widthInc = new JButton("^");
    widthDec = new JButton("v");
    heightInc.addActionListener(this);
    heightDec.addActionListener(this);
    widthInc.addActionListener(this);
    widthDec.addActionListener(this);
    adjustGridSize.add(new JLabel("Grid Height"));
    adjustGridSize.add(gridHeight);
    adjustGridSize.add(heightDec);
    adjustGridSize.add(heightInc);
    adjustGridSize.add(new JLabel("Grid Weight"));
    adjustGridSize.add(gridWidth);
    adjustGridSize.add(widthDec);
    adjustGridSize.add(widthInc);

    this.add(adjustGridSize);

    JPanel confirmPanel = new JPanel();
    start = new JButton("Confirm");
    start.addActionListener(this);
    confirmPanel.add(start);
    add(confirmPanel);

    setTitle("Power Index Game");
    setSize(800, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    int val;
    if (source == heightInc || source == heightDec) {
      gridHeight.setText(numberValidation(source, gridHeight.getText()));

    } else if (source == widthInc || source == widthDec) {
      gridWidth.setText(numberValidation(source, gridWidth.getText()));
    }

    if (source == start) {
      // take in information about the strategy and size
      String Strat = this.strategyChoice.getText();
      String grid = this.gridType.getText();
      String pospownum = this.ppn.getText();
      String high = this.gridHeight.getText();
      String wide = this.gridWidth.getText();

      int strat_num, grid_type_num, high_num, wide_num;
      double postivepowernumber;
      try {
        strat_num = Integer.parseInt(Strat);
        grid_type_num = Integer.parseInt(grid);
        postivepowernumber = Double.parseDouble(pospownum);
        if (postivepowernumber < 0 || postivepowernumber > 1) return;
        high_num = Integer.parseInt(high);
        wide_num = Integer.parseInt(wide);

      } catch (Exception exp) {
        return;
      }
      ct.initialization(strat_num, grid_type_num,postivepowernumber, high_num, wide_num);
      this.setVisible(false);
    }

  }

  /**
   * the return string of the field (restrict in number)
   * 
   * @param src get the action source button info
   * @param s the string in the field
   * @return
   */
  String numberValidation(Object src, String s) {
    int val;
    try {
      val = Integer.parseInt(s);
      if (src == heightInc || src == widthInc) val++;
      else val--;
    } catch (Exception e) {
      return s;
    }
    if (val < 1)
      val = 1;
    return "" + val;
  }
}
