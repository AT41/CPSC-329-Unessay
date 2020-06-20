package types;

import java.math.BigInteger;

import gui.AppView.AttackStatus;
import gui.AppView.AttackType;

public interface AttackListener {
	void attackComplete(AttackType type, AttackStatus status);
	void updateConsole(String message);
	void setAttackGuesses(AttackType type, BigInteger guesses);
	void setTotalGuesses(BigInteger guesses);
	void setAttackComments(AttackType type, String comments);
	public void finishedAlgorithms();
}
