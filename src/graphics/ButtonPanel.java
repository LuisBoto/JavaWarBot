package graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import politicalLogic.Warfield;

@SuppressWarnings("serial")
public
class ButtonPanel extends JPanel {
	private JButton btnStep;	
	private JButton btnFinish;	
	private Warfield warf;
	public HistoryPanel hp;
	public MapPanel mapP;
	
	public ButtonPanel(Warfield war, HistoryPanel hp, MapPanel mapPanel) {
		this.hp = hp;
		this.mapP = mapPanel;
		this.warf = war;
		add(getBtnStep());
		add(getBtnFinish());
		
	}
	
	private JButton getBtnFinish() {
		if (btnFinish == null) {
			btnFinish = new JButton("Finish");
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
}
