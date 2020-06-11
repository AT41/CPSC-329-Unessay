package types;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController {

	private AppView view;
	private AppModel model;
	
	
	/**
	 * Controller connects the view and model.
	 * Also acts as a caller of methods.
	 * @param view
	 * @param model
	 */
	public AppController(AppView view, AppModel model){
		this.view = view;
		this.model = model;
		
		/*
		 * the following line calls a method in the view
		 * which connects the pressing of the button to an ActionListener.
		 */
		//this.view.setMethodForButtonListenerInView(new RunButtonListener());
	}
	
	class RunButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO implement sequence of operations
			
			// step 1: hash the user's password
			model.setHashedPassword(view.getPlainTextPassword());
			
			// step 2: run algorithms
			model.runAlgorithms();		
		}
	}
	
}
