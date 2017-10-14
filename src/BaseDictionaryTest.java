import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.truman.CIRIL.BaseDictionary;
import edu.truman.CIRIL.Word;
import edu.truman.CIRIL.Word.WordType;

public class BaseDictionaryTest {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			BaseDictionary dict = new BaseDictionary(new File("CIRILDictionary.txt"));
			
			while(scanner.hasNextLine()) {
				String wordString = scanner.nextLine();
				System.out.println(dict.define(new Word(wordString, WordType.BASE)).getDefinition());
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
