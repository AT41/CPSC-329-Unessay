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
		"The brute force attack makes the basic assumption that all passwords are crackable using this method given enough time and space. Our brute force calculation calculates approximately how many guesses it would take to crack the given password."
		+ "\n\nThe way this number is calculated is by adding two numbers. The first number 'a' is a summation p^i where p is the number of possible characters in the password and where 1<=i<p. This number represents each possible password of length"
		+ " 1<=p-1. \n\nThe second number 'b', is the number of possible passwords of length p, such that each if each password 'x' of length p was converted to a base p number and the user's password 'u' was also converted to a base p number, then b=u-1. "
		+ "\n\nThus our estimate is a+b. ", 
		
		"The common passwords attack takes a list of 1 million commonly entered passwords and tests each of them to see if any pass. If any of them pass, the program will show how the existing password ranks in popularity."
		+ "\n\nAdditionally, our algorithm calculates the minimum Levenschtein distance between your entered password and each of the passwords in our database, as a measurement of how close your password could be to an existing password. "
		+ "Levenschtein distance is a measurement of the minimum amounts of edits (insertion, deletion, or character swap) it takes to turn one string into another. "
		+ "\n\nWe felt it appropriate to add this to our algorithm, as a brute force attack modifying existing passwords in a common passwords database can break your password easily.",
		
		"The rainbow table attack takes a list of solved md5 hashes and compares them to the md5 hash of the user's password to see if there are any matches.",
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
			this.infoDisplay.setCaretPosition(0);
		}
	}
}