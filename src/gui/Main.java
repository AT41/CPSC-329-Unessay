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
import javax.swing.JTextField;

//A GUI program is written as a subclass of Frame - the top-level container
//This subclass inherits all properties from Frame, e.g., title, icon, buttons, content-pane
public class Main extends Frame implements MainGUI {
	public static enum AttackType {
		BRUTE_FORCE,
		COMMON_PASSWORDS,
		RAINBOW_TABLE,
		DICTIONARY
	}
	
	private static final String[] ATTACK_NAMES = {"Brute Force Attack", "Common Passwords Attack", "Rainbow Table Attack", "Dictionary Attack"};
	private static final int[] WIDTH_HEIGHT = {700, 500};
	
	// Constructor to setup the GUI components
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
		
		createLeftSide(this);
		createRightSide(this);
		
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
		calculate.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		userInput.add(passwordLabel);
		userInput.add(passwordInput);
		userInput.add(calculate);
		
		return userInput;
	}
	
	private void createLeftSide(Frame main) {
		Container leftContainer = new Container();
		leftContainer.setLayout(new GridLayout(Main.ATTACK_NAMES.length, 1));
		leftContainer.setVisible(true);
		
		JLabel[] allLabels = new JLabel[Main.ATTACK_NAMES.length];
		for (int i = 0; i < Main.ATTACK_NAMES.length; i++) {
			allLabels[i] = new JLabel(Main.ATTACK_NAMES[i]);
			allLabels[i].setHorizontalAlignment(JLabel.CENTER);
			allLabels[i].setBorder(BorderFactory.createMatteBorder(1, 1, i == (Main.ATTACK_NAMES.length-1)? 1 : 0, 1, Color.black));
			leftContainer.add(allLabels[i]);
		}
		
		main.add(leftContainer, BorderLayout.WEST);
	}
	
	private void createRightSide(Frame main) {
		Container rightContainer = new Container();
		rightContainer.setLayout(new BoxLayout(rightContainer, BoxLayout.PAGE_AXIS));
		
		JLabel[] allLabels = new JLabel[Main.ATTACK_NAMES.length];
		for (int i = 0; i < Main.ATTACK_NAMES.length; i++) {
			allLabels[i] = new JLabel(Main.ATTACK_NAMES[i]);
			allLabels[i].setHorizontalAlignment(JLabel.CENTER);
			rightContainer.add(allLabels[i]);
		}
		
		main.add(rightContainer, BorderLayout.EAST);
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