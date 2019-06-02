package nicholasJohnstone.MappingApp;
import javax.swing.*;
import java.awt.image.*;
/**
 * Places the MapPanel in a scrollable pane, handles zooming/dragging, swaps between MapPanel and DrawPanel
 */
import java.awt.*;
import java.awt.event.*;
@SuppressWarnings("serial")
public class OuterMapPanel extends JScrollPane implements ImageListener, OwnerListener,YearListener,PlotListener{	
	
	final static String MAPPANEL_LABEL = "Map Panel";
	final static String DRAWPANEL_LABEL = "Draw Panel";
	final static double SCALE_FACTOR = 1.2;
	
	private MapPanel mapPanel;
	private	Scroller scroller=this.new Scroller();
	private JPanel contentPanel;
	
	public OuterMapPanel(MapPanel mapPanel){
		contentPanel=new JPanel(new CardLayout());
		setViewportView(contentPanel);
		this.mapPanel = mapPanel;
		DrawPanel drawPanel = new DrawPanel(mapPanel);
		OwnerManager.addOwnerListener(this);
		DeedManager.addPlotListener(this);
		DatePanel.addYearListener(this);
		FileManager.addImageListener(this);
		mapPanel.setOuterMapPanel(this);
		contentPanel.add(mapPanel, MAPPANEL_LABEL);
		contentPanel.add(drawPanel, DRAWPANEL_LABEL);
		drawPanel.setParentCardLayout(contentPanel);
		mapPanel.addMouseListener(scroller);
		mapPanel.addMouseMotionListener(scroller);
	}
	
	public void yearChanged(){					
		mapPanel.update();
		repaint();
	}

	public void ownerChanged(){						
		repaint();
	}

	public void plotChanged(){				
		repaint();
	}

	public void imageChanged(BufferedImage image){						
		mapPanel.setImage(image);
		repaint();
	}

	public void drawShape(){
		CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
		cardLayout.show(contentPanel, DRAWPANEL_LABEL);
	}
	
	public void zoomIn() {
		rescaleImage(SCALE_FACTOR);	
	}

	public void zoomOut() {
		rescaleImage(1/SCALE_FACTOR);	
	}
	
	private void rescaleImage(double scale){
		mapPanel.rescale(scale);
		Point initVP =getViewport().getViewPosition();
		Dimension size=getViewport().getSize();
		double x=(initVP.getX()+size.getWidth()/2)*scale-size.getWidth()/2;
		double y=(initVP.getY()+size.getHeight()/2)*scale-size.getHeight()/2;
		repaint();
		getViewport().setViewPosition(new Point((int) x,(int) y));
	}

	public class Scroller implements MouseListener,MouseMotionListener{

		private Point initCursorPoint;
		
		public void mouseDragged(MouseEvent e){
			Point scrollPoint=getViewport().getViewPosition();
			double x=initCursorPoint.getX()+scrollPoint.getX()-e.getX();
			double y=initCursorPoint.getY()+scrollPoint.getY()-e.getY();
			x=Math.max(x,0);
			DoublePoint trueSize=mapPanel.getImageSize();
			x=Math.min(x,trueSize.getX());
			y=Math.max(y,0);
			y=Math.min(y,trueSize.getY());
			getViewport().setViewPosition(new Point((int) x,(int) y));
		}
		
		public void mousePressed(MouseEvent e){
			initCursorPoint=new Point(e.getX(),e.getY());
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
		}

		public void mouseReleased(MouseEvent e){
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}	
		
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseClicked(MouseEvent e){}		
		public void mouseMoved(MouseEvent e){}
	}
}
