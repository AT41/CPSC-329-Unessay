package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;  // Using Frame class in package java.awt
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import gui.AppView.AttackType;
//A GUI program is written as a subclass of Frame - the top-level container
//This subclass inherits all properties from Frame, e.g., title, icon, buttons, content-pane
public class AppView extends Frame implements GUI {
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
	private static final int[] WIDTH_HEIGHT = {710, 800};

	private RightPanel rightPanel;
	private LeftPanel leftPanel;
	private UserPanelComponents userPanelComponents;
	
	private Font customFont = null;


	@Override
	public void addListenerForButton(ActionListener returnsPlaintextPassword) {
		this.userPanelComponents.calculateButton.addActionListener(e -> {
			if (this.userPanelComponents.passwordInput.getText().equals("")) {
				this.userPanelComponents.console.printToConsole("Please enter a non-empty password");
			} else {
				this.resetView();
				returnsPlaintextPassword.actionPerformed(new ActionEvent(this.userPanelComponents.passwordInput, 0, ""));
			}
		});
	}
	@Override
	public void setStatusFor(AttackType type, AttackStatus status) {
		this.rightPanel.changeLabelStatus(type.ordinal(), status);
	}
	@Override
	public void printToGUIConsole(String text) {
		this.userPanelComponents.console.printToConsole(text);
	}
	@Override
	public void setButtonEnableOrDisable(boolean shouldEnable) {
		this.userPanelComponents.calculateButton.setEnabled(shouldEnable);
	}
	@Override
	public void setGuessesFor(AttackType type, BigInteger guesses) {
		this.rightPanel.setGuessesFor(type, guesses);
	}
	@Override
	public void setAdditionalCommentsFor(AttackType type, String comments) {
		this.rightPanel.setAdditionalStat(type, comments);
	}
	
	@Override
	public void resetView() {
		this.userPanelComponents.resetView();
		this.rightPanel.resetView();
	}
	
	@Override
	public void openFinalView(Hashtable<String, BigInteger> allGuesses) {
		this.setButtonEnableOrDisable(true);
		Frame test = new FinalView(allGuesses);
		test.setVisible(true);
	}
	
	public AppView() {
		this.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                dispose();  
            }
        });
		
		try {
			InputStream is = AppView.class.getResourceAsStream("/CONSOLA.TTF");
			this.customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(12f);
		} catch (Exception e1) {
			System.err.println("Could not load font");
			System.out.println(e1.getMessage());
		}
		
		javax.swing.SwingUtilities.invokeLater(() -> {initialize();});
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
		
		this.leftPanel = createLeftSide();
		mainPanel.add(this.leftPanel.panel, BorderLayout.WEST);
		this.rightPanel = createRightSide();
		mainPanel.add(rightPanel.panel, BorderLayout.EAST);
		
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
		JPanel buttonPanel = new JPanel();
		JButton calculateButton = new JButton("Calculate Strength");
		calculateButton.setDoubleBuffered(true); // Prevents button from flickering
		calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(calculateButton);
		buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		
		userInput.add(passwordLabel);
		userInput.add(passwordInput);
		userInput.add(buttonPanel);

		JTextArea console = new JTextArea(
			  "====================================\n"
			+ "           Enter a Password         \n"
			+ "====================================\n"
		);
		JScrollPane scroller = new JScrollPane(console);
		scroller.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
		scroller.setVisible(true);
		console.setEditable(false);
		console.setVisible(true);
		console.setFont(this.customFont);
		console.setBackground(Color.black);
		console.setForeground(Color.green);
		userInput.add(scroller);
		this.userPanelComponents = new UserPanelComponents(passwordInput, calculateButton, console, scroller);
		
		return userInput;
	}
	
	private LeftPanel createLeftSide() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.setVisible(true);
		
		JLabel[] allLabels = new JLabel[AppView.ATTACK_NAMES.length];
		JButton[] infoButtons = new JButton[AppView.ATTACK_NAMES.length];
		for (int i = 0; i < AppView.ATTACK_NAMES.length; i++) {
			JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEFT));
			temp.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(i == 0 ? 1 : 0, 1, i == AppView.ATTACK_NAMES.length - 1? 1 : 0, 1, Color.gray),
				BorderFactory.createBevelBorder(BevelBorder.RAISED)
			));
			temp.setVisible(true);
			temp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
			
			ImageIcon icon = new ImageIcon(AppView.class.getResource("/icon-smaller.png"));
			infoButtons[i] = new JButton(icon);
			temp.add(infoButtons[i]);

			allLabels[i] = new JLabel(AppView.ATTACK_NAMES[i]);
			allLabels[i].setHorizontalAlignment(JLabel.CENTER);
			temp.add(allLabels[i]);
			
			leftPanel.add(temp);
		}
		
		JTextArea attackInfo = new JTextArea("Click on the info boxes for more details!");
		attackInfo.setLineWrap(true);
		attackInfo.setWrapStyleWord(true);
		attackInfo.setOpaque(false);
		attackInfo.setEditable(false);
		
		JScrollPane scroller = new JScrollPane(attackInfo);
		scroller.setBorder(BorderFactory.createEmptyBorder(8, 2, 0, 2));
		leftPanel.add(scroller);
		
		return new LeftPanel(leftPanel, allLabels, infoButtons, attackInfo);
	}
	
	private RightPanel createRightSide() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		JLabel title = new JLabel("Statistics");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		rightPanel.add(title, BorderLayout.NORTH);
		
		JLabel[] attackLabels = new JLabel[AppView.ATTACK_NAMES.length];
		JLabel[] outcomeLabels = new JLabel[AppView.ATTACK_NAMES.length];
		JLabel[] attackGuesses = new JLabel[AppView.ATTACK_NAMES.length];
		JTextArea[] additionalStats = new JTextArea[AppView.ATTACK_NAMES.length];
		
		JPanel temp = new JPanel();
		temp.setVisible(true);
		temp.setLayout(new BoxLayout(temp, BoxLayout.PAGE_AXIS));
		for (int i = 0; i < AppView.ATTACK_NAMES.length; i++) {
			JPanel holdsOutcomesAndCountAndAdditional = new JPanel();
			holdsOutcomesAndCountAndAdditional.setLayout(new BoxLayout(holdsOutcomesAndCountAndAdditional, BoxLayout.PAGE_AXIS));
			holdsOutcomesAndCountAndAdditional.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			
			JPanel holdsAttackAndOutcome = new JPanel(new FlowLayout(FlowLayout.LEFT));
			holdsAttackAndOutcome.setVisible(true);
			attackLabels[i] = new JLabel(AppView.ATTACK_NAMES[i] + ":");
			outcomeLabels[i] = new JLabel();
			holdsAttackAndOutcome.add(attackLabels[i]);
			holdsAttackAndOutcome.add(outcomeLabels[i]);
			holdsOutcomesAndCountAndAdditional.add(holdsAttackAndOutcome);

			JPanel holdsGuess = new JPanel(new FlowLayout(FlowLayout.LEFT));
			holdsGuess.setVisible(true);
			JLabel attackGuess = new JLabel();
			attackGuesses[i] = attackGuess;
			attackGuess.setAlignmentX(Component.LEFT_ALIGNMENT);
			holdsGuess.add(attackGuess);
			holdsOutcomesAndCountAndAdditional.add(holdsGuess);
			
			JTextArea additionalStat = new JTextArea();
			additionalStat.setOpaque(false);
			additionalStats[i] = additionalStat;
			additionalStat.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
			additionalStat.setEditable(false);
			additionalStat.setLineWrap(true);
			additionalStat.setWrapStyleWord(true);
			holdsOutcomesAndCountAndAdditional.add(additionalStat);
			
			temp.add(holdsOutcomesAndCountAndAdditional);
		}
		rightPanel.add(temp, BorderLayout.CENTER);
		
		return new RightPanel(rightPanel, outcomeLabels, attackGuesses, additionalStats);
	}
}