package graphics;


import javax.swing.*;
import fileIO.FileManager;
import graphics.*;

import java.awt.*;

public class Gui{

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.go();
	}
	
	private void go() {
		MainFrame mainFrame=new MainFrame();
		JPanel innerPanel = new JPanel();		
		ButtonPanel datePanel= new ButtonPanel();
		MapPanel mapPanel = new MapPanel();
		OuterMapPanel outerMapPanel = new OuterMapPanel(mapPanel);		
		FileManager.setParentFrame(mainFrame);
		FileManager fileManager=new FileManager(mapPanel);
		TopMenu topMenu = new TopMenu(outerMapPanel,fileManager,datePanel);
		
		mainFrame.getContentPane().add(BorderLayout.CENTER,innerPanel);
		innerPanel.setLayout(new BorderLayout());
		innerPanel.add(BorderLayout.CENTER,outerMapPanel);
		innerPanel.add(BorderLayout.NORTH,topMenu);
		innerPanel.add(BorderLayout.SOUTH,datePanel);
		
		mainFrame.addWindowListener(fileManager);
		mainFrame.setVisible(true);
	}
}
