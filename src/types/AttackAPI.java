package types;

import java.math.BigInteger;

public abstract class AttackAPI {

	
	protected final static int cycleMAX = 10000; // set a reasonable cycle limit on your algorithm
	
	protected String password; 	// hashed or plain text depending on algorithm
	
	protected boolean attackSuccess;  // set this to true if cycleCounter < cycleMax 
	protected int cycleCounter;
	BigInteger estimatedGuesses = BigInteger.ZERO;
	protected AppModel model;
	
	
	
	
	public void run() {
		this.estimatedGuesses = returnMetrics();
	}
	
	/** This method is where you will try and break the password.
	 * @return number of guesses.
	 */
	protected abstract BigInteger calculateMetric();
	
	
	
	
	/** This method will return either
	 * the number attempts taken
	 * or the estimated number of attempts
	 */
	public BigInteger returnMetrics() {
		return calculateMetric();
	}
	
	
	
	
}
