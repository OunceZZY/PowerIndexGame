import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;

//import javax.swing.*;
//import java.awt.*;

public class ColorButton extends JButton {
  JLabel label = new JLabel();
  private Color bg;

  public ColorButton() {
    label.setHorizontalTextPosition(JLabel.CENTER);
    label.setOpaque(true);

    setLayout(new BorderLayout());
    add(label, BorderLayout.CENTER);
  }

  public ColorButton(String text) {
    super(text);

    label.setHorizontalTextPosition(JLabel.CENTER);
    label.setText(text);
    label.setOpaque(true);

    setLayout(new BorderLayout());
    add(label, BorderLayout.CENTER);
  }

  @Override
  public void setText(String text) {
    label.setFont(new Font(null, 0, 12));
    label.setHorizontalTextPosition(JLabel.CENTER);
    label.setText(text);
    label.setOpaque(true);
  }

  @Override
  public void setBackground(Color bg) {
    if (!UIManager.getSystemLookAndFeelClassName().endsWith("WindowsLookAndFeel")) {
      super.setBackground(bg);
    }

    this.bg = bg;
    if (label != null) {
      label.setBackground(bg);
    }
  }

  @Override
  public void setForeground(Color fg) {
    super.setForeground(fg);
    if (label != null) {
      label.setForeground(fg);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Rectangle rectangle = getBounds();
    rectangle.x = 3;
    rectangle.y = 3;
    rectangle.width -= rectangle.x * 2;
    rectangle.height -= rectangle.y * 2;

    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(bg);
    g2d.fill(rectangle);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Vertex)) return false;
    Vertex obj = (Vertex) o;
    /*if (obj.getPowerIndex() == Double.parseDouble(this.getText())) {
      char thisside;
      if (this.bg.equals(GameWindow.Collab)) 
        thisside = 'C';
      else 
        thisside = 'D';
      if (obj.getSide() == thisside) return true;
    }
    return false;*/
    return ((this.getText().equals("") ||
        obj.getPowerIndex() == Double.parseDouble(this.getText()))
        && (obj.getSide() == ((this.bg.equals(GameWindow.Collab)) ? 'C' : 'D')))
            ? true : false;
  }
}