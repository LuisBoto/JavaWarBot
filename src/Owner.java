import java.awt.*;
import java.util.*;
import java.io.*;
/**
  * Simple data object, linked to a OwnerManager that provides edit/delete functionality
 */
class Owner implements Comparable<Owner>,Serializable {

	private static final long serialVersionUID = 1L;	
	
	private String name="default name";
	private Color color;
	private ArrayList<Deed> registrationList=new ArrayList<Deed>();		

	private transient OwnerManager manager;

	public String toString() {
		return name;
	}

	public int compareTo(Owner o){
		return this.toString().compareTo(o.toString());
	}

	public Color getColor() {
		return color;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}
	
	public void setColor(Color color) {
		this.color=color;
	}
	
	public void setManager(OwnerManager manager){
		this.manager=manager;
	}
	
	public OwnerManager getManager(){
		if(manager==null) {
			manager=new OwnerManager(this);
		}
		return manager;
	}

	public ArrayList<Deed> getDeedList(){
		return registrationList;
	}

}




		


