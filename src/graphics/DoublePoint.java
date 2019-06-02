package graphics;
import java.io.*;
/**
 * Scalable 2d point with double precision
 */
public class DoublePoint implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private double x;
	private double y;

	public DoublePoint(double x, double y) {
		this.x=x;
		this.y=y;
	}

	public void rescale(double scale) {
		x*=scale;
		y*=scale;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
