package types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

import gui.AppView;
import gui.AppView.AttackType;

public class RainbowTableAttack extends AttackAPI{
	public static String hashName = "Rainbow Table Attack Result";
	private static final String hashFileLocation = "/types/hashFile/sorted.txt";
	public String result;
	private ArrayList<String[]> allHashes;
	
	public RainbowTableAttack(String password, AppModel model) {
		this.attackType = AttackType.RAINBOW_TABLE;
		this.password = password;
		this.attackSuccess = false;
		this.cycleCounter = 0;
		this.result = "";
		this.model = model;
	}
	
	
	private ArrayList<String[]> constructHashTable() {
		// TODO Auto-generated method stub
		ArrayList<String[]> allHashes = new ArrayList<String[]>();
		try{
			InputStream is = (RainbowTableAttack.class.getResourceAsStream(hashFileLocation));
			InputStreamReader ifs = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(ifs);
			
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] temp = new String[2];
				temp[0] = line.substring(0, 32);
				temp[1] = line.substring(33);
				allHashes.add(temp);
			}
			
			reader.close();
			ifs.close();
			is.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return allHashes;
	}


	protected String searchHash(String hash, int startIndex, int endIndex) {
		System.out.println(startIndex + ", " + endIndex);
		if (startIndex > endIndex) {
			return null;
		}
		int compare = (startIndex + endIndex) / 2;
		String compareHash = allHashes.get(compare)[0];
		System.out.println(compareHash);
		if (compareHash.compareTo(hash) > 0) {
			return searchHash(hash, startIndex, compare - 1);
		} else if (compareHash.compareTo(hash) < 0) {
			return searchHash(hash, compare + 1, endIndex);
		} else {
			return allHashes.get(compare)[1];
		}
	}
	
	@Override
	protected BigInteger calculateMetric() {
		// TODO Auto-generated method stub
		this.model.callUpdateConsole("Checking rainbow table for '" + this.password + "'...");
		BigInteger estimate = BigInteger.ONE.negate();
		if (allHashes == null) {
			allHashes = constructHashTable();
		}
		String result = this.searchHash(this.password, 0, allHashes.size() - 1);
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
