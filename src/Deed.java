import java.io.*;
/**
 * Simple data object, linked to a DeedManager that provides edit/delete functionality
 */
class Deed implements Comparable<Deed>,Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Plot plot;
	private Owner owner;
	private int date=0;					//must have default value for use with TreeSet
	
	private transient DeedManager manager;
	
	public int compareTo(Deed o){
		if(date-o.date==0){
			return(owner.toString().compareTo(o.owner.toString()));
		}
		return date-o.date;
	}

	public String toString(){
		return Integer.toString(date)+":"+owner.toString();
	}
	
	public Owner getOwner() {								
		return owner;
	}

	public void setOwner(Owner owner){
		this.owner=owner;
	}

	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot=plot;
	}

	public int getDate(){
		return date;
	}

	public void setDate(int date) {
		this.date=date;
	}

	public DeedManager getManager(){
		if(manager==null) {
			manager=new DeedManager(this);
		}
		return manager;
	}

	public void setManager(DeedManager manager){
		this.manager=manager;
	}
}

