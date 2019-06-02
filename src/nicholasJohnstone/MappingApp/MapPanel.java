package nicholasJohnstone.MappingApp;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
/**
 * Displays a background image and user-drawn polygons ("plots"), rescalable, shows popups on left and right clicks
 */
@SuppressWarnings("serial")
class MapPanel extends JPanel {	
	
	private static final String NEW_PLOT_LABEL="Add New Plot";
	private static final String DELETE_PLOT_LABEL="Delete Plot";
	private static final String ADD_OWNER_LABEL="Add Owner";
	private static final String ADD_NEW_OWNER_LABEL="Add New Owner";
	private static final String NO_OWNER_LABEL="No Owners";
	
	private ArrayList<Plot> plotList = new ArrayList<Plot>();		
	private NewDeedPopup newDeedPopup= this.new NewDeedPopup();				
	private InfoPopup infoPopup= this.new InfoPopup();				
	private OuterMapPanel outerMapPanel;
	private BufferedImage mapImage=null;
	private DoublePoint trueSize;

	public MapPanel() {
		addMouseListener(newDeedPopup);
		addMouseListener(infoPopup);
		trueSize=new DoublePoint(600,600);
	}

	public void paintComponent(Graphics g) {
		double width= trueSize.getX();
		double height= trueSize.getY();
		g.setColor(Color.BLACK);
		g.clearRect(0,0,(int) (width*OuterMapPanel.SCALE_FACTOR*2),(int) (height*OuterMapPanel.SCALE_FACTOR*2));
		g.drawImage(mapImage,0,0,(int) width,(int) height,null);
		g.drawRect(0,0,(int) width,(int) height);
		Graphics2D g2 = (Graphics2D) g;
   		g2.setStroke(new BasicStroke(3));
		for(Plot plot: plotList){
			Polygon polygon=plot.getPolygon();
			g2.setColor(plot.getColor());
			g2.fillPolygon(polygon);
			g2.setColor(Color.BLACK);
			g2.drawPolygon(polygon);
		}
		setPreferredSize(new Dimension((int) width,(int) height));
		revalidate();
	}
	
	public void update() {
		for(Plot plot:plotList){
			plot.update();	
		}
	}

	public boolean setImage(BufferedImage image){
		if(image!=null) {
			mapImage=image;
			resetImageSize();
			return true;
		} 
		return false;
		
	}	

	public void resetImageSize(){					
		trueSize=new DoublePoint(mapImage.getWidth(null), mapImage.getHeight(null));
	}

	public void setOuterMapPanel(OuterMapPanel outerMapPanel){
		this.outerMapPanel=outerMapPanel;
	}

	public void rescale(double scale){
		trueSize.rescale(scale);
		for(Plot plot:plotList){
			plot.rescale(scale);
		}
	}

	public Plot plotContained(MouseEvent e){
		Plot plot;
		for(int i=plotList.size()-1;i>=0;i--){
			plot=plotList.get(i);
			if(plot.getPolygon().contains(e.getX(),e.getY())) {
				return plot;
			}
		}
		return null;	
	}
	public void addPlot(Plot newPlot){
		plotList.add(newPlot);
	}
	
	public ArrayList<Plot> getPlots(){
		return plotList;
	}

	public void setPlotList(ArrayList<Plot> x){
		plotList=x;
	}

	public DoublePoint getImageSize(){
		return trueSize;
	}
	
	public void setImageSize(DoublePoint size){
		trueSize=size;
	}

	class NewDeedPopup extends JPopupMenu implements MouseListener{
		
		public void mouseClicked(MouseEvent e){

			if (e.getButton()==MouseEvent.BUTTON3){
				buildMenu(plotContained(e));
				this.show(e.getComponent(),e.getX(), e.getY());
			}
		}
		
		public void buildMenu(Plot plot){
			removeAll();
			JMenuItem addPlotIcon=new JMenuItem(NEW_PLOT_LABEL);
			addPlotIcon.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					outerMapPanel.drawShape();
				}
			});
			if(plot==null) {
				add(addPlotIcon);
				return;
			}	
			JMenu ownersMenu = new JMenu(ADD_OWNER_LABEL);
			add(ownersMenu);
			add(addPlotIcon);	
			JMenuItem deletePlotItem = new JMenuItem(DELETE_PLOT_LABEL);	
			add(deletePlotItem);
			deletePlotItem.addActionListener(MapPanel.this.new PlotDeleteListener(plot));
			if(OwnerManager.getOwners().isEmpty()){
				ownersMenu.add(new JLabel(NO_OWNER_LABEL));
			} else {						
				for(Owner owner : OwnerManager.getOwners()) {
					JMenuItem ownerItem=new JMenuItem(owner.toString());
					ownersMenu.add(ownerItem);
					ownerItem.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							DeedManager.makeNewDeed(plot,owner);
						}
					});
				}
			}
			ownersMenu.addSeparator();
			JMenuItem addNewOwnerItem=new JMenuItem(ADD_NEW_OWNER_LABEL);
			ownersMenu.add(addNewOwnerItem);
			addNewOwnerItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Owner owner=OwnerManager.makeNewOwner();
					if(owner!=null){
						DeedManager.makeNewDeed(plot,owner);
					}
				}
			});
		}

		public void mousePressed(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}

	}

	class InfoPopup extends JPopupMenu implements MouseListener {

		public void mouseClicked(MouseEvent e){	
			if (e.getButton()==MouseEvent.BUTTON1){
				Plot plot=plotContained(e);
				buildMenu(plot);
				this.show(e.getComponent(),e.getX(), e.getY());
			}
		}
		
		public void buildMenu(Plot plot){
			this.removeAll();
			if(plot!=null){
				Set<Deed> registrations=plot.getDeeds();
				if(registrations.isEmpty()){
					this.add(new JLabel(NO_OWNER_LABEL));
				} else {
					for(Deed registration:registrations){
						JMenuItem registrationItem=new JMenuItem(registration.toString());
						this.add(registrationItem);
						registrationItem.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e){
								registration.getManager().update();
							}
						});
					}
				}
			}
		}	

		public void mousePressed(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}

	}

	class PlotDeleteListener implements ActionListener{
		private Plot plot;
	
		public PlotDeleteListener(Plot plot) {
			this.plot=plot;
		}
		
		public void actionPerformed(ActionEvent event){
			plotList.remove(plot);
			repaint();
		}
	}
	
}

