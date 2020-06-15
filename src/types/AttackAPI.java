package types;

import java.math.BigInteger;

public abstract class AttackAPI {

	
	protected final static int cycleMAX = 10000; // set a reasonable cycle limit on your algorithm
	
	//String hashedPassword;	// user's hashed password
	//String plainTextPassword; // user's plain text password
	
	protected String password; 	// hashed or plain text depending on algorithm
	
	protected boolean attackSuccess;  // set this to true if cycleCounter < cycleMax 
	protected int cycleCounter;
	BigInteger estimatedGuesses;
	
	
	
	
	public void run() {
		//algorithm();
		this.estimatedGuesses = returnMetrics();
	}
	
	
	
	/** This method is where you implement
	 * the attack algorithm. Remember to set attackSuccess
	 * 
	 * NO LONGER BEING USED
	 * SINCE WE DONT HAVE TIME
	 * 
	 */
	protected abstract void algorithm();
	
	
	
	
	/** This method is where you will implement
	 * the estimated number of cycles calculation
	 * @return
	 */
	protected abstract BigInteger calculateMetric();
	
	
	
	
	/** This method will return either
	 * the number attempts taken
	 * or the estimated number of attempts
	 */
	public BigInteger returnMetrics() {
		
		/*
		 * This part of the algorithm used to determine if the the algorithm succeeded
		 * but since we are no longer implementing the actual attempt, this is no longer needed
		if(attackSuccess) {
			return cycleCounter;
		}*/
		
		
		return calculateMetric();
	}
	
	
	
	
}
