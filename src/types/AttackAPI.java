package types;

public abstract class AttackAPI {

	/** 
	 * This method is used to update metrics 
	 *  such as the attack's respective Status,
	 *  currentGuess, or guessCounter
	 */
	public abstract void updateMetrics();
	
	/** 
	 * This method is used to calculate number
	 *  of guess that this attack might take
	 *  to crack the password if it were
	 *  to try to run the algorithm on it.
	 */
	public abstract void calculateMetrics();
	
	/** 
	 * This method can be used to actually make.
	 * a guess. We can implement this if we have
	 * time at the end of the project.
	 */
	public abstract void makeGuess();
	
	/** 
	 * This method would be used to check the guess,
	 * that was just made. 
	 */
	public abstract void checkGuess();
}
