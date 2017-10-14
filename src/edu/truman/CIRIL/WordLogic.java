package edu.truman.CIRIL;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Dictionary;
import java.util.Scanner;

import edu.truman.CIRIL.Word.WordType;

public class WordLogic
{
	String originalWord, baseWord;//user input
	ArrayList<Word> wordList;//output
	
	Scanner prefixes, suffixes;
	HashMap<String, String> prefixMap, suffixMap;
	BaseDictionary dict;
	
	String tempPrefix, tempSuffix;
	boolean prefixFlag, suffixFlag;
	int count;
	
	
	public WordLogic(String originalWord)
	{
		this.originalWord = originalWord;
		this.baseWord = this.originalWord;
	}
	
	private void buildHashMap(Scanner scanner, HashMap<String, String> hash) {
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String word[] = line.split("\\s+", 2);
			hash.put(word[0], word[1]);
		}
	}
	
	public void openEverything()
	{
		prefixMap = new HashMap<String, String>();
		suffixMap = new HashMap<String, String>();
		wordList = new ArrayList<Word>();
		try
		{
			File prefixFile = new File("CIRILprefixes.txt");
			prefixes = new Scanner(prefixFile);
			buildHashMap(prefixes, prefixMap);
			File suffixFile = new File("CIRILsuffixes.txt");
			suffixes = new Scanner(suffixFile);
			buildHashMap(suffixes, suffixMap);
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
		count = baseWord.length();
		prefixFlag = false;
		while(count >= 0)//traversing the file
		{
			tempPrefix = baseWord.substring(0, count);
			if(prefixMap.containsKey(tempPrefix))
			{
				Word newWord = new Word(tempPrefix, WordType.PREFIX, prefixMap.get(tempPrefix));
				wordList.add(newWord);
				baseWord = baseWord.substring(count, baseWord.length());
				prefixFlag = true;
				break;
			}
			else
			{
				count--;
			}
		}
		return prefixFlag;
	}
	
	private boolean findSuffixes()
	{
		count = 0;
		suffixFlag = false;
		while(count <= baseWord.length())//traversing the file
		{
			tempSuffix = baseWord.substring(count, baseWord.length());
			if(suffixMap.containsKey(tempSuffix))
			{
				Word newWord = new Word(tempSuffix, WordType.SUFFIX, suffixMap.get(tempSuffix));
				wordList.add(newWord);
				baseWord = baseWord.substring(0, count);
				suffixFlag = true;
				break;
			}
			else
			{
				count++;
			}
		}
		return suffixFlag;
	}
	
	private void fixWord()
	{
		Word tempWordSuffix;
		if(!wordList.isEmpty())
		{
			tempWordSuffix = wordList.get(wordList.size()-1);
			if(tempWordSuffix.getWordType() == WordType.SUFFIX)
			{
				String tempS = tempWordSuffix.getWord();
				String tempB = "";
				
				if(isVowel(tempS.charAt(0)))		//Fixes words where e is truncated when suffix is added eg. spacious => space
				{
					if((baseWord.charAt(baseWord.length()-1) != 'e') && (baseWord.charAt(baseWord.length()-1) != 'o') && (baseWord.charAt(baseWord.length()-1) != 'y'))
					{
						tempB = baseWord + 'e';
						if(dict.isWord(tempB))
						{
							baseWord = tempB;
						}
					}
				}
				if(baseWord.charAt(baseWord.length()-1) == 'i') //where y is changed to i when suffix is added eg. bountiful => bounty
				{
					tempB = baseWord.substring(0, baseWord.length()-1) + 'y';
				}
				if(baseWord.charAt(baseWord.length()-1) == 'y')  //where ie is changed to y when suffix is added eg. lying => lie
				{
					tempB = baseWord.substring(0, baseWord.length()-1) + "ie";	
				}
				if(baseWord.length() > 1)  //to prevent exceptions occuring when baseWord is only one letter
				{
						if(!isVowel(baseWord.charAt(baseWord.length()-1)) && (baseWord.charAt(baseWord.length()-1) == baseWord.charAt(baseWord.length()-2)))
					{															//Fixes words where an extra consonant is added before suffix eg. run => running
						tempB = baseWord.substring(0, baseWord.length()-1);
					}
				}
				if(dict.isWord(tempB))
				{
					baseWord = tempB;
				}
			}	
		}
	}
	
	private boolean isVowel(char c)
	{
		char[] vowels = new char[]{'a','e','i','o','u'};
		for(char v : vowels)
		{
			if (c == v)
				return true;
		}
		return false;
	}
	
	private void checkWord()
	{	
		//fixWord();
		if(!(dict.isWord(baseWord)) && wordList.size() > 0)
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
				fixWord();
				checkWord();
			}
		} 
	}
	public ArrayList<Word> assembleWordList()
	{
		openEverything();
		while(findPrefixes());
		while(findSuffixes());
		if (!(baseWord.length() == 0))
			fixWord();
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
