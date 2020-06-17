package types;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class RainbowTableAttack extends AttackAPI{
	
	private static final String hashFileLocation = "src/types/hashFile/hashes.txt";
	public String result;
	
	public RainbowTableAttack(String password) {
		this.password = password;
		this.attackSuccess = false;
		this.cycleCounter = 0;
		this.result = "";
	}
	
	
	@Override
	protected BigInteger calculateMetric() {
		// TODO Auto-generated method stub
		BigInteger estimate = BigInteger.ZERO;
		try{
			
			String line = "";
			File file = new File(hashFileLocation);
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()){
				estimate = estimate.add(BigInteger.ONE);
				line = reader.nextLine();
				String[] lineSplit = line.split(":");
				//System.out.println(lineSplit[0]);
				if(lineSplit[0].equals(password)) {
					this.attackSuccess = true;
					this.result = lineSplit[1];
					reader.close();
					return estimate;
				}
			}
			reader.close();
			}catch (FileNotFoundException e) {
				System.out.println("hashes.txt not found");
				e.printStackTrace();
				return BigInteger.ONE.negate();
			}
		return estimate;
	}

}
