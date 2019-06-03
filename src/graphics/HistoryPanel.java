package graphics;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;

@SuppressWarnings("serial")
public
class HistoryPanel extends JPanel {
	private JTextPane textPane;
	private JLabel lblHistory;
	private JScrollPane scrollPane;
	
	
	public HistoryPanel() {
		setLayout(new BorderLayout(0, 0));
		add(getLblHistory(), BorderLayout.NORTH);
		add(getScrollPane(), BorderLayout.CENTER);	
	}

	private JTextPane getTextPane() {
		if (textPane == null) {
			textPane = new JTextPane();
			textPane.setFont(new Font("Arial", Font.PLAIN, 18));
			textPane.setEditable(false);
			textPane.setText("War has not started!");
		}
		return textPane;
	}
	private JLabel getLblHistory() {
		if (lblHistory == null) {
			lblHistory = new JLabel("History");
			lblHistory.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblHistory.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblHistory;
	}
	
	public void updateHistory(String log) {
		getTextPane().setText(log);
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTextPane());
		}
		return scrollPane;
	}
}
