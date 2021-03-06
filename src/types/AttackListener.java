package types;

import java.math.BigInteger;
import java.util.Hashtable;

import gui.AppView.AttackStatus;
import gui.AppView.AttackType;

public interface AttackListener {
	void attackComplete(AttackType type, AttackStatus status);
	void updateConsole(String message);
	void setAttackGuesses(AttackType type, BigInteger guesses);
	void setAttackComments(AttackType type, String comments);
	void finishedAlgorithms(Hashtable<String, BigInteger> allGuesses);
}
