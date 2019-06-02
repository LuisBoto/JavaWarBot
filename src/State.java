import java.util.*;
import java.io.*;
/**
 * Contains infomation required to save file
 */
public class State implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private TreeSet<Owner> owners;
	private ArrayList<Plot> fields;
	private File currentFile;
	private File imageFile;
	private DoublePoint imageSize;
	private int minYear;
	private int maxYear;

	public State(FileManager fileManager,DatePanel datePanel,MapPanel mapPanel){
		owners=OwnerManager.getOwners();
		fields=mapPanel.getPlots();
		currentFile=fileManager.getFile();
		imageFile=fileManager.getImageFile();
		imageSize=mapPanel.getImageSize();
		minYear=datePanel.getYearMin();
		maxYear=datePanel.getYearMax();
	}

	public TreeSet<Owner> getOwners(){
		return owners;
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
