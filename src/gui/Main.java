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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	public static final Color BORDER_COLOR = Color.black;
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

	private RightPanel rightContainer;
	private LeftPanel leftContainer;
	private UserPanelComponents userPanelComponents;


	@Override
	public void addListenerForButton(ActionListener returnsPlaintextPassword) {
		this.userPanelComponents.calculateButton.addActionListener(e -> {
			returnsPlaintextPassword.actionPerformed(new ActionEvent(this.userPanelComponents.passwordInput, 0, ""));
		});
	}
	@Override
	public void setStatusFor(AttackType type, AttackStatus status) {
		this.rightContainer.changeLabelStatus(type.ordinal(), status);
	}
	@Override
	public void printToGUIConsole(String text) {
		this.userPanelComponents.console.printToConsole(text);
	}
	@Override
	public void setButtonEnableOrDisable(boolean shouldEnable) {
		this.userPanelComponents.calculateButton.setEnabled(shouldEnable);
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
		setTitle("Password Strength Calculator");
		setSize(WIDTH_HEIGHT[0], WIDTH_HEIGHT[1]);
		
		JPanel mainPanel = new JPanel();
		int mainPanelPadding = 4;
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(mainPanelPadding, mainPanelPadding, mainPanelPadding, mainPanelPadding));
		mainPanel.setVisible(true);
		add(mainPanel);
		
		this.leftContainer = createLeftSide();
		mainPanel.add(this.leftContainer.panel, BorderLayout.WEST);
		this.rightContainer = createRightSide();
		mainPanel.add(rightContainer.panel, BorderLayout.EAST);
		
		JPanel userInputPanel = createUserInputPage();
		mainPanel.add(userInputPanel, BorderLayout.CENTER);

		setVisible(true);
	}
	
	private JPanel createUserInputPage() {
		JPanel userInput = new JPanel();
		userInput.setLayout(new BoxLayout(userInput, BoxLayout.PAGE_AXIS));
		userInput.setVisible(true);
		userInput.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
		
		JLabel passwordLabel = new JLabel("Enter Password");
		passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JTextField passwordInput = new JTextField();
		passwordInput.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordInput.setMaximumSize(
				new Dimension(200, passwordInput.getPreferredSize().height)
		);
		JButton calculateButton = new JButton("Calculate Strength");
		calculateButton.setDoubleBuffered(true); // Prevents button from flickering
		calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		userInput.add(passwordLabel);
		userInput.add(passwordInput);
		userInput.add(calculateButton);
		
		JTextArea console = new JTextArea();
		console.setBorder(BorderFactory.createLineBorder(Main.BORDER_COLOR));
		console.setEditable(false);
		console.setVisible(true);
		userInput.add(console);
		this.userPanelComponents = new UserPanelComponents(passwordInput, calculateButton, console);
		
		return userInput;
	}
	
	private LeftPanel createLeftSide() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(Main.ATTACK_NAMES.length, 1));
		leftPanel.setVisible(true);
		leftPanel.setBorder(BorderFactory.createLineBorder(Main.BORDER_COLOR));
		
		JLabel[] allLabels = new JLabel[Main.ATTACK_NAMES.length];
		for (int i = 0; i < Main.ATTACK_NAMES.length; i++) {
			allLabels[i] = new JLabel(Main.ATTACK_NAMES[i]);
			allLabels[i].setHorizontalAlignment(JLabel.CENTER);
			allLabels[i].setBorder(BorderFactory.createMatteBorder(0, 0, i == (Main.ATTACK_NAMES.length-1)? 0 : 1, 0, Main.BORDER_COLOR));
			leftPanel.add(allLabels[i]);
		}
		
		return new LeftPanel(leftPanel, allLabels);
	}
	
	private RightPanel createRightSide() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBorder(BorderFactory.createLineBorder(Main.BORDER_COLOR));
		
		JLabel title = new JLabel("Statistics");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Main.BORDER_COLOR), BorderFactory.createEmptyBorder(8, 0, 20, 0)));
		rightPanel.add(title, BorderLayout.NORTH);
		
		JLabel[] attackLabels = new JLabel[Main.ATTACK_NAMES.length];
		JLabel[] outcomeLabels = new JLabel[Main.ATTACK_NAMES.length];
		
		Container temp = new Container();
		temp.setVisible(true);
		temp.setLayout(new BoxLayout(temp, BoxLayout.PAGE_AXIS));
		for (int i = 0; i < Main.ATTACK_NAMES.length; i++) {
			attackLabels[i] = new JLabel(Main.ATTACK_NAMES[i]);
			outcomeLabels[i] = new JLabel();
			
			temp.add(attackLabels[i]);
			temp.add(outcomeLabels[i]);
		}
		rightPanel.add(temp, BorderLayout.CENTER);
		
		return new RightPanel(rightPanel, outcomeLabels);
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