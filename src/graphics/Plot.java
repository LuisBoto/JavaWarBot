package graphics;
import java.awt.*;
import java.util.*;  
import java.io.*;
/**
 * Contains a polygon 
 */
public class Plot implements Serializable{	
	
	private static final long serialVersionUID = 1L;	
	private static final Color DEFAULT_COLOR=new Color(224,224,224);
	
	private Color color=DEFAULT_COLOR;
	private ArrayList<DoublePoint> pointList= new ArrayList<DoublePoint>();
	private Polygon polygon=new Polygon();
	
	public void update(String name) {
		if (name != null) {
			//We'll calculate a color from the given name
			int r = name.hashCode()%254;
			r=Math.abs(r);
			int g = name.substring(1, name.length()).hashCode()%254;
			g=Math.abs(g);
			int b = name.substring(0, name.length()-1).hashCode()%254;
			b=Math.abs(b);
			color=new Color(r, g, b);	
		} else {
			color = DEFAULT_COLOR;
		}
	}

	public void rescale(double scale){
		polygon=new Polygon();
		for (DoublePoint point:pointList){
			point.rescale(scale);
			polygon.addPoint((int) point.getX(),(int) point.getY());
		}
	}
	
	public void addPoint(int x,int y){
		polygon.addPoint(x,y);
		pointList.add(new DoublePoint(x,y));
	}
	
	public Color getColor() {
		return color;
	}

	public Polygon getPolygon(){
		return polygon;
	}
		
}
