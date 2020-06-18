package types;

import java.math.BigInteger;

import gui.AppView.AttackType;

public class BruteForceAttack extends AttackAPI{

	public BruteForceAttack(String password, AppModel model) {
		this.attackType = AttackType.BRUTE_FORCE;
		this.password = password;
		this.attackSuccess = true;
		this.cycleCounter = 0;
		this.model = model;
	}
	
	@Override
	public BigInteger calculateMetric() {
		// TODO Auto-generated method stub
		BigInteger estimate = BigInteger.valueOf(0);
		char[] passChars = this.password.toCharArray();
		int passLen = passChars.length;
		int[] asciiValues = new int[passLen];
		for(int x = 0; x < passLen; x++) {
			asciiValues[x] = passChars[x] - 32;
		}
		
		int numOfAllowedChars = 126 - 31; // from SPACE (32) to ~ on ascii table (126) ;
		//System.out.println(estimate);
		// num of guesses it took to reach current length of string
		for(int x = 1; x < passLen; x++) {
			BigInteger oldEstimate = estimate.add(BigInteger.ZERO);
			estimate = estimate.add(BigInteger.valueOf(numOfAllowedChars).pow(x));
			//System.out.printf("reaching: %s + (%d ^ %d)) = %s\n", oldEstimate.toString(), numOfAllowedChars, x, estimate.toString());
		}
		// num of iterations that have occured on current length of string
		for(int x = 0; x < passLen; x++) {
			BigInteger oldEstimate = estimate.add(BigInteger.ZERO);
			estimate = estimate.add(BigInteger.valueOf((long)asciiValues[(passLen-1)-x]).multiply(BigInteger.valueOf(numOfAllowedChars).pow(x)));
			//System.out.printf("current: %d + (%d * (%d ^ %d)) = %d\n", oldEstimate,asciiValues[(passLen-1)-x], numOfAllowedChars, x, estimate);
		}
		estimate = estimate.add(BigInteger.ONE);
		//System.out.println("final: " + estimate);
		return estimate;
	}

	@Override
	protected void done() {
		//A regular computer would likely make about 100,000 guesses per second. 
		//https://www.expressvpn.com/blog/how-attackers-brute-force-password/#:~:text=How%20quickly%20an%20attacker%20can,about%20100%2C000%20guesses%20per%20second.

		BigInteger speed = BigInteger.valueOf(100000);
		BigInteger time = speed.divide(this.estimatedGuesses);
		this.model.updateAdditionalComments(AttackType.BRUTE_FORCE, "It would take approximately " + time.toString() + "seconds \nto crack this password given a 100000 guesses/sec \nbrute-force attack");
	}


}
