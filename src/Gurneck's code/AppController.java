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
	
	/**
	 * Should we implement this closer to view rather than controller?!?! 
	 * find out next week on dragonballz
	 * @author Gurneck
	 *
	 */
	class RunButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO implement sequence of operations
			
			// step 1: hash user's password and store it in the model.
			model.setHashedPassword(view.getPlainTextPassword());
			
			// step 2: run algorithm 1
			model.bfAttack.calculateMetrics();
			
			// step 3: update view with results from algorithm 1
			model.bfAttack.updateMetrics();
			
			// step 4: run other algorithms
					
		}
	}
	
}
