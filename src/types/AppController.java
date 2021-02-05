package types;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Hashtable;

import javax.swing.JTextField;

import gui.AppView.AttackStatus;
import gui.AppView.AttackType;
import gui.GUI;

public class AppController {
	
	
	class RunButtonListener implements ActionListener{
		private GUI view;
		RunButtonListener(GUI view) {
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			this.view.setButtonEnableOrDisable(false);

			model.resultInfo = new Hashtable<String, BigInteger>();
			model.setPlainTextPassword(((JTextField)e.getSource()).getText());
			model.setHashedPassword(((JTextField)e.getSource()).getText());
			// step 2: run algorithms
			model.runAlgorithms();
			
			//System.out.println(model.bfAttack.attackSuccess);
			//this.view.setStatusFor(AttackType.BRUTE_FORCE, AttackStatus.POSSIBLE);
		}
	}
	
	
	class AttackDoneListener implements AttackListener{
		private GUI view;
		
		AttackDoneListener(GUI view) {
			this.view = view;
		}
		@Override
		public void attackComplete(AttackType type, AttackStatus status) {
			// TODO Auto-generated method stub
			this.view.setStatusFor(type, status);
		}	
		@Override
		public void updateConsole(String message) {
			this.view.printToGUIConsole(message);
		}
		@Override
		public void setAttackGuesses(AttackType type, BigInteger guesses) {
			// TODO Auto-generated method stub
			this.view.setGuessesFor(type, guesses);
		}
		@Override
		public void setAttackComments(AttackType type, String comments) {
			// TODO Auto-generated method stub
			this.view.setAdditionalCommentsFor(type, comments);
		}
		
		@Override 
		public void finishedAlgorithms(Hashtable<String, BigInteger> allGuesses) {
			this.view.openFinalView(allGuesses);
		}
	}
	private GUI view;
	private AppModel model;
	
	
	/**
	 * Controller connects the view and model.
	 * Also acts as a caller of methods.
	 * @param view
	 * @param model
	 */
	public AppController(GUI view, AppModel model){
		this.view = view;
		this.model = model;
		javax.swing.SwingUtilities.invokeLater(() -> this.view.addListenerForButton(new RunButtonListener(view)));
		this.model.addAttackListener(new AttackDoneListener(view));
	}
}
