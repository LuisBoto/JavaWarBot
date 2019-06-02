import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
/**
 * Displays the current year in a bordered box
 */
@SuppressWarnings("serial")
public class DateLabel extends JLabel{
	
	public DateLabel(int initYear){
	super(String.valueOf(initYear));	
	Font font = getFont();
	setFont(new Font(font.getName(), Font.PLAIN, 28));			
	Border raisedbevel = BorderFactory.createRaisedBevelBorder();	
	Border loweredbevel = BorderFactory.createLoweredBevelBorder();
	Border border = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
  	setBorder(border);
	setVerticalAlignment(JLabel.CENTER);
	}

	public void setYear(int year){
		setText(String.valueOf(year));
	}
}
