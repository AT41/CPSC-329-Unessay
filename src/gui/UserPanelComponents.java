package gui;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class UserPanelComponents {
	public JTextField passwordInput;
	public JButton calculateButton;
	public Console console;
	class Console {
		public JTextArea consoleText;
		
		public Console(JTextArea consoleText) {
			this.consoleText = consoleText;
		}
		
		public void printToConsole(String text) {
			this.consoleText.setText(this.consoleText.getText() + "\n" + text);
		}
	}
	
	public UserPanelComponents(JTextField passwordInput, JButton calculateButton, JTextArea consoleText) {
		this.passwordInput = passwordInput;
		this.calculateButton = calculateButton;
		this.console = new Console(consoleText);
	}
}