package types;

import java.math.BigInteger;

public class BruteForceAttack extends AttackAPI{

	public BruteForceAttack(String password) {
		//this.hashedPassword = hashedPassword;
		//this.plainTextPassword = plainTextPassword;
		this.password = password;
		this.attackSuccess = true;
		this.cycleCounter = 0;
		this.estimatedGuesses = BigInteger.ZERO;
	}

	@Override
	public void algorithm() {
		// TODO Auto-generated method stub
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




}
