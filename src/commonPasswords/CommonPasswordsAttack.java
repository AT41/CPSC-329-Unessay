package commonPasswords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import types.AttackAPI;

public class CommonPasswordsAttack extends AttackAPI {
	private static final String allPasswords = "src/commonPasswords/passwordFiles";
	private int currentLevenschteinDistance = Integer.MAX_VALUE;
	private String levenGuess = "";
	
	public static void main(String[] args) {
		AttackAPI cpa = new CommonPasswordsAttack("fwefw");
	}
	
	public CommonPasswordsAttack(String plaintextPassword) {
		this.password = plaintextPassword;
	}
	@Override
	protected void algorithm() {
		File[] files = new File(allPasswords).listFiles();
		BufferedReader br;
		String toCompare = "";
		for (int i = 0; i < files.length; i++) {
			System.out.println("Moved to new file: " + i);
			try {
				br = new BufferedReader(new FileReader(files[i]));
			
			} catch (FileNotFoundException e) {
				System.err.println("File at index " + i + " wasn't found");
				e.printStackTrace();
				return;
			}
			try {
				toCompare = br.readLine();
				while (toCompare != null) {
					int levenDist = calculateLevenschtein(this.password, toCompare);
					if (currentLevenschteinDistance > levenDist) {
						this.currentLevenschteinDistance =  levenDist;
						this.levenGuess = toCompare;
					}
					
					if (toCompare.equals(this.password)) {
						System.out.println("Got a match!");
						System.out.println(this.currentLevenschteinDistance);
						System.out.println(this.levenGuess);
						this.attackSuccess = true;
						return;
					}
					
					toCompare = br.readLine();
				}
			} catch (IOException e) {
				System.err.println("Could not read line");
				e.printStackTrace();
				return;
			}
		}

		this.attackSuccess = false;
		System.out.println(this.currentLevenschteinDistance);
		System.out.println(this.levenGuess);
		
		return;
	}
	
	@Override
	protected BigInteger calculateMetric() {
		return BigInteger.valueOf(-1);
	}

	private int calculateLevenschtein(String s1, String s2) {
		if (s1 == null) {
			return s2.length();
		} else {
			int[][] memo = new int[s1.length()+1][s2.length()+1];
			for (int i = 0; i <= s1.length(); i++) {
				for (int j = 0; j <= s2.length(); j++) {
					memo[i][j] = -1;
				}
			}
			return levenschtein(s1, s2, 0, 0, memo);
		}
	}
	
	private int levenschtein(String s1, String s2, int length1, int length2, int[][] memo) {
		if (memo[length1][length2] != -1) {
			return memo[length1][length2];
		} else if (s1.length() == length1 || s2.length() == length2) {
			int ans = Math.max(s1.length() - length1, s2.length() - length2);
			memo[length1][length2] = ans;
			return ans;
		} else {
			int lev1 = (s1.charAt(length1) == s2.charAt(length2)) ? levenschtein(s1, s2, length1 + 1, length2 + 1, memo) : levenschtein(s1, s2, length1 + 1, length2 + 1, memo) + 1;
			int lev2 = levenschtein(s1, s2, length1 + 1, length2, memo) + 1;
			int lev3 = levenschtein(s1, s2, length1, length2 + 1, memo) + 1;
			int ans = Math.min(Math.min(lev1, lev2), lev3);
			memo[length1][length2] = ans;
			return ans;
		}
	}

}
