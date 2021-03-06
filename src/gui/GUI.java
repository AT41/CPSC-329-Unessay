package gui;

import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Hashtable;

import gui.AppView.AttackStatus;
import gui.AppView.AttackType;

public interface GUI {
	public void setButtonEnableOrDisable(boolean shouldEnable);
	
	/**
	 * The ActionEvent will return a reference to the JTextField which contains the password
	 * @param returnsPlaintextPassword
	 */
	public void addListenerForButton(ActionListener returnsPlaintextPassword);
	
	/**
	 * Sets the status displayed on the rightmost side for the given attack type
	 * @param attackType
	 * @param text
	 */
	public void setStatusFor(AttackType type, AttackStatus status);
	
	public void setGuessesFor(AttackType type, BigInteger guesses);
	
	public void setAdditionalCommentsFor(AttackType type, String comments);
	
	public void resetView();
	
	/**
	 * Prints to console in center
	 * @param text
	 */
	public void printToGUIConsole(String text);

	void openFinalView(Hashtable<String, BigInteger> allGuesses);
}
