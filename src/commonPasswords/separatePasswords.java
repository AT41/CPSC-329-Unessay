package commonPasswords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The passwords file (https://github.com/danielmiessler/SecLists/blob/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt) 
 * contains an ordered list of the most popular passwords, but it is hard to find the subset lists where the passwords have the same rarity.
 * This file tries to separate the contents into a multitude of files where each file contains the ordered sublist of the next n most common passwords.
 * @author Anthony
 *
 */
public class separatePasswords {
	private static String fileExtension = "src/commonPasswords/";
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*FileWriter file = new FileWriter("test.txt");
		file.append('c');
		file.close();*/
		BufferedReader br = new BufferedReader(new FileReader(fileExtension + "passwords.txt"));
		int listNumber = 0;
		br.mark(1000);
		do {
			br.reset();
			createNextList(listNumber, br);
			listNumber++;
			br.mark(1000);
		} while (br.readLine() != null);
	}
	
	public static void createNextList(int listNumber, BufferedReader br) throws IOException {
		FileWriter file = new FileWriter(fileExtension + "passwordFiles/" + (listNumber < 10 ? "0" : "") + Integer.toString(listNumber) + ".txt");
		
		int tempEnteredLines = 0, minimumTempEnteredLines = 200, totalEnteredLines = 0, minimumTotalEnteredLines = 10000;
		while (tempEnteredLines < minimumTempEnteredLines && totalEnteredLines < minimumTotalEnteredLines) {
			tempEnteredLines = 0;
			String currentLine = br.readLine();
			int latestCharOrder = charOrder(currentLine);
			int nextLineCharOrder = latestCharOrder;
			do {
				file.write(currentLine + "\n");
				tempEnteredLines++;
				totalEnteredLines++;
				
				latestCharOrder = nextLineCharOrder;
				br.mark(1000);
				currentLine = br.readLine();
				if (currentLine == null) {
					file.close();
					return;
				}
				nextLineCharOrder = charOrder(currentLine);
			} while (latestCharOrder >= nextLineCharOrder);
			br.reset();
		}
		
		file.close();
	}

	public static char charOrder(String s) {
		char cTemp = 0;
		for (int i = 0; i < s.length(); i++) {
			cTemp = Character.toLowerCase(s.charAt(i));
			if (48 <= cTemp && cTemp <= 57 || 97 <= cTemp && cTemp <= 122) {
				break;
			} else {
				cTemp = 33;
			}
		}
		return cTemp;
	}

}
