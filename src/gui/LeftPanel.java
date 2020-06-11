package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

class LeftPanel {
		public JLabel[] allLabels;
		public JPanel panel;
		
		public LeftPanel(JPanel panel, JLabel[] allLabels) {
			this.allLabels = allLabels;
			this.panel = panel;
		}
	}