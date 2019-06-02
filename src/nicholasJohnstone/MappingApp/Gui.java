package nicholasJohnstone.MappingApp;
import javax.swing.*;
import java.awt.*;

/**
 * This GUI app allows the user to colour code a map by ownership of individual plots at different dates. 
 * @author Nicholas Johnstone
 */
public class Gui{

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.go();
	}
/**
 * Sets up the objects required for the program and displays them on the screen
 */
	private void go() {
		MainFrame mainFrame=new MainFrame();
		JPanel innerPanel = new JPanel();		
		DatePanel datePanel= new DatePanel();
		MapPanel mapPanel = new MapPanel();
		OuterMapPanel outerMapPanel = new OuterMapPanel(mapPanel);
		FileManager fileManager=new FileManager(datePanel,mapPanel);
		TopMenu topMenu = new TopMenu(outerMapPanel,fileManager,datePanel);
		
		mainFrame.getContentPane().add(BorderLayout.CENTER,innerPanel);
		innerPanel.setLayout(new BorderLayout());
		innerPanel.add(BorderLayout.CENTER,outerMapPanel);
		innerPanel.add(BorderLayout.NORTH,topMenu);
		innerPanel.add(BorderLayout.SOUTH,datePanel);
		
		FileManager.setParentFrame(mainFrame);
		fileManager.openDefault();
		OwnerManager.setDialogParent(mainFrame);
		DeedManager.setDialogParent(mainFrame);
		DateRangeEditor.setDialogParent(mainFrame);
		mainFrame.addWindowListener(fileManager);
		mainFrame.setVisible(true);
	}
}
