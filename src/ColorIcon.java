import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
/**
 * Plain colour Icon used to display the colour of each owner
 */
public class ColorIcon implements Icon {
  
  private Color color;
  
  public ColorIcon(Color color) {
    this.color = color;
  }

  public void paintIcon(Component c, Graphics g, int x, int y) {
    Color old_color = g.getColor();
    g.setColor(color);
    g.fillRect(x,y,20,20);
    g.setColor(old_color);
  }

  public int getIconWidth() {
    return 20;
  }

  public int getIconHeight() {
    return 20;
  }
  
}

