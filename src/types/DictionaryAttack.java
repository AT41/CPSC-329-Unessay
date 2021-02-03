package types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gui.AppView;
import gui.AppView.AttackType;

public class DictionaryAttack extends AttackAPI{

	private static final String dictionaryFileLocation = "/types/dictionaryFile/words2.txt";
	public String result;
	public List<String> wordsFound = new ArrayList<String>();
	
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
				//BigInteger separators = BigInteger.ONE;
				int words = 0;
				BigInteger total = BigInteger.valueOf(216555);
				
				try {
					InputStream is = (RainbowTableAttack.class.getResourceAsStream(dictionaryFileLocation));
					InputStreamReader ifs = new InputStreamReader(is);
					BufferedReader reader = new BufferedReader(ifs);
					
					// Does not currently work for strings like "progressible" where "pro" is a word, "gressible" is a word, 
					// but "progress" is removed first, leaving "ible" which is not a word.
					while (true) {
						String lastTry = password;
						String originalpassword = password;
						String longestguess = "";
						String line = "";
						
						if (password.charAt(0) == '_' || password.charAt(0) == ' ') {
							password = password.substring(1, password.length());
							//separators = separators.multiply(BigInteger.TWO);
						}
						
						if (Character.isUpperCase(password.charAt(0))) {
							//separators = separators.multiply(BigInteger.TWO);
							//System.out.println("Uppercase: " + password);
							password = password.substring(0, 1).toLowerCase() + password.substring(1);
						}
						
						while ((line = reader.readLine()) != null) {
							if (password.equals(longestguess)) {
								break;
							}
							estimate = estimate.add(BigInteger.ONE);
							line = line.toLowerCase();
							
							
							if (line.length() < 1) {
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
						//System.out.println("WORD FOUND: " + longestguess);
						if (longestguess.length() > 0) {
							wordsFound.add(longestguess);
							
						}
						//System.out.println("Word removed, new PW: " + password);
						//System.out.println("Last try: " + lastTry);
						if (password.length() == 0) {
							estimate = estimate.multiply(BigInteger.ONE.add(BigInteger.ONE).multiply(BigInteger.ONE.add(BigInteger.ONE).multiply((total.pow(words)))));
							//estimate = estimate.multiply(separators);
							this.attackSuccess = true;
							this.result = line;
							reader.close();
							is.close();
							ifs.close();
							return estimate;
							
						} else if (lastTry == password) {
							reader.close();
							is.close();
							ifs.close();
							this.attackSuccess = false;
							return BigInteger.ONE.negate();
							
						} else {
							words++;
							estimate = BigInteger.ZERO;
							//estimate = estimate.multiply(words);
							
						}
						
					}
						
					
					} catch (FileNotFoundException e) {
						System.out.println("english.txt not found");
						e.printStackTrace();
						return BigInteger.ONE.negate();
					} catch (Exception e) {
						System.out.println(e.getMessage());
						return BigInteger.ONE.negate();
					}
				
	}


	@Override
	protected void done() {
		this.model.updateAdditionalComments(AttackType.DICTIONARY, "English dictionary words found: " + wordsFound.toString());
		this.model.resultInfo.put("Dictionary Attack", this.estimatedGuesses);
		super.done();
	}
}
