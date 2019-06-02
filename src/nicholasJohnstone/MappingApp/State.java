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
	
	public State(File documentF, File imageF, ArrayList<Plot> fields, DoublePoint imgSize){
		this.fields=fields;
		currentFile=documentF;
		imageFile=imageF;
		imageSize=imgSize;
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
