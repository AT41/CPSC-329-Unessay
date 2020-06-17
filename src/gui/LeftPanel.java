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
		
		"The common passwords attack takes a list of 1 million commonly entered passwords and tests each of them to see if any pass. If any of them pass, the program will show how the existing password ranks in popularity."
		+ "\n\nAdditionally, our algorithm calculates the minimum Levenschtein distance between your entered password and each of the passwords in our database, as a measurement of how close your password could be to an existing password. "
		+ "Levenschtein distance is a measurement of the minimum amounts of edits (insertion, deletion, or character swap) it takes to turn one string into another. ",
		
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