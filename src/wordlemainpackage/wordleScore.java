package wordlemainpackage;

import java.awt.Color;
import java.io.*;
import java.util.*;

public class wordleScore {
	public
	
	String scoreFileLocation;
	File scoreFile;

	ArrayList<String> scoreListNames;
	ArrayList<String> scoreListScores;
	ArrayList<String> scoreListWords;
	int guessedCharactersAmount[];
	int askedCharactersAmount[];
	
	int playerAmount;
	double playerScores[];
	String playerNames[];
	String playerCorrectAnswers[];
	
	int progressState[][];
	//0 = unfound black
	//1 = foundInWord yellow
	//2 = foundPlace green
	
	public wordleScore(String fileLocation, int pAmount, String[] pNames, String[] pCorrectAnswers)
	{
		
		scoreFileLocation = fileLocation;
		playerAmount = pAmount;
		playerScores = new double[playerAmount];
		playerNames = pNames;
		playerCorrectAnswers = pCorrectAnswers;
		
		progressState = new int[playerAmount][5];
		
		scoreListNames = new ArrayList<String>(200);
		scoreListScores = new ArrayList<String>(200);
		scoreListWords = new ArrayList<String>(200);
		
        guessedCharactersAmount = new int[26];
        askedCharactersAmount= new int[26];
        
        readFile();
        
        for(int i=0;i<playerAmount;i++)
        {
        	
        	for(int j=0;j<5;j++)
        	{
        		askedCharactersAmount[(playerCorrectAnswers[i].charAt(j)-65)]++;
        	}
        	        	
        }
        
		writeToFile();
		readFile();
        
	}
	
	public void newGuess(int player, String guessedWord, int secondsPassed)
	{
		
		double timeMultiplier = ((double)(300 - secondsPassed))/(double)60;
		
		if(timeMultiplier < 0)
		{
			timeMultiplier = 0;
		}
				
		for(int i=0;i<5;i++)
		{
			if(playerCorrectAnswers[player].contains(guessedWord.charAt(i)+""))
			{
				if(playerCorrectAnswers[player].charAt(i) == guessedWord.charAt(i))
				{
					
					if(progressState[player][i]==1)
					{
						
						playerScores[player] += ((double)100)*timeMultiplier;
						
						progressState[player][i]=2;
					}
					else
					{
						if(progressState[player][i]==0)
						{
							
							playerScores[player] += ((double)200)*timeMultiplier;
							
							progressState[player][i]=2;
						}
					}
				}
				else
				{
					if(progressState[player][i]!=2 && progressState[player][i]!=1)
					{
						playerScores[player] += ((double)100)*timeMultiplier;
						progressState[player][i]=1;
						break;
					}
				}
			}
		}
		
    	for(int i=0;i<5;i++)
    	{
    		guessedCharactersAmount[guessedWord.charAt(i)-65]++;
    	}
    	
		if(playerCorrectAnswers[player].equals(guessedWord))
		{
			playerScores[player] += ((double)1000)*timeMultiplier;
		}
		
		writeToFile();
		readFile();
	}
	
	public void playerFinishedGame(int player)
	{
		addScoreToList(player);
		writeToFile();
		readFile();
	}
	
	public int getPlayerScore(int player)
	{
		return (int)playerScores[player];
	}
	
	public void addScoreToList(int player)
	{
		
        scoreListNames.add(playerNames[player]);
        scoreListWords.add(playerCorrectAnswers[player]);
        scoreListScores.add(String.format("%04d",((int)playerScores[player])));
        
        ArrayList<String> tempSorter = new ArrayList<String>(200);
        
        for(int i=0;i<scoreListNames.size();i++)
        {
        	tempSorter.add(scoreListNames.get(i)+"-"+scoreListScores.get(i)+"-"+scoreListWords.get(i));
        }
        
        Collections.sort(tempSorter, Comparator.comparing(item -> item.split("-")[1]));
        Collections.reverse(tempSorter);
        
        scoreListNames.clear();
        scoreListWords.clear();
        scoreListScores.clear();
        
        String parts[];
        		
        for(int i=0;i<tempSorter.size();i++)
        {
        	parts = tempSorter.get(i).split("-");
        	scoreListNames.add(parts[0]);
        	scoreListScores.add(parts[1]);
        	scoreListWords.add(parts[2]);
        }
        
        //this part sucks change it

	}
	
	public void readFile()
	{
		try
		{
            scoreFile = new File(scoreFileLocation);
            if (!scoreFile.exists())
            {
            	writeToFileDefault();
            }
            
            Scanner reader = new Scanner(scoreFile);
            
            String line = "";
            int alphabetCounter;
            String[] parts;
            
    		scoreListNames.clear();
    		scoreListScores.clear();
    		scoreListWords.clear();
            
            while (reader.hasNextLine())
            {
            	line = reader.nextLine();
	            if(line.equals("Scores:"))
	            {
	            	break;
	            }
            }
            
            while (reader.hasNextLine())
            {
            	line = reader.nextLine();
	            if(!line.equals(""))
	            {
	            	break;
	            }
            }
            
            if(!line.equals("Statistics:"))
            {
            	while(reader.hasNextLine())
            	{
            		
            		parts = line.split("-");
            		
            		scoreListNames.add(parts[0]);
            		scoreListScores.add(parts[1]);
            		scoreListWords.add(parts[2]);
            		
            		line = reader.nextLine();
            		if(line.equals(""))
            		{
            			break;
            		}
            	}
            	
                while (reader.hasNextLine())
                {
                	line = reader.nextLine();
    	            if(line.equals("Statistics:"))
    	            {
    	            	break;
    	            }
                }
            }
            
            while (reader.hasNextLine())
            {
            	line = reader.nextLine();
	            if(line.equals("Guessed:"))
	            {
	            	break;
	            }
            }
            
            while (reader.hasNextLine())
            {
            	line = reader.nextLine();
	            if(!line.equals(""))
	            {
	            	break;
	            }
            }
            
            alphabetCounter=0;
            
    		parts = line.split("-");
    		guessedCharactersAmount[alphabetCounter] = Integer.parseInt(parts[1]);
    		alphabetCounter++;
    		
        	while(reader.hasNextLine())
        	{
        		line = reader.nextLine();
        		if(line.equals(""))
        		{
        			break;
        		}
        		else
        		{
            		parts = line.split("-");
            		guessedCharactersAmount[alphabetCounter] = Integer.parseInt(parts[1]);
            		alphabetCounter++;
            	}
        	}
        	
            while (reader.hasNextLine())
            {
            	line = reader.nextLine();
	            if(line.equals("Word Asked:"))
	            {
	            	break;
	            }
            }
            
            while (reader.hasNextLine())
            {
            	line = reader.nextLine();
	            if(!line.equals(""))
	            {
	            	break;
	            }
            }
            
            alphabetCounter=0;
            
    		parts = line.split("-");
    		askedCharactersAmount[alphabetCounter] = Integer.parseInt(parts[1]);
    		alphabetCounter++;
    		
        	while(reader.hasNextLine())
        	{
        		line = reader.nextLine();
        		if(line.equals(""))
        		{
        			break;
        		}
        		else
        		{
            		parts = line.split("-");
            		askedCharactersAmount[alphabetCounter] = Integer.parseInt(parts[1]);
            		alphabetCounter++;
            	}
        	}
        	
            reader.close();   
        }
        catch(Exception e1)
        //SecurityException
        {
    		System.out.println("ERROR:");
            e1.printStackTrace();
        }
	}
	
	public void writeToFile()
	{
    	try
    	{
		FileWriter Writer = new FileWriter(scoreFile, false);
		
		Writer.write("Scores:\r\n"
				+ "\r\n"
				+ "");
		
		for(int i=0;i<scoreListNames.size();i++)
		{
			Writer.write(scoreListNames.get(i) + "-" + scoreListScores.get(i) + "-" + scoreListWords.get(i) + "\r\n");
		}
		
		Writer.write("\r\n"
				+ "Statistics:\r\n"
				+ "\r\n"
				+ "Guessed:\r\n"
				+ "\r\n"
				+ "");
		
		for(int i=0;i<26;i++)
		{
			Writer.write( (char)(65+i) + "-" + guessedCharactersAmount[i] + "\r\n");
		}
		
		Writer.write("\r\n"
				+ "Word Asked:\r\n"
				+ "\r\n"
				+ "");
		
		for(int i=0;i<26;i++)
		{
			Writer.write( (char)(65+i) + "-" + askedCharactersAmount[i] + "\r\n");
		}
		
		Writer.close();
    	}
    	catch (Exception e1)
    	//IOException
        {
    		System.out.println("ERROR:");
            e1.printStackTrace();
        }
	}
	
	public void writeToFileDefault()
	{
    	try
    	{
    		scoreFile.createNewFile();
            FileWriter Writer = new FileWriter(scoreFile);
            Writer.write("Scores:\r\n"
            		+ "\r\n"
            		+ "Statistics:\r\n"
            		+ "\r\n"
            		+ "Guessed:\r\n"
            		+ "\r\n"
            		+ "A-0\r\n"
            		+ "B-0\r\n"
            		+ "C-0\r\n"
            		+ "D-0\r\n"
            		+ "E-0\r\n"
            		+ "F-0\r\n"
            		+ "G-0\r\n"
            		+ "H-0\r\n"
            		+ "I-0\r\n"
            		+ "J-0\r\n"
            		+ "K-0\r\n"
            		+ "L-0\r\n"
            		+ "M-0\r\n"
            		+ "N-0\r\n"
            		+ "O-0\r\n"
            		+ "P-0\r\n"
            		+ "Q-0\r\n"
            		+ "R-0\r\n"
            		+ "S-0\r\n"
            		+ "T-0\r\n"
            		+ "U-0\r\n"
            		+ "V-0\r\n"
            		+ "W-0\r\n"
            		+ "X-0\r\n"
            		+ "Y-0\r\n"
            		+ "Z-0\r\n"
            		+ "\r\n"
            		+ "Word Asked:\r\n"
            		+ "\r\n"
            		+ "A-0\r\n"
            		+ "B-0\r\n"
            		+ "C-0\r\n"
            		+ "D-0\r\n"
            		+ "E-0\r\n"
            		+ "F-0\r\n"
            		+ "G-0\r\n"
            		+ "H-0\r\n"
            		+ "I-0\r\n"
            		+ "J-0\r\n"
            		+ "K-0\r\n"
            		+ "L-0\r\n"
            		+ "M-0\r\n"
            		+ "N-0\r\n"
            		+ "O-0\r\n"
            		+ "P-0\r\n"
            		+ "Q-0\r\n"
            		+ "R-0\r\n"
            		+ "S-0\r\n"
            		+ "T-0\r\n"
            		+ "U-0\r\n"
            		+ "V-0\r\n"
            		+ "W-0\r\n"
            		+ "X-0\r\n"
            		+ "Y-0\r\n"
            		+ "Z-0");
            Writer.close();
        }
    	catch (Exception e1)
    	//IOException
        {
    		System.out.println("ERROR:");
            e1.printStackTrace();
        }
	}
	
}
