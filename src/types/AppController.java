package types;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import gui.Main.AttackStatus;
import gui.Main.AttackType;
import gui.MainGUI;

public class AppController {
	class RunButtonListener implements ActionListener{
		private MainGUI view;
		RunButtonListener(MainGUI view) {
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			this.view.setButtonEnableOrDisable(false);
			System.out.println(((JTextField)e.getSource()).getText());
			// step 1: hash the user's password
			// model.setHashedPassword();
			
			// step 2: run algorithms
			model.runAlgorithms();
			//System.out.println(model.bfAttack.attackSuccess);
			//this.view.setStatusFor(AttackType.BRUTE_FORCE, AttackStatus.POSSIBLE);
		}
	}
	class AttackDoneListener implements AttackListener{
		private MainGUI view;
		AttackDoneListener(MainGUI view) {
			this.view = view;
		}
		@Override
		public void attackComplete(AttackType type, AttackStatus status) {
			// TODO Auto-generated method stub
			this.view.setStatusFor(type, status);
		}
		
	}
	private MainGUI view;
	private AppModel model;
	
	
	/**
	 * Controller connects the view and model.
	 * Also acts as a caller of methods.
	 * @param view
	 * @param model
	 */
	public AppController(MainGUI view, AppModel model){
		this.view = view;
		this.model = model;
		this.view.addListenerForButton(new RunButtonListener(view));
		this.model.addAttackListener(new AttackDoneListener(view));
		/*
		 * the following line calls a method in the view
		 * which connects the pressing of the button to an ActionListener.
		 */
		//this.view.setMethodForButtonListenerInView(new RunButtonListener());
	}
	
}
