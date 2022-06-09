package wordlemainpackage;


public class Source
{

	public static void main(String[] args)
	{
		
		wordleDictionary Dictionary = new wordleDictionary("dictionary.txt");
		
		new WordleGui(Dictionary);

	}

}
