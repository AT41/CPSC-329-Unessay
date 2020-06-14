package types;

public class BruteForceAttack extends AttackAPI{

	public BruteForceAttack(String hashedPassword, String plainTextPassword) {
		this.cycleMAX = 100000;
		this.hashedPassword = hashedPassword;
		this.plainTextPassword = plainTextPassword;
		this.attackSuccess = false;
		this.cycleCounter = 0;
		this.estimatedGuesses = 0;
	}

	@Override
	public int algorithm() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int calculateMetric() {
		// TODO Auto-generated method stub
		return 0;
	}




}
