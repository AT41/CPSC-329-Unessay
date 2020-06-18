package types;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

import gui.AppView.AttackType;

public class DictionaryAttack extends AttackAPI{

	private static final String dictionaryFileLocation = "src/types/dictionaryFile/words.txt";
	public String result;
	
	public DictionaryAttack(String password, AppModel model) {
		this.attackType = AttackType.DICTIONARY;
		this.password = password;
		this.attackSuccess = false;
		this.cycleCounter = 0;
		this.model = model;
	}
	
	@Override
	protected BigInteger calculateMetric() {
		// TODO Auto-generated method stub
				BigInteger estimate = BigInteger.ZERO;
				try{
					File file = new File(dictionaryFileLocation);
					
					// Does not currently work for strings like "progressible" where "pro" is a word, "gressible" is a word, 
					// but "progress" is removed first, leaving "ible" which is not a word.
					while (true) {
						String lastTry = password;
						String originalpassword = password;
						String longestguess = "";
						int lastIndex = 0;
						String line = "";
						Scanner reader = new Scanner(file);
						
						while (reader.hasNextLine()) {
							if (password.equals(longestguess)) {
								break;
							}
							estimate = estimate.add(BigInteger.ONE);
							line = reader.nextLine();
							if (line.length() < 3) {
								continue;
							}
							
							// Start checking when the first character matches
							if (password.charAt(0) == (line.charAt(0))) {
								int i = 0;
								while (password.charAt(i) == (line.charAt(i))) {
									
									// Go character by character to get the longest matching word possible
									if (i < line.length() - 1) {
										i++;
										//System.out.println(i + " and " + line.length());
									} else {
										longestguess = line;
										//System.out.println("Password contains: " + longestguess);
										
										break;
									}
								}
							}
						}
						
						// Remove word if found
						password = password.substring(longestguess.length(), password.length());
						System.out.println("WORD FOUND: " + longestguess);
						System.out.println("Word removed, new PW: " + password);
						//System.out.println("Last try: " + lastTry);
						if (password.length() == 0) {
							this.attackSuccess = true;
							this.result = line;
							reader.close();
							return estimate;
						} else if (lastTry == password) {
							reader.close();
							this.attackSuccess = false;
							return estimate;
							
						}
					}
						
					
					} catch (FileNotFoundException e) {
						System.out.println("english.txt not found");
						e.printStackTrace();
						return BigInteger.ONE.negate();
					}
				
	}
}
