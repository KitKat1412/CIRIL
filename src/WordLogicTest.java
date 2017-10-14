import java.util.ArrayList;
import java.util.Scanner;

import edu.truman.CIRIL.Word;
import edu.truman.CIRIL.WordLogic;

public class WordLogicTest {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()) {
			String wordString = scanner.nextLine();
			WordLogic wordLogic = new WordLogic(wordString);
			ArrayList<Word> words = wordLogic.assembleWordList();
			for(Word word : words) {
				System.out.println(word.getWord() + ":" + word.getDefinition());
			}
		}
	}
	
}
