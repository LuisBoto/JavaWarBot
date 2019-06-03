package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import politicalLogic.Locality;
import politicalLogic.Warfield;

/**
 * Displays a background image and user-drawn polygons ("plots"), rescalable,
 * shows popups on left and right clicks
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel {

	private static final String NEW_PLOT_LABEL = "Add new Plot";
	private static final String DELETE_PLOT_LABEL = "Delete Plot";
	private static final String NAME_GOVERNMENT_LABEL = "Set zone's Goverment...";
	private static final String NAME_LOCALITY_LABEL = "Set zone's Name...";
	private static final String ADD_FRONTIER_LABEL = "Add new Frontier...";

	private ArrayList<Plot> plotList = new ArrayList<Plot>();
	private PlottingMenu plottingMenu = new PlottingMenu(); //Shows when right-clicking the panel
	private OuterMapPanel outerMapPanel;
	private BufferedImage mapImage = null;
	private DoublePoint trueSize;
	private Warfield warf;
	private boolean frontierSelecting = false;
	private Locality auxLocality; //Variable to store initial locality while selecting a frontier

	public MapPanel(Warfield w) {
		warf = w;
		addMouseListener(plottingMenu);
		trueSize = new DoublePoint(600, 600);
	}

	public void paintComponent(Graphics g) {
		double width = trueSize.getX();
		double height = trueSize.getY();
		g.setColor(Color.BLACK);
		g.clearRect(0, 0, (int) (width * OuterMapPanel.SCALE_FACTOR * 2),
				(int) (height * OuterMapPanel.SCALE_FACTOR * 2));
		g.drawImage(mapImage, 0, 0, (int) width, (int) height, null);
		g.drawRect(0, 0, (int) width, (int) height);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		for (Plot plot : plotList) {
			Polygon polygon = plot.getPolygon();
			g2.setColor(plot.getColor());
			g2.fillPolygon(polygon);
			g2.setColor(Color.BLACK);
			g2.drawPolygon(polygon);
		}
		setPreferredSize(new Dimension((int) width, (int) height));
		revalidate();
	}

	public void update() {
		for (Plot plot : plotList) {
			plot.update(null);
		}
	}

	public boolean setImage(BufferedImage image) {
		if (image != null) {
			mapImage = image;
			resetImageSize();
			return true;
		}
		return false;

	}

	public void resetImageSize() {
		trueSize = new DoublePoint(mapImage.getWidth(null), mapImage.getHeight(null));
	}

	public void setOuterMapPanel(OuterMapPanel outerMapPanel) {
		this.outerMapPanel = outerMapPanel;
	}

	public void rescale(double scale) {
		trueSize.rescale(scale);
		for (Plot plot : plotList) {
			plot.rescale(scale);
		}
	}

	public Plot plotContained(MouseEvent e) {
		Plot plot;
		for (int i = plotList.size() - 1; i >= 0; i--) {
			plot = plotList.get(i);
			if (plot.getPolygon().contains(e.getX(), e.getY())) {
				return plot;
			}
		}
		return null;
	}

	public void addPlot(Plot newPlot) {
		plotList.add(newPlot);
	}

	public ArrayList<Plot> getPlots() {
		return plotList;
	}

	public void setPlotList(ArrayList<Plot> x) {
		plotList = x;
	}

	public DoublePoint getImageSize() {
		return trueSize;
	}

	public void setImageSize(DoublePoint size) {
		trueSize = size;
	}

	class PlottingMenu extends JPopupMenu implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				if(frontierSelecting) {
					Locality loc = warf.getLocalityFromPlot(plotContained(e));
					if(loc!=null) {
						loc.addFrontier(auxLocality);
						auxLocality.addFrontier(loc);
					}
					auxLocality = null;
					frontierSelecting = false;
				}
			}			
			if (e.getButton() == MouseEvent.BUTTON3) {
				buildMenu(plotContained(e));
				this.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		public void buildMenu(Plot plot){
			removeAll();
			JMenuItem addPlotItem=new JMenuItem(NEW_PLOT_LABEL);
			addPlotItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					outerMapPanel.drawShape();
				}
			});
			//Clicking on empty map only prompts new plot option
			if(plot==null) {
				add(addPlotItem);
				return;
			}	
			//Clicking on a plot shows other options			
			Locality lo = warf.getLocalityFromPlot(plot);
			if (lo!=null) {
				if(lo.getGovernment()!=null)
					add(new JMenuItem("Government: "+lo.getGovernment()));
				if(lo.getName()!=null)
					add(new JMenuItem("Local name: "+lo.getName()));
				JMenu frontierList = new JMenu("Frontiers");
				for (int i=0; i<lo.getFrontiers().size(); i++) {
					if(lo.getFrontiers().get(i).getName()!=null) {
						JMenuItem nombre = new JMenuItem(lo.getFrontiers().get(i).getName());
						frontierList.add(nombre);
					}
				}
				if (lo.getFrontiers().size()>0)
					add(frontierList);
				JMenuItem addFrontierItem = new JMenuItem(ADD_FRONTIER_LABEL);
				addFrontierItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//After this, user must click on another locality to add a Frontier.
						frontierSelecting = true;
						auxLocality = lo;
					}
				});
				add(addFrontierItem);
				add(new JSeparator());
			}
			add(addPlotItem);	
			JMenuItem deletePlotItem = new JMenuItem(DELETE_PLOT_LABEL);	
			add(deletePlotItem);
			deletePlotItem.addActionListener(new PlotDeleteListener(plot));
			JMenuItem governmentPlotItem = new JMenuItem(NAME_GOVERNMENT_LABEL);
			governmentPlotItem.addActionListener(new PlotGovernmentListener(plot));					
			add(governmentPlotItem);
			JMenuItem namePlotItem = new JMenuItem(NAME_LOCALITY_LABEL);
			namePlotItem.addActionListener(new PlotNameListener(plot));					
			add(namePlotItem);
			add(deletePlotItem);
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

	}

	class PlotDeleteListener implements ActionListener {
		private Plot plot;

		public PlotDeleteListener(Plot plot) {
			this.plot = plot;
		}

		public void actionPerformed(ActionEvent event) {
			plotList.remove(plot);
			repaint();
			validate();
		}
	}
	
	class PlotNameListener implements ActionListener {
		private Plot plot;

		public PlotNameListener(Plot plot) {
			this.plot = plot;
		}

		public void actionPerformed(ActionEvent event) {
			Locality lo = warf.getLocalityFromPlot(plot);
			String name = JOptionPane.showInputDialog(null, "Locality's name is...", "Input name", 2);
			if (lo == null) {
				Locality l = new Locality();
				l.setGraphic(plot);
				l.setName(name);
				warf.addLocality(l);
			} else {
				lo.setName(name);
			}
			repaint();
			validate();
		}
	}
	
	class PlotGovernmentListener implements ActionListener {
		private Plot plot;

		public PlotGovernmentListener(Plot plot) {
			this.plot = plot;
		}

		public void actionPerformed(ActionEvent event) {
			Locality lo = warf.getLocalityFromPlot(plot);
			String gov = JOptionPane.showInputDialog(null, "Locality's government is...", "Input government", 2);
			if (lo == null) {
				Locality l = new Locality();
				l.setGraphic(plot);
				l.setGovernment(gov);
				warf.addLocality(l);
			} else {
				lo.setGovernment(gov);
			}
			repaint();
			validate();
		}
	}

}
