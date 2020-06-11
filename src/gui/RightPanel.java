package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Main.AttackStatus;

class RightPanel {
	public JLabel[] outcomes;
	public JPanel panel;
	
	public RightPanel(JPanel panel, JLabel[] allLabels) {
		this.outcomes = allLabels;
		this.panel = panel;
	}
	
	public void changeLabelStatus(int labelIndex, AttackStatus status) {
		outcomes[labelIndex].setText(status == AttackStatus.IMPOSSIBLE ? "NO" : "YES");
	}
}