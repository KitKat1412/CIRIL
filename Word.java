
public class Word
{
	final private String word;
	final private String definition;
	final public enum WordType {PREFIX, SUFFIX, BASE};
	final private WordType type;
	
	Word(String word, WordType type, String definition)
	{
		this.word = word;
		this.type = type;
		this.definition = definition;
	}
	
	Word(String word, WordType type)
	{
		this.word = word;
		this.type = type;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public String getDefinition()
	{
		return definition;
	}
	
	public WordType getwordType()
	{
		return type;
	}
}
