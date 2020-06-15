package types;

public abstract class AttackAPI {

	
	final static int cycleMAX = 10000; // set a reasonable cycle limit on your algorithm
	
	//String hashedPassword;	// user's hashed password
	//String plainTextPassword; // user's plain text password
	
	String password; 	// hashed or plain text depending on algorithm
	
	boolean attackSuccess;  // set this to true if cycleCounter < cycleMax 
	int cycleCounter;
	int estimatedGuesses;
	
	
	
	
	public void run() {
		//algorithm();
		this.estimatedGuesses = returnMetrics();
	}
	
	
	
	/** This method is where you implement
	 * the attack algorithm. Remember to set attackSuccess
	 */
	protected abstract void algorithm();
	
	
	
	
	/** This method is where you will implement
	 * the estimated number of cycles calculation
	 * @return
	 */
	protected abstract int calculateMetric();
	
	
	
	
	/** This method will return either
	 * the number attempts taken
	 * or the estimated number of attempts
	 */
	public int returnMetrics() {
		if(attackSuccess) {
			return cycleCounter;
		}
		return calculateMetric();
	}
	
	
	
	
}
