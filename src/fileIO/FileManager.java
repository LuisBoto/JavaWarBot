package fileIO;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import nicholasJohnstone.MappingApp.DrawPanel;
import nicholasJohnstone.MappingApp.ImageListener;
import nicholasJohnstone.MappingApp.MapPanel;
import nicholasJohnstone.MappingApp.Plot;
import nicholasJohnstone.MappingApp.PlotListener;
import nicholasJohnstone.MappingApp.State;

import java.util.*;
import java.awt.image.*;
import javax.imageio.*;
/**
 * Provides saving facilities, when window is closed offers to save unsaved progress
 */
public class FileManager extends WindowAdapter implements PlotListener {

	private static final String EXTENSION_STRING = "afg";
	private static ArrayList<ImageListener> imageListenerList=new ArrayList<ImageListener>();
	private static JFrame parent;
	
	private File currentFile = new File("resources/defaultState.afg");
	private File imageFile = new File("resources/Map.jpg");
	private BufferedImage image;
	private JFileChooser fileChooser;
	private JFileChooser imageChooser;
	private boolean saved=true;
	private MapPanel mapPanel;
	
	public static void setParentFrame(JFrame newParent){
		parent=newParent;
	}

	public static void addImageListener(ImageListener x){
		imageListenerList.add(x);
	}
	
	public FileManager(MapPanel mapPanel){
		this.mapPanel=mapPanel;
		DrawPanel.addPlotListener(this);
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(EXTENSION_STRING+" files",EXTENSION_STRING);		
		fileChooser.setFileFilter(filter);
		imageChooser=new JFileChooser();
		filter = new FileNameExtensionFilter("Image file","jpg","png","bmp","wbmp","gif");		
		imageChooser.setFileFilter(filter);
		imageChanged();
		openDefault();
	}

	public boolean newState(){	
		if(confirmClose()){					
			currentFile=null;		

			mapPanel.setPlotList(new ArrayList<Plot>());

			parent.setTitle("untitled");
			saved=true;	
			return true;
			}
		return false;
	}
	
	public void openDefault() {
		State state=null;
		try {
			FileInputStream fileIn = new FileInputStream(currentFile);
			ObjectInputStream in = new ObjectInputStream(fileIn);
 				state = (State) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i) {
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c) {
			System.out.println("class not found");
			c.printStackTrace();
			return;
		}

		mapPanel.setPlotList(state.getPlots());
		imageFile=state.getImageFile();
		imageChanged();
		mapPanel.setImageSize(state.getImageSize());

		parent.setTitle(currentFile.getName());
		saved=true;
	}
	
	public void openFrom(){
		if(confirmClose()){
			if(fileChooser.showOpenDialog(parent)==JFileChooser.APPROVE_OPTION) {
				State state=null;
				currentFile=fileChooser.getSelectedFile();
				try {
					FileInputStream fileIn = new FileInputStream(currentFile);
					ObjectInputStream in = new ObjectInputStream(fileIn);
         				state = (State) in.readObject();
					in.close();
					fileIn.close();
				}catch(IOException i) {
					i.printStackTrace();
					return;
				}catch(ClassNotFoundException c) {
					System.out.println("class not found");
					c.printStackTrace();
					return;
				}

				mapPanel.setPlotList(state.getPlots());
				imageFile=state.getImageFile();
				imageChanged();
				mapPanel.setImageSize(state.getImageSize());

				parent.setTitle(currentFile.getName());
				saved=true;
			}
		}
	}
	public void save(){
		if(currentFile==null){
			saveAs();
			return;
		}
		try {
			FileOutputStream fileOut = new FileOutputStream(currentFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(new State(this,mapPanel));
			out.close();
			fileOut.close();
			saved=true;
		}catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	public void saveAs(){
		if(fileChooser.showSaveDialog(parent)==JFileChooser.APPROVE_OPTION){
			String filename = fileChooser.getSelectedFile().toString();
			if( !filename.endsWith("."+EXTENSION_STRING)){
				filename=filename+"."+EXTENSION_STRING;
			}
			currentFile=new File(filename);
			parent.setTitle(currentFile.getName());
			save();
		}
	}
	
	public void importImage(){
		if(imageChooser.showOpenDialog(parent)==JFileChooser.APPROVE_OPTION){
			imageFile=imageChooser.getSelectedFile();
			imageChanged();
		}
	}
	public void loadImage() {
		try {
			image=ImageIO.read(imageFile);
		} catch(Exception e) { 
			System.out.println("Couldn't load image");
		}
	}
	
	public void windowClosing(WindowEvent e){
		if(confirmClose()){
			parent.dispose();
		}
	}
	
	public boolean confirmClose(){
		if(!saved){
			String[] options={"Save","Don't Save","Cancel"};
			int result=JOptionPane.showOptionDialog(parent,"Save changes before closing?","Save notification",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null,options,null);
			switch(result) {
				case JOptionPane.CANCEL_OPTION: return false;
				case JOptionPane.YES_OPTION:	save();
				case JOptionPane.NO_OPTION:	return true;
			}
			return false;
		}
		return true;
	}
	
	public File getFile(){
		return currentFile;
	}

	public File getImageFile(){
		return imageFile;
	}
	
	private void imageChanged() {
		loadImage();
		for(ImageListener x:imageListenerList){
			x.imageChanged(image);
		}	
	}
	
	public void ownerChanged(){
		saved=false;
	}
		
	public void plotChanged(){
		saved=false;
	}

	public void yearChanged(){
		saved=false;
	}
}
