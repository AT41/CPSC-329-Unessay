package commonPasswords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.List;

import gui.AppView.AttackType;
import types.AppModel;
import types.AttackAPI;
import types.RainbowTableAttack;

public class CommonPasswordsAttack extends AttackAPI {
	private static final String allPasswords = "/commonPasswords/passwordFiles";
	private final int numberOfPasswordFiles = 64;
	private int currentLevenschteinDistance = Integer.MAX_VALUE;
	private String levenGuess = "";
	
	public CommonPasswordsAttack(String plaintextPassword, AppModel model) {
		this.attackType = AttackType.COMMON_PASSWORDS;
		this.password = plaintextPassword;
		this.model = model;
	}
	
	@Override
	protected BigInteger calculateMetric() {
		try {
			BufferedReader br;
			String toCompare = "";
			BigInteger estimatedGuesses = BigInteger.ZERO;
			for (int i = 0; i < numberOfPasswordFiles; i++) {
				
				this.model.callUpdateConsole("Moved to new file: " + i);
				try {
					String fileName = Integer.toString(i) + ".txt";
					if (i < 10) {
						fileName = "0" + fileName;
					}
					
					InputStream is = (RainbowTableAttack.class.getResourceAsStream(allPasswords + "/" + fileName));
					InputStreamReader ifs = new InputStreamReader(is);
					br = new BufferedReader(ifs);
					
					toCompare = br.readLine();
					while (toCompare != null) {
						estimatedGuesses = estimatedGuesses.add(BigInteger.ONE);
						int levenDist = calculateLevenschtein(this.password, toCompare);
						if (currentLevenschteinDistance > levenDist) {
							this.currentLevenschteinDistance =  levenDist;
							this.levenGuess = toCompare;
						}
						
						if (toCompare.equals(this.password)) {
							this.attackSuccess = true;
							this.model.resultInfo.put("Common Passwords Attack", estimatedGuesses);
							return estimatedGuesses;
						}
						
						toCompare = br.readLine();
					}

					is.close();
					ifs.close();
					br.close();
				
				} catch (Exception e) {
					e.printStackTrace();
					return BigInteger.ONE.negate();
				}
			}
	
			this.attackSuccess = false;
			this.model.updateAdditionalComments(this.attackType, "Though the attack failed, the minimum Levenschtein distance out of all the passwords was " + this.currentLevenschteinDistance 
					+ ".\nThe closest match to the existing passwords is: " + this.levenGuess);
			this.model.resultInfo.put("Common Passwords Attack", BigInteger.ONE.negate());
			return estimatedGuesses;
		}	catch (Exception e) {
			System.out.println(e.getMessage());
			return BigInteger.ONE.negate();
		}
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

	@Override
	protected void done() {
		super.done();
	}
}
