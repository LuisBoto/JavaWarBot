package nicholasJohnstone.MappingApp;
import java.awt.*;
import java.util.*;  
import java.io.*;
/**
 * Contains a polygon and list of deeds, can update color to reflect current owner
 */
class Plot implements Serializable{	
	
	private static final long serialVersionUID = 1L;	
	private static final Color DEFAULT_COLOR=new Color(224,224,224);
	
	private static int currentYear=0;
	
	private Color color=DEFAULT_COLOR;
	private TreeSet<Deed> Deeds=new TreeSet<Deed>();
	private ArrayList<DoublePoint> pointList= new ArrayList<DoublePoint>();
	private Polygon polygon=new Polygon();

	public static void setCurrentYear(int year) {
		currentYear=year;
	}
	
	public void update() {
		color=DEFAULT_COLOR;
		for(Deed Deed: Deeds) {
			if(currentYear>=Deed.getDate()){
				color=Deed.getOwner().getColor();
			} else {
				return;
			}	
		} 
		return;				
	}

	public void rescale(double scale){
		polygon=new Polygon();
		for (DoublePoint point:pointList){
			point.rescale(scale);
			polygon.addPoint((int) point.getX(),(int) point.getY());
		}
	}
	
	public void addDeed(Deed x){
		Deeds.add(x);
	}	
		
	public void removeDeed(Deed x){
		Deeds.remove(x);
	}
	
	public void addPoint(int x,int y){
		polygon.addPoint(x,y);
		pointList.add(new DoublePoint(x,y));
	}
	
	public Color getColor() {
		return color;
	}


	public TreeSet<Deed> getDeeds(){
		return Deeds;
	}

	public void setDeeds(TreeSet<Deed> Deeds){
		this.Deeds=Deeds;
	}

	public Polygon getPolygon(){
		return polygon;
	}
		
}
