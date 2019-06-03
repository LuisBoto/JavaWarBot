package graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import politicalLogic.Warfield;

@SuppressWarnings("serial")
public
class ButtonPanel extends JPanel {
	private JButton btnStep;	
	private JButton btnFinish;	
	private Warfield warf;
	public HistoryPanel hp;
	public MapPanel mapP;
	private JButton btnAutomate;
	private boolean isAutoRunning = false;
	private Timer auto;
	
	public ButtonPanel(Warfield war, HistoryPanel hp, MapPanel mapPanel) {
		this.hp = hp;
		this.mapP = mapPanel;
		this.warf = war;
		add(getBtnStep());
		add(getBtnFinish());
		add(getBtnAutomate());
		
	}
	
	private JButton getBtnFinish() {
		if (btnFinish == null) {
			btnFinish = new JButton("Finish");
			btnFinish.setMnemonic('f');
			btnFinish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					warf.fullWar();
					hp.updateHistory(warf.getBattleLog().toString());
					mapP.repaint();
					mapP.validate();
				}
			});
			btnFinish.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
		return btnFinish;
	}
	
	private JButton getBtnStep() {
		if (btnStep == null) {
			btnStep = new JButton("Step");
			btnStep.setMnemonic('s');
			btnStep.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					warf.stepWar();
					hp.updateHistory(warf.getBattleLog().toString());
					mapP.repaint();
					mapP.validate();
				}
			});
			btnStep.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
		return btnStep;
	}
	private JButton getBtnAutomate() {
		if (btnAutomate == null) {
			btnAutomate = new JButton("Automate");
			btnAutomate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!isAutoRunning) {
						auto = new Timer (1000, new ActionListener () 
						{ 
						    public void actionPerformed(ActionEvent e) 
						    { 
						    	warf.stepWar();
								hp.updateHistory(warf.getBattleLog().toString());
								mapP.repaint();
								mapP.validate();
						    } 
						}); 
						isAutoRunning = true;
						btnAutomate.setText("Stop");
						auto.start();
					} else {
						auto.stop();
						btnAutomate.setText("Automate");
						isAutoRunning = false;
					}
				}
			});
			btnAutomate.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
		return btnAutomate;
	}
	
	public void newState(Warfield war, HistoryPanel hp, MapPanel mapPanel) {
		this.hp = hp;
		this.mapP = mapPanel;
		this.warf = war;		
	}
}
