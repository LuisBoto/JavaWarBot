package graphics;
import javax.swing.*;
import java.awt.Font;

@SuppressWarnings("serial")
public
class ButtonPanel extends JPanel {
	private JButton btnStep;	
	private JButton btnFinish;	
	
	public ButtonPanel() {
	
		add(getBtnStep());
		add(getBtnFinish());
		
	}
	
	private JButton getBtnFinish() {
		if (btnFinish == null) {
			btnFinish = new JButton("Finish");
			btnFinish.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
		return btnFinish;
	}
	
	private JButton getBtnStep() {
		if (btnStep == null) {
			btnStep = new JButton("Step");
			btnStep.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
		return btnStep;
	}
}
