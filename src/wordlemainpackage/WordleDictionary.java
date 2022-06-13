package wordlemainpackage;

import java.io.*;
import java.util.*;

public class WordleDictionary
{
	
	public
	ArrayList<String> Dictionary;
	boolean dictionaryFound;
	
	public WordleDictionary(String fileLocation)
	{
	    try
	    {
            File file = new File(fileLocation);
            Scanner reader = new Scanner(file);
            
            Dictionary = new ArrayList<String>(1000);
            
            while (reader.hasNextLine())
            {
	            String line = reader.nextLine();
	            Dictionary.add(line.toUpperCase());
	        }
            reader.close();

			dictionaryFound = true;

	    }
        catch (FileNotFoundException e)
	    {
			dictionaryFound = false;
	    }
    }
	
	public boolean contains(String word)
	{
		return Dictionary.contains(word);
	}
	
	public String random()
	{
		int index = (int)(Math.random() * Dictionary.size());
		return Dictionary.get(index);
	}
	
}
