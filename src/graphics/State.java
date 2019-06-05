package graphics;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import politicalLogic.Warfield;
/**
 * Contains infomation required to save file
 */
public class State implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Plot> fields;
	private Warfield warfield;
	private File currentFile;
	private File imageFile;
	private DoublePoint imageSize;
	
	public State(File documentF, File imageF, ArrayList<Plot> fields, DoublePoint imgSize, Warfield w){
		this.warfield = w;
		this.fields=fields;
		currentFile=documentF;
		imageFile=imageF;
		imageSize=imgSize;
	}
	
	public Warfield getWarfield() {
		return this.warfield;
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
}
