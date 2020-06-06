
public class AppModel {
	
	// Stores the hashed password, used to verify guesses
	String hashedPassword;
	
	// can be used to update console in view
	String currentGuess;
	
	// stores the total number of attempts, or we can also use it to store the current attempt number
	int guessCounter;
	
	// either used to display completion status or success status, I haven't decided yet
	boolean dictionaryAttackStatus;
	boolean bruteForceAttackStatus;
	boolean commonPasswordAttackStatus;
	boolean rainbowTableAttackStatus;
	
	// model stores instance of the attack so it can call it's functions
	BruteForceAttack bfAttack;
	
	// constructor
	public AppModel(){
		this.hashedPassword = "";
		this.currentGuess = "";
		this.guessCounter = 0;
		this.dictionaryAttackStatus = false;
		this.bruteForceAttackStatus = false;
		this.commonPasswordAttackStatus = false;
		this.rainbowTableAttackStatus = false;
		
		this.bfAttack = new BruteForceAttack();
	}
	
	public boolean getDictionaryAttackStatus() {
		return dictionaryAttackStatus;
	}

	public void setDictionaryAttackStatus(boolean dictionaryAttackStatus) {
		this.dictionaryAttackStatus = dictionaryAttackStatus;
	}

	public boolean getBruteForceAttackStatus() {
		return bruteForceAttackStatus;
	}

	public void setBruteForceAttackStatus(boolean bruteForceAttackStatus) {
		this.bruteForceAttackStatus = bruteForceAttackStatus;
	}

	public boolean getCommonPasswordAttackStatus() {
		return commonPasswordAttackStatus;
	}

	public void setCommonPasswordAttackStatus(boolean commonPasswordAttackStatus) {
		this.commonPasswordAttackStatus = commonPasswordAttackStatus;
	}

	public boolean getRainbowTableAttackStatus() {
		return rainbowTableAttackStatus;
	}

	public void setRainbowTableAttackStatus(boolean rainbowTableAttackStatus) {
		this.rainbowTableAttackStatus = rainbowTableAttackStatus;
	}

	public int getGuessCounter() {
		return this.guessCounter;
	}
	
	public void incrGuessCounter() {
		this.guessCounter++;
	}
	
	public String getCurrentGuess() {
		return this.currentGuess;
	}
	
	public void setCurrentGuess(String guess) {
		this.currentGuess = guess;
	}
	
	
	public String getHashedPassword() {
		return this.hashedPassword;
	}
	public void setHashedPassword(String plainTextPass) {
		this.hashedPassword = hashPassword(plainTextPass);
	}
	
	/**
	 * Hashing algorithm is to be implemented in the model
	 * @param password
	 * @return
	 */
	public String hashPassword(String password) {
		// TODO implement hashing algorithm
		return "";
	}
	
}
