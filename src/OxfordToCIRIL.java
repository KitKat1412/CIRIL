import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OxfordToCIRIL {

	public static void main(String args[]) {
		File oxfordDictFile = new File("OxfordDictionary.txt");
		
		try {
			Scanner oxfordDictScanner = new Scanner(oxfordDictFile, "UTF-8");
			while(oxfordDictScanner.hasNextLine()) {
				String line = oxfordDictScanner.nextLine();
				String[] word = line.split("\\s+", 3);
				if(word.length >= 3 && word[0].indexOf("-") == -1 && word[1].indexOf(".") != -1) {
					if(word[0].indexOf("1") != -1) {
						word[0] = word[0].replace("1", "");
					}
					word[0] = word[0].toLowerCase();
					System.out.println(String.join(" ", word));
				}
				else {
				}
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
