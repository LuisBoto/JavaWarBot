package fileIO;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import graphics.ButtonPanel;
import graphics.DoublePoint;
import graphics.DrawPanel;
import graphics.HistoryPanel;
import graphics.ImageListener;
import graphics.MapPanel;
import graphics.Plot;
import graphics.PlotListener;
import graphics.State;
import politicalLogic.Locality;
import politicalLogic.Warfield;

/**
 * Provides saving facilities, when window is closed offers to save unsaved progress
 */
public class FileManager extends WindowAdapter implements PlotListener {

	private static final String EXTENSION_STRING = "txt";
	private static ArrayList<ImageListener> imageListenerList=new ArrayList<ImageListener>();
	private static JFrame parent;
	
	private File currentFile = new File("resources/defaultState.txt");
	private File imageFile = new File("resources/Map.jpg");
	private BufferedImage image;
	private JFileChooser fileChooser;
	private JFileChooser imageChooser;
	private boolean saved=true;
	private MapPanel mapPanel;
	private ButtonPanel buttonPanel;
	private HistoryPanel hPanel;
	
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
	
	public void setButtonPanel(ButtonPanel bp) {
		this.buttonPanel = bp;
	}
	
	public void setHistoryPanel(HistoryPanel hp) {
		this.hPanel = hp;
	}

	public boolean newState(){	
		if(confirmClose()){					
			currentFile=null;		

			mapPanel.setPlotList(new ArrayList<Plot>());
			Warfield w = new Warfield();
			mapPanel.setWarfield(w);
			buttonPanel.newState(w, hPanel, mapPanel);
			parent.setTitle("untitled");
			saved=true;	
			return true;
			}
		return false;
	}
	
	public void openDefault() {
		currentFile = new File("resources/defaultState.txt");
		load();
	}
	
	public void openFrom(){
		if(confirmClose()){
			if(fileChooser.showOpenDialog(parent)==JFileChooser.APPROVE_OPTION) {
				currentFile=fileChooser.getSelectedFile();
				load();
			}
		}
	}
	
	private State parseSavedFile(String content) {
		//This method will parse the parametered saved file contents
		File documentFile;
		File imageFile;
		DoublePoint imageSize;
		ArrayList<Plot> fields = new ArrayList<Plot>();
		Warfield warfield = new Warfield();
		String[] parts = content.split("\n");
		documentFile = new File(parts[0].split("-")[1]);
		imageFile = new File(parts[1].split("-")[1]);
		double xsize = Double.parseDouble(parts[2].split("-")[1].split(" ")[0]);
		double ysize = Double.parseDouble(parts[2].split("-")[1].split(" ")[1]);
		imageSize = new DoublePoint(xsize, ysize);
		//Parsing individual plots and corresponding localities
		for (int i=0; i<parts.length; i++) {
			if (parts[i].split("-")[0].equals("Plot")) {
				int j = i+1;
				Plot plot = new Plot();
				while (!parts[j].split("-")[0].equals("Plot") && !parts[j].equals("EOF")) {
					int x = Integer.parseInt(parts[j].split(" ")[1]);
					int y = Integer.parseInt(parts[j].split(" ")[3]);
					plot.addPoint(x, y);
					j++;
				}
				fields.add(plot);
				
				if(parts[i].split("-").length<=1)
					continue;
				String data = parts[i].split("-")[1];
				Locality l = new Locality();
				l.setName(data.split("_")[0].split(":")[1]);
				l.setGraphic(plot);
				l.setGovernment(data.split("_")[1].split(":")[1]);
				warfield.addLocality(l);
			}
		}
		//Parsing frontiers
		for (int i=0; i<parts.length; i++) {
			if (parts[i].split("-")[0].equals("Plot")) {
				if(parts[i].split("-").length<=2)
					continue;
				String data = parts[i].split("-")[2];
				String name =  parts[i].split("-")[1].split("_")[0].split(":")[1];
				Locality l = warfield.getLocalityFromName(name);
				String[] fronts = data.split("_");
				for (String f : fronts) {
					l.addFrontier(warfield.getLocalityFromName(f));
				}
			}
		}
		return new State(documentFile, imageFile, fields, imageSize, warfield);
	}
	
	public void loadState(State state) {
		mapPanel.setPlotList(state.getPlots());
		mapPanel.setWarfield(state.getWarfield());
		imageFile=state.getImageFile();
		imageChanged();
		mapPanel.setImageSize(state.getImageSize());
		parent.setTitle(currentFile.getName());
		saved=true;
	}
	
	private void load() {
		State state=null;
		try {
			FileReader fr = new FileReader(currentFile);
			BufferedReader bf = new BufferedReader(fr);
			StringBuilder st = new StringBuilder();
			String aux = bf.readLine();
			while(aux != null) {
				st.append(aux+"\n");		
				aux = bf.readLine();
			}
			bf.close();
			fr.close();

			state = parseSavedFile(st.toString());
		}catch(IOException i) {
			System.out.println("Error loading file");
		}
		
		loadState(state);
	}
	
	public void save(){
		if(currentFile==null){
			saveAs();
			return;
		}
		try {
			//We'll parse the Plot list into a .txt file
			FileWriter fileOut = new FileWriter(currentFile);
			BufferedWriter out = new BufferedWriter(fileOut);
			State s = new State(this.currentFile, this.imageFile, mapPanel.getPlots(), mapPanel.getImageSize(), mapPanel.getWarfield());
			out.write("document file-"+this.currentFile.getPath()+"\n");
			out.write("image file-"+this.imageFile.getPath()+"\n");
			out.write("imageSize-"+mapPanel.getImageSize().getX()+" "+mapPanel.getImageSize().getY()+"\n");
			for (Plot p : s.getPlots()) {
				Locality l = s.getWarfield().getLocalityFromPlot(p);
				String title = "Plot-";
				if (l!=null) {
					title+="name:"+l.getName()+"_";
					title+="government:"+l.getGovernment()+"-";
					for (int k=0; k<l.getFrontiers().size(); k++) {
						title+=l.getFrontiers().get(k).getName()+"_";
					}
				} 
				out.write(title+"\n");				
				for (int i=0; i<p.getPolygon().npoints; i++) {
					out.write("x "+p.getPolygon().xpoints[i]+" y "+p.getPolygon().ypoints[i]+"\n");
				}
			}
			out.write("EOF");
			out.close();
			fileOut.close();
			saved=true;
		}catch(IOException i) {
			System.out.println("Error saving file");
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
	
	public void plotChanged(){
		saved=false;
	}
	
	public boolean isSaved() {
		return this.saved;
	}

	
}

