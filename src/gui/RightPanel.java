package gui;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

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
	
	private static final String attackGuessesText = "Total Guesses: ";
	private static final String additionalStatText = "Additional Stats: \n";
	
	public RightPanel(JPanel panel, JLabel[] allLabels, JLabel[] attackGuesses, JTextArea[] additionalStats) {
		this.outcomes = allLabels;
		this.panel = panel;
		this.attackGuesses = attackGuesses;
		this.additionalStats = additionalStats;
		
		this.resetView();
	}
	
	public void changeLabelStatus(int labelIndex, AttackStatus status) {
		outcomes[labelIndex].setText(status == AttackStatus.IMPOSSIBLE ? "NO" : "YES");
		outcomes[labelIndex].setForeground(status == AttackStatus.IMPOSSIBLE ? Color.red : Color.green);
	}
	
	public void setGuessesFor(AttackType type, BigInteger guesses) {
	    NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));
	    this.attackGuesses[type.ordinal()].setText(attackGuessesText + formatter.format(guesses).replaceAll("E", "e"));
	};
	
	public void setAdditionalStat(AttackType type, String info) {
		this.additionalStats[type.ordinal()].setText(additionalStatText + info);
	}
	
	public void resetView() {
		for (int i = 0; i < attackGuesses.length; i++) {
			this.attackGuesses[i].setText(attackGuessesText);
			this.additionalStats[i].setText(additionalStatText);
		}
	}
}