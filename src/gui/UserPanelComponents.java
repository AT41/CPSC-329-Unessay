package gui;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class UserPanelComponents {
	public JTextField passwordInput;
	public JButton calculateButton;
	public Console console;
	class Console {
		public JTextArea consoleText;
		public JScrollPane scroller;
		
		public Console(JTextArea consoleText, JScrollPane scroller) {
			this.consoleText = consoleText;
			this.scroller = scroller;
		}
		
		public void printToConsole(String text) {
			this.consoleText.setText(this.consoleText.getText() + "\n" + text);
			this.scroller.getVerticalScrollBar().setValue(this.scroller.getVerticalScrollBar().getMaximum());
		}
		
		public void resetConsole() {
			this.consoleText.setText("");
		}
	}
	
	public UserPanelComponents(JTextField passwordInput, JButton calculateButton, JTextArea consoleText, JScrollPane scroller) {
		this.passwordInput = passwordInput;
		this.calculateButton = calculateButton;
		this.console = new Console(consoleText, scroller);
	}
	
	public void resetView() {
		this.console.resetConsole();
	}
}