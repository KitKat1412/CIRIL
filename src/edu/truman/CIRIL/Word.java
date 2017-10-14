package edu.truman.CIRIL;
public class Word
{
	final private String word;
	final private String definition;
	public enum WordType {PREFIX, SUFFIX, BASE, ORG};
	final private WordType type;
	
	public Word(String word, WordType type, String definition)
	{
		this.word = word;
		this.type = type;
		this.definition = definition;
	}
	
	public Word(String word, WordType type)
	{
		this.word = word;
		this.type = type;
		this.definition = null;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public String getDefinition()
	{
		return definition;
	}
	
	public WordType getWordType()
	{
		return type;
	}
	
	public Word setWord(String word)
	{
		return new Word(word, type, definition);
	}
	
	public Word setDefinition(String definition) {
		return new Word(word, type, definition);
	}
	
	public Word setWordType(WordType type) {
		return new Word(word, type, definition);
	}
}
