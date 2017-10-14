package edu.truman.CIRIL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Scanner;

import edu.truman.CIRIL.Word.WordType;

import java.io.File;

public class WordLogic
{
	String originalWord, baseWord;//user input
	ArrayList<Word> wordList;//output
	
	Scanner prefixes, suffixes;
	BaseDictionary dict;
	
	String tempPrefix, tempSuffix;
	boolean prefixFlag, suffixFlag;
	int count;
	
	
	public WordLogic(String originalWord)
	{
		this.originalWord = originalWord;
	}
	
	
	public void openEverything()
	{
		try
		{
			File prefixFile = new File("CIRIL/CIRILprefixes.txt");
			prefixes = new Scanner(prefixFile);
			File suffixFile = new File("CIRIL/CIRILsuffixes.txt");
			suffixes = new Scanner(suffixFile);
			dict = new BaseDictionary(new File("CIRILDictionary.txt"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void closeEverything()
	{
		try
		{
			prefixes.close();
			suffixes.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private boolean findPrefixes()
	{
		count = 0;
		prefixFlag = false;
		while(prefixes.hasNext())//traversing the file
		{
			tempPrefix = baseWord.substring(0, count);
			if(tempPrefix.equals(prefixes.next()))
			{
				Word newWord = new Word(tempPrefix, WordType.PREFIX, prefixes.nextLine());
				wordList.add(newWord);
				baseWord = baseWord.substring(count, baseWord.length());
				prefixFlag = true;
				break;
			}
			else
			{
				prefixes.nextLine();
				count++;
			}
		}
		return prefixFlag;
	}
	
	private boolean findSuffixes()
	{
		count = baseWord.length()-1;
		suffixFlag = false;
		while(suffixes.hasNext())//traversing the file
		{
			tempSuffix = baseWord.substring(count, baseWord.length()-1);
			if(tempSuffix.equals(suffixes.next()))
			{
				Word newWord = new Word(tempSuffix, WordType.SUFFIX, suffixes.nextLine());
				wordList.add(newWord);
				baseWord = baseWord.substring(0, count);
				suffixFlag = true;
				break;
			}
			else
			{
				suffixes.nextLine();
				count--;
			}
		}
		return suffixFlag;
	}
	
	private void checkWord()
	{
		if(!(dict.isWord(baseWord)))
		{
			Word lastItem = wordList.get(wordList.size()-1);
			if(lastItem.getWordType() == WordType.PREFIX)
			{
				baseWord = lastItem.getWord() + baseWord;
				wordList.remove(wordList.size()-1);
				checkWord();
			}
			if(lastItem.getWordType() == WordType.SUFFIX)
			{
				baseWord = baseWord + lastItem.getWord();
				wordList.remove(wordList.size()-1);
				checkWord();
			}
		}
	}
	public ArrayList<Word> assembleWordList()
	{
		openEverything();
		while(findPrefixes());
		checkWord();
		while(findSuffixes());
		checkWord();
		wordList.add(dict.define(new Word(baseWord, WordType.BASE)));
		wordList.add(dict.define(new Word(originalWord, WordType.ORG)));
		closeEverything();
		return wordList;
		
	}
	public ArrayList<Word> getWords()
	{
		return wordList;
	}
	
	
	
	
	
	/** PSEUDOCODE
	 * 
	 * for(word in prefix list)//traversing the file
	 *  	if (substring baseWord equals prefix)
	 *  		add to prefix ArrayList
	 *  		baseWord = baseWord -substring baseWord
	 *  		prefixFlag = true
	 *  		break
	 *  	else
	 *  		substring++
	 *  		prefixFlag= false
	 *  
	 *  for(word in suffix list)
	 *  	if(substring(last) baseWord equals suffix)
	 *  		add to suffix ArrayList
	 *  		baseWord = baseWord - substring(last) baseWord
	 *  		suffixFlag = true
	 *  		break
	 *  	else
	 *  		substring--
	 *  		suffixFlag = false
	 *  
	 *  if(prefixFlag or suffixFlag)
	 *  	if(baseWord found in dictionary)
	 *  		for(word in prefix ArrayList)
	 *  			for (line in prefix File)
	 *  				if(word found in line string)
	 *  					output word
	 *  					output definition
	 *  					break
	 *  		output baseWord definition
	 *  		for(word in suffix ArrayList)
	 *  			for(line in suffix File)
	 *  				if(word found in line string)
	 *  					output word
	 *  					output definition
	 *  					break
	 *  else
	 *  	recursive call with baseWord
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  while(find prefixes until it returns false){}
	 * checkWord()
	 * while(find suffixes until it returns false){}
	 * checkWord()
	 *  Add dict.define(new Word(baseWord, WordType.BASE)) to ArrayList
	 *  Add dict.define(new Word(originalWord, WordType.ORG)) to ArrayList
	 *  
	 *  
	 */
	
}
