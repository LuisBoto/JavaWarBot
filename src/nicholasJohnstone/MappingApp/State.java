package nicholasJohnstone.MappingApp;
import java.util.*;

import fileIO.FileManager;

import java.io.*;
/**
 * Contains infomation required to save file
 */
public class State implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Plot> fields;
	private File currentFile;
	private File imageFile;
	private DoublePoint imageSize;
	private int minYear;
	private int maxYear;

	public State(FileManager fileManager,MapPanel mapPanel){
		fields=mapPanel.getPlots();
		currentFile=fileManager.getFile();
		imageFile=fileManager.getImageFile();
		imageSize=mapPanel.getImageSize();
	}
	
	public ArrayList<Plot> getPlots(){
		return fields;
	}

	public File getFile(){
		return currentFile;
	}

	public File getImageFile(){
		return imageFile;
	}
	public DoublePoint getImageSize(){
		return imageSize;
	}

	public int getMinYear(){
		return minYear;
	}

	public int getMaxYear(){
		return maxYear;
	}
}
