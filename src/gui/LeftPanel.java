package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class LeftPanel {
	public JLabel[] allLabels;
	public JPanel panel;
	
	private static final String[] infoMessages = {
		"bruteForceInfoHere", 
		"commonPasswordInfoHere",
		"rainbowTableInfoHere",
		"dictionaryInfoHere"
	};
	
	public LeftPanel(JPanel panel, JLabel[] allLabels, JButton[] infoButtons, JTextArea infoDisplay) {
		this.allLabels = allLabels;
		this.panel = panel;
		
		for (int i = 0; i < infoButtons.length; i++) {
			infoButtons[i].addActionListener(new InfoButtonListener(infoMessages[i], infoDisplay));
		}
	}
	
	class InfoButtonListener implements ActionListener {
		private String message;
		private JTextArea infoDisplay;
		
		public InfoButtonListener(String message, JTextArea infoDisplay) {
			this.message = message;
			this.infoDisplay = infoDisplay;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			this.infoDisplay.setText(message);
		}
	}
}