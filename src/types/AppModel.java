package types;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import gui.AppView.AttackStatus;
import gui.AppView.AttackType;

import commonPasswords.CommonPasswordsAttack;

public class AppModel {

	// Stores the hashed password, used to verify guesses
	String hashedPassword;

	// Stores the plain text password, used to calculate time metrics
	String plainTextPassword;

	// model stores instance of the attack so it can call it's functions
	BruteForceAttack bfAttack;
	CommonPasswordsAttack cpAttack;
	RainbowTableAttack rpAttack;
	DictionaryAttack dAttack;
	
	
	/**
	 * 0. BruteForce Result
	 * 1. BruteForce-Alpha Result
	 * 2. BruteForce-Lower-Alpha Result
	 * 3. BruteForce-Mix-Alpha Result
	 * 4. BruteForce-Numeric Result
	 * 5. BruteForce-Alpha-Numeric Result
	 * 6. BruteForce-Lower-Alpha-Numeric Result
	 * 7. BruteForce-Mix-Alpha-Numeric Result
	 * 8. CommonPasswords Result
	 * 9. Rainbow Table Result
	 * 10. Dictionary Result
	 */
	BigInteger[] results = new BigInteger[11];
	
	
	
	ArrayList<AttackListener> controllerListener;

	// constructor
	public AppModel(){
		this.hashedPassword = "";
		this.plainTextPassword = "";
		controllerListener = new ArrayList<AttackListener>();
	}

	public String getHashedPassword() {
		return this.hashedPassword;
	}

	public void setHashedPassword(String plainTextPass) {
		this.hashedPassword = hashPassword(plainTextPass);
	}
	public String getPlainTextPassword() {
		return this.plainTextPassword;
	}

	public void setPlainTextPassword(String plainTextPass) {
		this.plainTextPassword = plainTextPass;
	}

	public void addAttackListener(AttackListener updatesAttackStatus) {
		controllerListener.add(updatesAttackStatus);
	}

	/**
	 * Hashing algorithm is to be implemented in the model
	 * https://www.baeldung.com/java-md5 using MessageDigest class
	 * https://stackoverflow.com/questions/2817752/java-code-to-convert-byte-to-hexadecimal/50846880
	 * @param password
	 * @return md5Hash(password)
	 */
	public String hashPassword(String password) {
		String hash = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			//System.out.println(digest.toString());
		    StringBuilder sb = new StringBuilder();
		    for (byte b : digest) {
		        sb.append(String.format("%02X", b));
		    }
		    hash = sb.toString().toLowerCase();
		    //System.out.println(hash);
		    //System.out.println(sb.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hash;
	}

	public void runAlgorithms() {
		this.bfAttack = new BruteForceAttack(plainTextPassword,this);
		this.bfAttack.execute();

		this.cpAttack = new CommonPasswordsAttack(plainTextPassword,this);
		this.cpAttack.execute();
		
		this.rpAttack = new RainbowTableAttack(hashedPassword,this);
		this.rpAttack.execute();

		
		this.dAttack = new DictionaryAttack(plainTextPassword,this);
		this.dAttack.execute();
	}

	public void finishedAttackEvent(AttackType type, AttackStatus status) {
		for(AttackListener al : this.controllerListener) {
			al.attackComplete(type, status);
		}
	}

	public void callUpdateConsole(String message) {
		for(AttackListener al : this.controllerListener) {
			al.updateConsole(message);
		}
	}
	public void updateAdditionalComments(AttackType type, String comments) {
		for(AttackListener al : this.controllerListener) {
			al.setAttackComments(type, comments);
		}
	}
	public void updateAttackGuesses(AttackType type, BigInteger guesses) {
		for(AttackListener al : this.controllerListener) {
			al.setAttackGuesses(type, guesses);
		}
	}
	public void updateTotalGuesses(BigInteger guesses) {
		for(AttackListener al : this.controllerListener) {
			al.setTotalGuesses(guesses);
		}
	}



}
