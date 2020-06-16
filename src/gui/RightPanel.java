package gui;

import java.awt.Color;
import java.math.BigInteger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import gui.AppView.AttackStatus;
import gui.AppView.AttackType;

class RightPanel {
	public JLabel[] outcomes;
	public JLabel[] attackGuesses;
	public JTextArea[] additionalStats;
	public JPanel panel;
	public JLabel overallGuesses;
	
	private static final String attackGuessesText = "Total Guesses: ";
	private static final String additionalStatText = "Additional Stats: \n";
	
	public RightPanel(JPanel panel, JLabel[] allLabels, JLabel[] attackGuesses, JTextArea[] additionalStats, JLabel overallGuesses) {
		this.outcomes = allLabels;
		this.panel = panel;
		this.overallGuesses = overallGuesses;
		this.attackGuesses = attackGuesses;
		this.additionalStats = additionalStats;
		
		for (int i = 0; i < attackGuesses.length; i++) {
			this.attackGuesses[i].setText(attackGuessesText);
			this.additionalStats[i].setText(additionalStatText);
		}
	}
	
	public void changeLabelStatus(int labelIndex, AttackStatus status) {
		outcomes[labelIndex].setText(status == AttackStatus.IMPOSSIBLE ? "NO" : "YES");
		outcomes[labelIndex].setForeground(status == AttackStatus.IMPOSSIBLE ? Color.red : Color.green);
	}
	
	public void setTotalGuesses(BigInteger totalGuesses) {
		this.overallGuesses.setText(totalGuesses.toString());
	}
	
	// If digits are too large, consider scientific notation
	public void setGuessesFor(AttackType type, BigInteger guesses) {
		this.attackGuesses[type.ordinal()].setText(attackGuessesText + guesses.toString());
	};
	
	public void setAdditionalStat(AttackType type, String info) {
		this.additionalStats[type.ordinal()].setText(additionalStatText + info);
	}
}