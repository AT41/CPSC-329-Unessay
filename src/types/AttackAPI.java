package types;

import java.math.BigInteger;
import java.util.List;

import javax.swing.SwingWorker;

import gui.AppView.AttackStatus;
import gui.AppView.AttackType;

public abstract class AttackAPI extends SwingWorker {

	
	protected final static int cycleMAX = 10000; // set a reasonable cycle limit on your algorithm
	
	protected String password; 	// hashed or plain text depending on algorithm
	
	protected boolean attackSuccess;  // set this to true if cycleCounter < cycleMax 
	protected int cycleCounter;
	BigInteger estimatedGuesses = BigInteger.ZERO;
	protected AppModel model;
	protected AttackType attackType;
	
	
	
	/*public void runAlgorithms() {
		this.estimatedGuesses = calculateMetric();
	}*/
	
	/** This method is where you will try and break the password.
	 * @return number of guesses.
	 */
	protected abstract BigInteger calculateMetric();

	@Override
	protected Object doInBackground() throws Exception {
		this.model.callUpdateConsole("Starting " + this.attackType.toString() +" Attack");
		this.estimatedGuesses = calculateMetric();
		this.model.finishedAttackEvent(this.attackType, attackSuccess ? AttackStatus.POSSIBLE : AttackStatus.IMPOSSIBLE);
		this.model.updateAttackGuesses(this.attackType, estimatedGuesses);
		return this.estimatedGuesses;
	}
	
	@Override
	protected void done() {
		this.model.whenFinished(attackType);
	}
}
