package nicholasJohnstone.MappingApp;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.colorchooser.*;
/**
 * Provides functionality and dialog for adding/editing/deleting one owner
 */
@SuppressWarnings("serial")
class OwnerManager extends JPanel {
	
	private static final String NAME_LABEL="Name: ";
	private static final String OK_LABEL="Ok";
	private static final String CANCEL_LABEL="Cancel";
	private static final String DIALOG_TITLE="Enter Details";
	
	private static final Object[] ownerOptions={OK_LABEL,CANCEL_LABEL};
	
	private static TreeSet<Owner> owners=new TreeSet<Owner>();
	private static ArrayList<OwnerListener> ownerListeners=new ArrayList<OwnerListener>();
	private static Component parent;
	
	private JTextField namePlot=new JTextField(10);
	private JColorChooser colorChooser=new JColorChooser();
	private JOptionPane pane;
	private JButton okButton=new JButton();
	private Owner owner;
	private ArrayList<Deed> deedList;

	public static TreeSet<Owner> getOwners(){
		return owners;
	}

	public static void setOwners(TreeSet<Owner> newOwners){
		owners=newOwners;
	}

	public static void setDialogParent(Component newParent){			//remember to use in GUI
		parent=newParent;
	}

	public static void addOwnerListener(OwnerListener x){
		ownerListeners.add(x);
	}

	public static void ownerChanged(){
		for(OwnerListener x:ownerListeners){
			x.ownerChanged();
		}
	}

	public static Owner makeNewOwner(){						
		OwnerManager manager=new OwnerManager();
		if(manager.update()){
			return manager.owner;
		}
		return null;
	}

	public OwnerManager(Owner owner){
		this.owner=owner;
		this.deedList=owner.getDeedList();
		namePlot.setText(owner.getName());
		colorChooser.setColor(owner.getColor());
		buildDialog();
	}
			
	private OwnerManager(){
		owner=new Owner();
		this.deedList=owner.getDeedList();
		buildDialog();		
	}
	
	public void buildDialog(){		
		add(new JLabel(NAME_LABEL));
		add(namePlot);
		add(colorChooser);
		add(okButton);
		editColorChooser();
		okButton.setPreferredSize(new Dimension(0,0));
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				pane.setValue(ownerOptions[0]);
			}
		});
		pane=new JOptionPane(this,JOptionPane.QUESTION_MESSAGE,JOptionPane.OK_CANCEL_OPTION,null,ownerOptions);
	}	

	public void editColorChooser(){
		colorChooser.setPreviewPanel(new JPanel());								//prevents preview panel showing
		AbstractColorChooserPanel[] panels=colorChooser.getChooserPanels();		//removes other color chooser panels
		for(AbstractColorChooserPanel p:panels){
			if(!p.getDisplayName().equals("Swatches")){
				colorChooser.removeChooserPanel(p);
			}
		}
	}

	public boolean update(){		
		if(getUserInput()){
			for(Deed deed:deedList){
				deed.getPlot().update();
			}	
			ownerChanged();
			return true;
		}
		return false;
	}

	public boolean getUserInput(){
		JDialog dialog = pane.createDialog(parent,DIALOG_TITLE);
		dialog.getRootPane().setDefaultButton(okButton);
		namePlot.requestFocusInWindow();
		dialog.setVisible(true);
		Object result=pane.getValue();
		if(result==null) {
			return false;
		} else if(ownerOptions[0].equals(result)) {
			owners.remove(owner);
			owner.setName(namePlot.getText());
			owner.setColor(colorChooser.getColor());
			owners.add(owner);
			return true;
		}
		return false;
	}

	public void addDeed(Deed deed){
		deedList.add(deed);
	}

	public void removeDeed(Deed deed){
		deedList.remove(deed);
	}

	public void delete(){
			for(Deed deed:deedList){
				Plot plot = deed.getPlot();
				plot.removeDeed(deed);
				plot.update();
			}	
			owners.remove(owner);		
			ownerChanged();				
	}
						
}
