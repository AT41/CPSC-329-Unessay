package gui;

import gui.Main.AttackType;

public interface MainGUI {
	/**
	 * Sets the status displayed on the rightmost side for the given attack type
	 * @param attackType
	 * @param text
	 */
	public void setStatusFor(AttackType attackType, String text);
	
	/**
	 * Sets number of tries for the attack type
	 * @param attackType
	 * @param tries
	 */
	public void setTriesFor(AttackType attackType, int tries);
}
