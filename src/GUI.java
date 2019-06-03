import javax.swing.*;
import fileIO.FileManager;
import graphics.*;
import politicalLogic.Warfield;

import java.awt.*;

public class GUI{

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.go();
	}
	
	private void go() {
		Warfield w = new Warfield();
		MainFrame mainFrame=new MainFrame();
		JPanel innerPanel = new JPanel();		
		HistoryPanel hPanel = new HistoryPanel();
		MapPanel mapPanel = new MapPanel(w);
		OuterMapPanel outerMapPanel = new OuterMapPanel(mapPanel);		
		FileManager.setParentFrame(mainFrame);
		FileManager fileManager=new FileManager(mapPanel);
		ButtonPanel bPanel= new ButtonPanel(mapPanel.getWarfield(), hPanel, mapPanel);
		fileManager.setButtonPanel(bPanel);
		fileManager.setHistoryPanel(hPanel);
		TopMenu topMenu = new TopMenu(outerMapPanel,fileManager,bPanel);
		
		mainFrame.getContentPane().add(BorderLayout.CENTER,innerPanel);
		innerPanel.setLayout(new BorderLayout());
		innerPanel.add(BorderLayout.CENTER,outerMapPanel);
		innerPanel.add(BorderLayout.NORTH,topMenu);
		innerPanel.add(BorderLayout.SOUTH,bPanel);
		innerPanel.add(BorderLayout.EAST,hPanel);
		
		mainFrame.addWindowListener(fileManager);
		mainFrame.setVisible(true);
	}
}
