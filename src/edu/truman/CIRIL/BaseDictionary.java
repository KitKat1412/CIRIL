package edu.truman.CIRIL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

final public class BaseDictionary {
	
	private static HashMap<String, String> definitionMap;

	public BaseDictionary(File dictionaryFile) throws FileNotFoundException {
		buildDefinitionMap(new Scanner(dictionaryFile, "UTF-8"));
	}
	
	private void buildDefinitionMap(Scanner dictScanner) {
		definitionMap = new HashMap<String, String>();
		while(dictScanner.hasNextLine()) {
			String line = dictScanner.nextLine();
			String word[] = line.split("\\s+", 2);
			definitionMap.put(word[0], word[1]);
		}
	}
	
	/**
	 * @param word Word
	 * @return true if word
	 */
	public boolean isWord(Word word) {
		return definitionMap.containsKey(word.getWord().toLowerCase());
	}
	
	/**
	 * @param word Word
	 * @return word with definition
	 */
	
	public Word define(Word word) {
		if(isWord(word)) {
			return word.setDefinition(definitionMap.get(word.getWord()));
		}
		else {
			return word;
		}
	}
}
