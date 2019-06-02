package graphics;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	private static final String DEFAULT_TITLE="untitled";
	private static final int DEFAULT_WIDTH=1000;
	private static final int DEFAULT_HEIGHT=800;
	
	public MainFrame() {
	setTitle(DEFAULT_TITLE);
	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	//file manager handles closing
	this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);			
	}
}
