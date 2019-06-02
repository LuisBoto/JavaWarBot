package nicholasJohnstone.MappingApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
@SuppressWarnings("serial")
/**
 * Brings up dialog for user to edit date range and passes new range back to datePanel
 */
public class DateRangeEditor extends JPanel{

	private static final String YEAR_MIN_LABEL="Start Year:"; 
	private static final String YEAR_MAX_LABEL="End Year:"; 
	private static final String WARNING_LABEL="End Year must be greater than Start Year";
	private static final String OK_LABEL="Ok";
	private static final String CANCEL_LABEL="Cancel";
	
	private static final String[] options = {OK_LABEL,CANCEL_LABEL};
	private static Component parent;

	private JTextField yearMinField=new JTextField(4);
	private JTextField yearMaxField=new JTextField(4);
	private JOptionPane pane;
	private JButton okButton=new JButton();
	private JLabel warning=new JLabel(WARNING_LABEL);
	private DatePanel datePanel;

	public DateRangeEditor(DatePanel datePanel){
		this.datePanel=datePanel;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));	
		add(new JLabel(YEAR_MIN_LABEL));
		add(yearMinField);
		add(new JLabel(YEAR_MAX_LABEL));
		add(yearMaxField);
		add(okButton);
		okButton.setPreferredSize(new Dimension(0,0));
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				pane.setValue(options[0]);
			}
		});
		pane=new JOptionPane(this,JOptionPane.QUESTION_MESSAGE,JOptionPane.OK_CANCEL_OPTION,null,options);
		add(Box.createRigidArea(new Dimension(5,20)));
		warning.setBorder(BorderFactory.createLineBorder(Color.red));
	}

	public static void setDialogParent(Component newParent){
		parent=newParent;
	}

	public int getUserInput(){
		int result=requestInput();
		while(result==0) {
			try{
				int min=Integer.valueOf(yearMinField.getText());
				int max=Integer.valueOf(yearMaxField.getText());
				if(max>min){					//provide bounds as constants
					remove(warning);
					datePanel.setYearRange(min,max);	
					return result;
				}
			} catch(Exception e) {}
			add(warning);
			result=requestInput();
		}
		return result;
	}

	private int requestInput() {
		JDialog dialog = pane.createDialog(parent,"Enter details");	
		yearMinField.requestFocusInWindow();			
		dialog.getRootPane().setDefaultButton(okButton);
		dialog.setVisible(true);
		Object result=pane.getValue();
		if(result==null) {
			return -1;
		} 
		switch((String) result) {
			case OK_LABEL: 	return 0;
			case CANCEL_LABEL: 	return 1;
			default: 	return -1;
		}
	}
}

