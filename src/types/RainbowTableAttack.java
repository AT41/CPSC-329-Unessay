package types;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Scanner;

import gui.AppView.AttackType;

public class RainbowTableAttack extends AttackAPI{
	
	private static final String hashFileLocation = "src/types/hashFile/hashes.txt";
	public String result;
	private Hashtable<String,String> hashtable;
	
	public RainbowTableAttack(String password, AppModel model) {
		this.attackType = AttackType.RAINBOW_TABLE;
		this.password = password;
		this.attackSuccess = false;
		this.cycleCounter = 0;
		this.result = "";
		this.model = model;
		this.hashtable = constructHashTable();
	}
	
	
	private Hashtable<String,String> constructHashTable() {
		// TODO Auto-generated method stub
		Hashtable<String,String> table = new Hashtable<String,String>();
		try{
			String line = "";
			File file = new File(hashFileLocation);
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()){
				line = reader.nextLine();
				String[] lineSplit = line.split(":");
				table.put(lineSplit[0],lineSplit[1]);
			}	
			reader.close();
		}catch (FileNotFoundException e) {
			System.out.println("hashes.txt not found");
			e.printStackTrace();
		}
		return table;
	}


	@Override
	protected BigInteger calculateMetric() {
		// TODO Auto-generated method stub
		BigInteger estimate = BigInteger.ONE;
		String result = hashtable.get(this.password);
		if(result != null) {
			this.result = result;
			this.attackSuccess = true;
		}
		return estimate;
	}

}
