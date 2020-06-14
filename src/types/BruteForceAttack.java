package types;

public class BruteForceAttack extends AttackAPI{

	public BruteForceAttack(String password) {
		//this.hashedPassword = hashedPassword;
		//this.plainTextPassword = plainTextPassword;
		this.attackSuccess = true;;
		this.cycleCounter = 0;
		this.estimatedGuesses = 0;
	}

	@Override
	public void algorithm() {
		// TODO Auto-generated method stub
	}

	@Override
	public int calculateMetric() {
		// TODO Auto-generated method stub
		
		return 0;
	}




}
