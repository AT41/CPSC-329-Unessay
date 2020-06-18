package types;

import java.math.BigInteger;

import gui.AppView.AttackType;

public class DictionaryAttack extends AttackAPI{

	private static final String dictionaryFileLocation = "src/types/dictionaryFile/words.txt";
	
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
		return null;
	}

}
