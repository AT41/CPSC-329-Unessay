package types;

import gui.Main.AttackStatus;
import gui.Main.AttackType;

public interface AttackListener {
	void attackComplete(AttackType type, AttackStatus status);
}
