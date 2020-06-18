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
		this.bfAttack.run();
		finishedAttackEvent(AttackType.BRUTE_FORCE,this.bfAttack.attackSuccess ? AttackStatus.POSSIBLE : AttackStatus.IMPOSSIBLE);
		updateAttackGuesses(AttackType.BRUTE_FORCE,bfAttack.estimatedGuesses);
		//A regular computer would likely make about 100,000 guesses per second. 
		//https://www.expressvpn.com/blog/how-attackers-brute-force-password/#:~:text=How%20quickly%20an%20attacker%20can,about%20100%2C000%20guesses%20per%20second.
		BigInteger speed = BigInteger.valueOf(100000);
		BigInteger time = speed.divide(this.bfAttack.estimatedGuesses);
		updateAdditionalComments(AttackType.BRUTE_FORCE, "It would take approximately " + time.toString() + "seconds \nto crack this password given a 100000 guesses/sec \nbrute-force attack");
		//callUpdateConsole("Estimated Number of guesses for bruteforce: " + this.bfAttack.estimatedGuesses.toString());
		//callUpdateConsole("TESTING TESTING 123");
		
		this.cpAttack = new CommonPasswordsAttack(plainTextPassword,this);
		this.cpAttack.run();
		finishedAttackEvent(AttackType.COMMON_PASSWORDS, this.cpAttack.attackSuccess ? AttackStatus.POSSIBLE : AttackStatus.IMPOSSIBLE);
		
		this.rpAttack = new RainbowTableAttack(hashedPassword,this);
		this.rpAttack.run();
		finishedAttackEvent(AttackType.RAINBOW_TABLE,this.rpAttack.attackSuccess ? AttackStatus.POSSIBLE : AttackStatus.IMPOSSIBLE);
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
