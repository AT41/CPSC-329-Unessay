package types;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

import gui.AppView.AttackType;

public class RainbowTableAttack extends AttackAPI{
	public static String hashName = "Rainbow Table Attack Result";
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
		this.hashtable = constructHashTable();
		this.model.callUpdateConsole("Checking rainbow table for '" + this.password + "'...");
		BigInteger estimate = BigInteger.ONE.negate();
		String result = hashtable.get(this.password);
		if(result != null) {
			this.result = result;
			this.attackSuccess = true;
			estimate = estimate.negate();
			this.model.callUpdateConsole("Rainbow table searched, match found. Plaintext is '" + this.result +"'");
			
		}
		else {
			this.model.callUpdateConsole("Rainbow table searched, match not found.");
		}
		
		return estimate;
	}

	@Override
	protected void done() {
		this.model.resultInfo.put(this.hashName, this.estimatedGuesses);
		super.done();
	}
}
