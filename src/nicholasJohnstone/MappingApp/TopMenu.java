package nicholasJohnstone.MappingApp;
import javax.swing.*;
import java.awt.event.*;
/**
 *Displays file/edit menus and zoom in/out buttons
 */
@SuppressWarnings("serial")
public class TopMenu extends JMenuBar{
	
	public TopMenu(OuterMapPanel outerMapPanel,FileManager fileManager,DatePanel datePanel){
		buildFileMenu(fileManager);
		add(Box.createHorizontalGlue());
		buildZoomMenu(outerMapPanel);	
	}
	
	private void buildFileMenu(FileManager fileManager){
		JMenu fileMenu = new JMenu("File");
		add(fileMenu);
		JMenuItem newItem=new JMenuItem("New");
		fileMenu.add(newItem);
		newItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fileManager.newState();
			}
		});
		fileMenu.addSeparator();
		JMenuItem openItem=new JMenuItem("Open");
		fileMenu.add(openItem);
		openItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fileManager.openFrom();
			}
		});
		fileMenu.addSeparator();
		JMenuItem saveItem=new JMenuItem("Save");
		fileMenu.add(saveItem);
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fileManager.save();
			}
		});
		JMenuItem saveAsItem=new JMenuItem("Save As...");
		fileMenu.add(saveAsItem);
		saveAsItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fileManager.saveAs();
			}
		});
		fileMenu.addSeparator();
		JMenuItem imageIcon= new JMenuItem("Import Image");
		fileMenu.add(imageIcon);
		imageIcon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fileManager.importImage();
			}
		});
	}

	public void buildZoomMenu(OuterMapPanel outerMapPanel){		
		JButton zoomOutItem = new JButton("-");
		zoomOutItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				outerMapPanel.zoomOut();		
			}
		});
		add(zoomOutItem);		
		JButton zoomInItem = new JButton("+");
		zoomInItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				outerMapPanel.zoomIn();		
			}
		});
		add(zoomInItem);
	}
		
}
