package nicholasJohnstone.MappingApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * Provides functionality and dialog for adding/editing/deleting one deed
 */
@SuppressWarnings("serial")
public class DeedManager extends JPanel{

	private static final String YEAR_LABEL="Year acquired:";
	private static final String DELETE_LABEL="Delete This Owner";
	private static final String NEW_OWNER_LABEL="Add";	//Must be different from OK_LABEL
	private static final String OK_LABEL="Ok";
	private static final String CANCEL_LABEL="Cancel";
	private static final String DIALOG_TITLE="Enter Details";
	
	private static final Object[] newDeedOptions={NEW_OWNER_LABEL,CANCEL_LABEL};		
	private static final Object[] deedOptions={OK_LABEL,DELETE_LABEL,CANCEL_LABEL};
	private static final int deedOptionType=JOptionPane.YES_NO_CANCEL_OPTION;
	private static final int newDeedoptionType=JOptionPane.YES_NO_OPTION;
	
	private static ArrayList<PlotListener> plotListenerList=new ArrayList<PlotListener>();
	private static Component parent;
	
	private JTextField yearPlot=new JTextField(10);
	private JOptionPane pane;
	private Object[] options;
	private int optionType;
	private JButton okButton=new JButton();
	private JLabel warning=new JLabel();
	private Deed deed;
	private Plot plot;
	private Owner owner;

	public static void setDialogParent(Component newParent){
		parent=newParent;
	}

	public static void addPlotListener(PlotListener x){
		plotListenerList.add(x);
	}

	public static Deed makeNewDeed(Plot plot,Owner owner){
		DeedManager manager= new DeedManager(plot,owner);
		if(manager.update()){
			manager.options=deedOptions;
			manager.optionType=deedOptionType;
			manager.pane.setOptions(manager.options);
			manager.pane.setOptionType(manager.optionType);
			return manager.deed;
		}
		return null;
	}
			
	public DeedManager(Plot plot, Owner owner){
		this.plot=plot;
		this.owner=owner;
		deed=new Deed();
		deed.setPlot(plot);
		deed.setOwner(owner);
		deed.setManager(this);
		options=newDeedOptions;
		optionType=newDeedoptionType;
		buildDialog();
	}

	public DeedManager(Deed deed){
		this.plot=deed.getPlot();
		this.owner=deed.getOwner();
		this.deed=deed;
		yearPlot.setText(String.valueOf(deed.getDate()));
		options=deedOptions;
		optionType=deedOptionType;
		buildDialog();
	}

	public void buildDialog(){
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));	
		add(new JLabel(YEAR_LABEL));
		add(yearPlot);
		add(okButton);
		okButton.setPreferredSize(new Dimension(0,0));
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				pane.setValue(options[0]);
			}
		});
		pane=new JOptionPane(this,JOptionPane.QUESTION_MESSAGE,optionType,null,options);
		add(Box.createRigidArea(new Dimension(5,20)));
		warning.setBorder(BorderFactory.createLineBorder(Color.red));
	}

	public boolean update (){		
		int result=getUserInput();
		switch(result) {
		case 0: plot.update();
			plotChanged();
			return true;
		case 1: plot.update();
			plotChanged();
			owner.getManager().addDeed(deed);
			return true;
		case 2:	plot.removeDeed(deed);
			plot.update();
			plotChanged();
			return false;
		} 
		return false;
	}

	public int getUserInput(){
		int result=requestInput();
		while(result==0||result==1) {
			try{
				int date=Integer.valueOf(yearPlot.getText());
				remove(warning);
				plot.removeDeed(deed);
				deed.setDate(date);
				plot.addDeed(deed);
				return result;
			} catch(Exception e) {}
			warning.setText("Enter a number");
			add(warning);
			result=requestInput();
		}
		return result;
	}

	private int requestInput() {
		JDialog dialog = pane.createDialog(parent,DIALOG_TITLE);	
		yearPlot.requestFocusInWindow();			
		dialog.getRootPane().setDefaultButton(okButton);
		dialog.setVisible(true);
		Object result=pane.getValue();
		if(result==null) {
			return -1;
		} 
		switch((String) result) {
			case OK_LABEL: 		return 0;
			case NEW_OWNER_LABEL: return 1;
			case DELETE_LABEL: 	return 2;
			case CANCEL_LABEL: 	return 3;
			default: 		return 4;
		}
	}

	public void plotChanged() {						
		for(PlotListener x:plotListenerList){
			x.plotChanged();
		}
	}
}
