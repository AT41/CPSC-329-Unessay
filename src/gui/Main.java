package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;  // Using Frame class in package java.awt
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//A GUI program is written as a subclass of Frame - the top-level container
//This subclass inherits all properties from Frame, e.g., title, icon, buttons, content-pane
public class Main extends Frame implements MainGUI {
	class RightContainer {
		public JLabel[] outcomes;
		public Container container;
		
		public RightContainer(Container container, JLabel[] allLabels) {
			this.outcomes = allLabels;
			this.container = container;
		}
		
		public void changeLabelStatus(int labelIndex, AttackStatus status) {
			outcomes[labelIndex].setText(status == AttackStatus.IMPOSSIBLE ? "NO" : "YES");
		}
	}
	class LeftPanel {
		public JLabel[] allLabels;
		public Container container;
		
		public LeftPanel(Container container, JLabel[] allLabels) {
			this.allLabels = allLabels;
			this.container = container;
		}
	}
	class Console {
		public JTextArea consoleText;
		
		public Console(JTextArea consoleText) {
			this.consoleText = consoleText;
		}
		
		public void printToConsole(String text) {
			this.consoleText.setText(this.consoleText.getText() + "\n" + text);
		}
	}

	public static enum AttackType {
		BRUTE_FORCE,
		COMMON_PASSWORDS,
		RAINBOW_TABLE,
		DICTIONARY
	}
	public static enum AttackStatus {
		IMPOSSIBLE,
		POSSIBLE
	}
	
	private static final String[] ATTACK_NAMES = {"Brute Force Attack", "Common Passwords Attack", "Rainbow Table Attack", "Dictionary Attack"};
	private static final int[] WIDTH_HEIGHT = {700, 500};

	private RightContainer rightContainer;
	private LeftPanel leftContainer;
	private Console console;
	
	public void setStatusFor(AttackType type, AttackStatus status) {
		this.rightContainer.changeLabelStatus(type.ordinal(), status);
	}
	
	public void printToGUIConsole(String text) {
		this.console.printToConsole(text);
	}
	
	public Main() {
		this.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                dispose();  
            }
        });
		
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout());
		setTitle("Password Strength Calculator");
		setSize(WIDTH_HEIGHT[0], WIDTH_HEIGHT[1]);
		
		this.leftContainer = createLeftSide();
		this.add(this.leftContainer.container, BorderLayout.WEST);
		this.rightContainer = createRightSide();
		this.add(rightContainer.container, BorderLayout.EAST);
		
		Container userContainer = createUserInputPage();
		this.add(userContainer, BorderLayout.CENTER);

		setVisible(true);
	}
	
	private Container createUserInputPage() {
		Container userInput = new Container();
		userInput.setLayout(new BoxLayout(userInput, BoxLayout.PAGE_AXIS));
		userInput.setVisible(true);
		
		JLabel passwordLabel = new JLabel("Enter Password");
		passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JTextField passwordInput = new JTextField();
		passwordInput.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordInput.setMaximumSize(
				new Dimension(200, passwordInput.getPreferredSize().height)
		);
		JButton calculate = new JButton("Calculate Strength");
		calculate.setDoubleBuffered(true); // Prevents button from flickering
		calculate.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		userInput.add(passwordLabel);
		userInput.add(passwordInput);
		userInput.add(calculate);
		
		JTextArea console = new JTextArea();
		console.setBorder(BorderFactory.createLineBorder(Color.black));
		console.setEditable(false);
		console.setVisible(true);
		userInput.add(console);
		this.console = new Console(console);
		
		return userInput;
	}
	
	private LeftPanel createLeftSide() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(Main.ATTACK_NAMES.length, 1));
		leftPanel.setVisible(true);
		leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
		leftPanel.setBackground(Color.white);
		
		JLabel[] allLabels = new JLabel[Main.ATTACK_NAMES.length];
		for (int i = 0; i < Main.ATTACK_NAMES.length; i++) {
			allLabels[i] = new JLabel(Main.ATTACK_NAMES[i]);
			allLabels[i].setHorizontalAlignment(JLabel.CENTER);
			allLabels[i].setBorder(BorderFactory.createMatteBorder(1, 1, i == (Main.ATTACK_NAMES.length-1)? 1 : 0, 1, Color.black));
			leftPanel.add(allLabels[i]);
		}
		
		return new LeftPanel(leftPanel, allLabels);
	}
	
	private RightContainer createRightSide() {
		Container rightContainer = new Container();
		rightContainer.setLayout(new BoxLayout(rightContainer, BoxLayout.PAGE_AXIS));
		
		JLabel[] attackLabels = new JLabel[Main.ATTACK_NAMES.length];
		JLabel[] outcomeLabels = new JLabel[Main.ATTACK_NAMES.length];
		for (int i = 0; i < Main.ATTACK_NAMES.length; i++) {
			attackLabels[i] = new JLabel(Main.ATTACK_NAMES[i]);
			outcomeLabels[i] = new JLabel();
			
			Container temp = new Container();
			temp.setVisible(true);
			temp.setLayout(new FlowLayout());
			temp.add(attackLabels[i]);
			temp.add(outcomeLabels[i]);
			rightContainer.add(temp);
		}
		
		return new RightContainer(rightContainer, outcomeLabels);
	}
	
	public static void main(String[] args) {
	   new Main();
	}
	
	// This is code proving that we don't need to relinquish control of the main thread to update the display
	/*private void test(Container c) {
		JLabel testL = new JLabel();
		c.add(testL);
		int i = 0;
		while (true) {
			testL.setText(Integer.toString(i));
			i++;
		}
	}*/
}