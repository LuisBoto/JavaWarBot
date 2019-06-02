import javax.swing.*;
import java.awt.event.*;
/**
 *Displays file/edit menus and zoom in/out buttons
 */
@SuppressWarnings("serial")
public class TopMenu extends JMenuBar{
	
	public TopMenu(OuterMapPanel outerMapPanel,FileManager fileManager,DatePanel datePanel){
		buildFileMenu(fileManager);
		buildEditMenu(outerMapPanel,datePanel);
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
				fileManager.open();
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
		
	private void buildEditMenu(OuterMapPanel outerMapPanel,DatePanel datePanel){
		JMenu editMenu= new JMenu("Edit");
		add(editMenu);
		JMenuItem addFieldItem= new JMenuItem("Add New Field");
		editMenu.add(addFieldItem);
		addFieldItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				outerMapPanel.drawShape();		
			}
		});
		JMenuItem addOwnerItem = new JMenuItem("Add New Owner");
		editMenu.add(addOwnerItem);
		addOwnerItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				OwnerManager.makeNewOwner();		
			}
		});
		JMenuItem editDateIcon= new JMenuItem("Edit Date Range");
		editMenu.add(editDateIcon);
		editDateIcon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				datePanel.editYearRange();		
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
