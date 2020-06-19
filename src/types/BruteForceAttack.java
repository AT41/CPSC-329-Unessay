package types;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import gui.AppView.AttackType;

public class BruteForceAttack extends AttackAPI{

	private static enum Range{
		ALPHA,
		LOWER_ALPHA,
		MIX_ALPHA,
		NUMERIC,
		ALPHA_NUMERIC,
		LOWER_ALPHA_NUMERIC,
		MIX_ALPHA_NUMERIC
	}

	private BigInteger alpha;
	private BigInteger lower_alpha;
	private BigInteger mix_alpha;
	private BigInteger numeric;
	private BigInteger alpha_numeric;
	private BigInteger lower_alpha_numeric;
	private BigInteger mix_alpha_numeric;

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
		this.alpha = otherCalc(Range.ALPHA,(90-64));
		this.lower_alpha = otherCalc(Range.LOWER_ALPHA,(122-96));
		this.mix_alpha = otherCalc(Range.MIX_ALPHA,((90-64)+(122-96)));
		this.numeric = otherCalc(Range.NUMERIC,(57-47));
		this.alpha_numeric = otherCalc(Range.ALPHA_NUMERIC,((90-64)+(57-47)));
		this.lower_alpha_numeric = otherCalc(Range.LOWER_ALPHA_NUMERIC,((122-96)+(57-47)));
		this.mix_alpha_numeric = otherCalc(Range.MIX_ALPHA_NUMERIC,((122-96)+(57-47)+(90-64)));
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

	public BigInteger otherCalc(Range range, int numOfAllowedChars) {

		BigInteger estimate = BigInteger.valueOf(0);
		char[] passChars = this.password.toCharArray();
		if(!rangeCheck(range, passChars)) {
			estimate = BigInteger.ONE.negate();
			//System.out.println(range.toString() +" final: " + estimate);
			return estimate;
		}
		int passLen = passChars.length;
		int[] asciiValues = seriesGen(range, passChars);
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
		//System.out.println(range.toString() +" final: " + estimate);
		return estimate;
	}

	public boolean rangeCheck(Range range, char[] passChars) {
		boolean verify = true;
		int passLen = passChars.length;
		if(range == Range.ALPHA) {
			for(int x = 0; x < passLen; x++) {
				if(!(passChars[x] >=65 && passChars[x] <= 90)) {
					verify = false;
				}
			}
		}
		else if(range == Range.LOWER_ALPHA) {
			for(int x = 0; x < passLen; x++) {
				if(!(passChars[x] >=97 && passChars[x] <= 122)) {
					verify = false;
				}
			}
		}
		else if(range == Range.MIX_ALPHA) {
			for(int x = 0; x < passLen; x++) {
				if(!(passChars[x] >=65 && passChars[x] <= 90) && !(passChars[x] >=97 && passChars[x] <= 122)) {
					verify = false;
				}
			}
		}
		else if(range == Range.NUMERIC) {
			for(int x = 0; x < passLen; x++) {
				if(!(passChars[x] >=48 && passChars[x] <= 57)) {
					verify = false;
				}
			}
		}
		else if(range == Range.ALPHA_NUMERIC) {
			for(int x = 0; x < passLen; x++) {
				if(!(passChars[x] >=65 && passChars[x] <= 90) && !(passChars[x] >=48 && passChars[x] <= 57)) {
					verify = false;
				}
			}
		}
		else if(range == Range.LOWER_ALPHA_NUMERIC) {
			for(int x = 0; x < passLen; x++) {
				if(!(passChars[x] >=48 && passChars[x] <= 57) && !(passChars[x] >=97 && passChars[x] <= 122)) {
					verify = false;
				}
			}
		}
		else if(range == Range.MIX_ALPHA_NUMERIC) {
			for(int x = 0; x < passLen; x++) {
				if(!(passChars[x] >=48 && passChars[x] <= 57) && !(passChars[x] >=97 && passChars[x] <= 122) && !(passChars[x] >=65 && passChars[x] <= 90)) {
					verify = false;
				}
			}
		}
		return verify;
	}

	public int[] seriesGen(Range range, char[] passChars) {
		int passLen = passChars.length;
		int[] series = new int[passLen];
		if(range == Range.ALPHA) {
			for(int x = 0; x < passLen; x++) {
				series[x] = passChars[x] - 65;
			}
		}
		else if(range == Range.LOWER_ALPHA) {
			for(int x = 0; x < passLen; x++) {
				series[x] = passChars[x] - 97;
			}
		}
		else if(range == Range.MIX_ALPHA) {
			for(int x = 0; x < passLen; x++) {
				if(passChars[x] >=65 && passChars[x] <= 90) {
					series[x] = passChars[x] - 65;
				}
				else if(passChars[x] >=97 && passChars[x] <= 122) {
					series[x] = (passChars[x] - 97) + (90-65)+1;
				}
			}
		}
		else if(range == Range.NUMERIC) {
			for(int x = 0; x < passLen; x++) {
				series[x] = passChars[x] - 48;
			}
		}
		else if(range == Range.ALPHA_NUMERIC) {
			for(int x = 0; x < passLen; x++) {
				if(passChars[x] >=48 && passChars[x] <= 57) {
					series[x] = passChars[x] - 48;
				}
				else if(passChars[x] >=65 && passChars[x] <= 90) {
					series[x] = (passChars[x] - 65) + (57-48)+1;
				}
			}
		}
		else if(range == Range.LOWER_ALPHA_NUMERIC) {
			for(int x = 0; x < passLen; x++) {
				if(passChars[x] >=48 && passChars[x] <= 57) {
					series[x] = passChars[x] - 48;
				}
				else if(passChars[x] >=97 && passChars[x] <= 122) {
					series[x] = (passChars[x] - 97) + (57-48)+1;
				}
			}
		}
		else if(range == Range.MIX_ALPHA_NUMERIC) {
			for(int x = 0; x < passLen; x++) {
				if(passChars[x] >=48 && passChars[x] <= 57) {
					series[x] = passChars[x] - 48;
				}
				else if(passChars[x] >=65 && passChars[x] <= 90) {
					series[x] = (passChars[x] - 65) + (57-48)+1;
				}
				else if(passChars[x] >=97 && passChars[x] <= 122) {
					series[x] = (passChars[x] - 97) + (57-48)+1+(90-65)+1;
				}
			}
		}
		return series;
	}
	
	@Override
	protected void done() {
		NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));

		this.model.updateAdditionalComments(AttackType.BRUTE_FORCE, "Alpha(A-Z) \n: "+ (alpha.equals(BigInteger.ONE.negate())? "Failed" : formatter.format(alpha))
		+ "\n\nLowerAlpha(a-z): \n"+ (lower_alpha.equals(BigInteger.ONE.negate())? "Failed" : formatter.format(lower_alpha))
		+ "\n\nMixAlpha(A-Za-z): \n"+ (mix_alpha.equals(BigInteger.ONE.negate())? "Failed" : formatter.format(mix_alpha))
		+ "\n\nNumeric(0-9): \n"+ (numeric.equals(BigInteger.ONE.negate())? "Failed" : formatter.format(numeric))
		+ "\n\nAlphaNumeric(0-9A-Z): \n"+ (alpha_numeric.equals(BigInteger.ONE.negate())? "Failed" : formatter.format(alpha_numeric))
		+ "\n\nLowerAlphaNumeric(0-9a-z) \n: "+ (lower_alpha_numeric.equals(BigInteger.ONE.negate())? "Failed" : formatter.format(lower_alpha_numeric))
		+ "\n\nMixAlphaNumeric(0-9A-Za-z) \n: "+ (mix_alpha_numeric.equals(BigInteger.ONE.negate())? "Failed" : formatter.format( mix_alpha_numeric)));

		
		this.model.resultInfo.put("BruteForceAttack All chars Result", this.estimatedGuesses);
		this.model.resultInfo.put("BruteForceAttack alpha Result", this.alpha);
		this.model.resultInfo.put("BruteForceAttack lower_alpha Result", this.lower_alpha);
		this.model.resultInfo.put("BruteForceAttack mix_alpha Result", this.mix_alpha);
		this.model.resultInfo.put("BruteForceAttack numeric Result", this.numeric);
		this.model.resultInfo.put("BruteForceAttack alpha_numeric Result", this.alpha_numeric);
		this.model.resultInfo.put("BruteForceAttack lower_alpha_numeric Result", this.lower_alpha_numeric);
		this.model.resultInfo.put("BruteForceAttack mix_alpha_numeric Result", this.mix_alpha_numeric);
		
	}

}
