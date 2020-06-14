package types;

public abstract class AttackAPI {

	
	int cycleMAX; // set a reasonable cycle limit on your algorithm
	
	String hashedPassword;	// user's hashed password
	String plainTextPassword; // user's plain text password
	boolean attackSuccess;
	int cycleCounter;
	int estimatedGuesses;
	
	
	
	
	public void run() {
		this.cycleCounter = algorithm();
		if(cycleCounter < cycleMAX) {
			this.attackSuccess = true;
		}
		this.estimatedGuesses = returnMetrics();
	}
	
	
	
	/** This method is where you implement
	 * the attack algorithm.
	 * @return number of cycles
	 */
	public abstract int algorithm();
	
	
	
	
	/** This method is where you will implement
	 * the ESTIMATED time calculation
	 * @return
	 */
	public abstract int calculateMetric();
	
	
	
	
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
