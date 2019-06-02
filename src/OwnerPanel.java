import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
/**
 * Displays a list of Owner names and colors in alphabetical order
 */
@SuppressWarnings("serial")
class OwnerPanel extends JPanel implements OwnerListener{		
	
	private static final String NEW_OWNER_LABEL="Add New Owner";
	private static final String DELETE_LABEL="Delete";
	
	private JPanel innerPanel;
	private JButton newOwnerButton;
	
	public OwnerPanel(){
		setLayout(new GridLayout(1,1));		
		innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(0,1));
		JPanel extraPanel = new JPanel();
		extraPanel.add(innerPanel);
		JScrollPane scrollPane = new JScrollPane(extraPanel);
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();	
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border border = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
  		setBorder(border);
		add(scrollPane,BorderLayout.NORTH);
		OwnerManager.addOwnerListener(this);
		newOwnerButton=new JButton(NEW_OWNER_LABEL);
		newOwnerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				OwnerManager.makeNewOwner();		
			}
		});
		buildMenu();
	}

	public void buildMenu(){			
		innerPanel.removeAll();	
		innerPanel.add(newOwnerButton);
		for(Owner owner:OwnerManager.getOwners()){		
			JButton ownerButton = new JButton(owner.toString(), new ColorIcon(owner.getColor()));
			innerPanel.add(ownerButton);
			ownerButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					owner.getManager().update();
				}
			});
			ownerButton.addMouseListener(new DeletePopup(owner));
		}
	}
	
	public void ownerChanged(){
		buildMenu();
		revalidate();
	}

	class DeletePopup extends JPopupMenu implements MouseListener {

		public DeletePopup(Owner owner){
			JMenuItem deleteIcon=new JMenuItem(DELETE_LABEL);
			add(deleteIcon);
			deleteIcon.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					owner.getManager().delete();
				}
			});
		}

		public void mouseClicked(MouseEvent e){	
			show(e.getComponent(),e.getX(), e.getY());
		}

		public void mousePressed(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}

	}	
}


