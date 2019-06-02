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
			byte[] chain = name.getBytes();
			int r = chain[0];
			int g = chain[chain.length-1];
			int b = chain[chain.length/2];
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
