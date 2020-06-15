package types;

import gui.AppView.AttackStatus;
import gui.AppView.AttackType;

public interface AttackListener {
	void attackComplete(AttackType type, AttackStatus status);
	void updateConsole(String message);
}
