import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * Allows user to draw a polygon on the screen, right click or double click finishes selection
 */
@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener{

	private static ArrayList<PlotListener> plotListenerList=new ArrayList<PlotListener>();	
	
	private Plot newPlot=new Plot();
	private MapPanel mapPanel;
	private JPanel cardPanel;
	private int n=0;
	private MouseEvent lastClick;
	private boolean isDrawing=false;
	private ArrayList<Point> points=new ArrayList<Point>();
	private Point extraPoint;

	public static void addPlotListener(PlotListener x){
		plotListenerList.add(x);
	}
	
	public DrawPanel(MapPanel mapPanel){
		this.mapPanel=mapPanel;
		addMouseListener(this);
		addMouseMotionListener(this);
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));	
	}

	public void setParentCardLayout(JPanel cardPanel){
		this.cardPanel=cardPanel;
	}

	public void paintComponent(Graphics g) {
		mapPanel.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
   		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		n=points.size();
		if(n>1){
			for(int i=0;i<n-1;i++){
				g2.drawLine((int) points.get(i).getX(),(int) points.get(i).getY(),(int) points.get(i+1).getX(),(int) points.get(i+1).getY());
			}
		}
		if(n>0 && extraPoint!=null){
			g2.drawLine((int) points.get(n-1).getX(),(int) points.get(n-1).getY(),(int) extraPoint.getX(),(int) extraPoint.getY());
		}
			
	}
	
	public void mousePressed(MouseEvent e){
		lastClick=e;
		singleClick(lastClick);
		if(e.getButton()==MouseEvent.BUTTON1){
			singleClick(lastClick);
			checkDoubleClick();
		} else if (e.getButton()==MouseEvent.BUTTON3){
			doubleClick();
		}
	}
	public void mouseMoved(MouseEvent e){	
		if(isDrawing){
			extraPoint=new Point(e.getX(),e.getY());
			repaint();
		}
	}
	
	public void singleClick(MouseEvent e){
		isDrawing=true;
		newPlot.addPoint(e.getX(),e.getY());
		points.add(new Point(e.getX(),e.getY()));
		repaint();
	}
	
	public void doubleClick(){
		if(n>2){
			mapPanel.addPlot(newPlot);
			plotChanged();
		}
		exit();
	}
	
	public void checkDoubleClick() {
		if(lastClick.getClickCount()==2) {
			doubleClick();
		}
	}

	public void exit(){
		newPlot=new Plot();
		points.clear();
		n=0;
		extraPoint=null;
		isDrawing=false;
		CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
		cardLayout.show(cardPanel, OuterMapPanel.MAPPANEL_LABEL);
	}
	
	public void plotChanged() {						
		for(PlotListener x:plotListenerList){
			x.plotChanged();
		}
	}

	public void mouseDragged(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}	
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}
