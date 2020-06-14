package types;

import java.util.ArrayList;

import gui.Main.AttackStatus;
import gui.Main.AttackType;

public class AppModel {
	
	// Stores the hashed password, used to verify guesses
	String hashedPassword;

	// Stores the plain text password, used to calculate time metrics
	String plainTextPassword;
	
	// model stores instance of the attack so it can call it's functions
	BruteForceAttack bfAttack;
	
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
	
	
	public void addAttackListener(AttackListener updatesAttackStatus) {
		controllerListener.add(updatesAttackStatus);
	}
	
	/**
	 * Hashing algorithm is to be implemented in the model
	 * @param password
	 * @return
	 */
	public String hashPassword(String password) {
		// TODO implement hashing algorithm
		return "";
	}
	
	public void runAlgorithms() {
		
		this.bfAttack = new BruteForceAttack(hashedPassword, plainTextPassword);
		this.bfAttack.run();
		finishedAttackEvent(AttackType.BRUTE_FORCE,this.bfAttack.attackSuccess ? AttackStatus.POSSIBLE : AttackStatus.IMPOSSIBLE);
		
		
		
		
	}
	
	public void finishedAttackEvent(AttackType type, AttackStatus status) {
		for(AttackListener al : this.controllerListener) {
			al.attackComplete(type, status);
		}
	}
	
	
	
}
