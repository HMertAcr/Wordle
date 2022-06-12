package wordlemainpackage;

import java.io.*;
import java.util.*;

public class WordleScore
{
	public
	
	String scoreFileLocation;
	File scoreFile;
	
	ArrayList<ScoreEntry> scoreList;
	
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
	
	public WordleScore(String fileLocation)
	{
		
		scoreFileLocation = fileLocation;
		scoreList = new ArrayList<ScoreEntry>(200);
		
        guessedCharactersAmount = new int[26];
        askedCharactersAmount= new int[26];
        
        readFile();
        Collections.sort(scoreList,Comparator.reverseOrder());
		writeToFile();
		readFile();
        
	}
	
	public void setNewPlayers(int pAmount, String[] pNames, String[] pCorrectAnswers)
	{
		
		playerAmount = pAmount;
		playerScores = new double[playerAmount];
		playerNames = pNames;
		playerCorrectAnswers = pCorrectAnswers;
		
		progressState = new int[playerAmount][5];
		        
        readFile();
        
        for(int i=0;i<playerAmount;i++)
        {
        	
        	for(int j=0;j<5;j++)
        	{
        		askedCharactersAmount[(playerCorrectAnswers[i].charAt(j)-65)]++;
        	}
        	        	
        }
        
        Collections.sort(scoreList,Comparator.reverseOrder());
		writeToFile();
		readFile();
        
	}
	
	public void newGuess(int player, String guessedWord, int tries, int secondsPassed)
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
						
						playerScores[player] += (((double)100)*timeMultiplier/(double)tries);
						
						progressState[player][i]=2;
					}
					else
					{
						if(progressState[player][i]==0)
						{
							
							playerScores[player] += (((double)200)*timeMultiplier/(double)tries);
							
							progressState[player][i]=2;
						}
					}
				}
				else
				{
					if(progressState[player][i]!=2 && progressState[player][i]!=1)
					{
						playerScores[player] += (((double)100)*timeMultiplier/(double)tries);
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
			playerScores[player] += (((double)1000)*timeMultiplier/(double)tries);
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
		scoreList.add(new ScoreEntry(playerNames[player], String.format("%04d",(int)playerScores[player]), playerCorrectAnswers[player]));
        Collections.sort(scoreList,Comparator.reverseOrder());
	}
	
	public ScoreEntry[] getHighscore()
	{
		ScoreEntry[] highscore = new ScoreEntry[10];
		
		if(scoreList.size()>10)
		{
			for(int i=0;i<10;i++)
			{
				highscore[i] = scoreList.get(i);
			}
		}
		else
		{
			for(int i=0;i<scoreList.size();i++)
			{
				highscore[i] = scoreList.get(i);
			}
			
			for(int i=scoreList.size();i<10;i++)
			{
				highscore[i] = new ScoreEntry("","","");
			}
			
		}

		return highscore;
	}

	public CharAmount[] getMostGuessed()
	{
		
		CharAmount mostGuessedList[] = new CharAmount[26];
	
		for(int i=0;i<26;i++)
		{
			mostGuessedList[i] = new CharAmount(((char)(i+65))+"",guessedCharactersAmount[i]);
		}
		
		Arrays.sort(mostGuessedList, Collections.reverseOrder());
		
		return mostGuessedList;
	}
	
	public CharAmount[] getMostAsked()
	{
		
		CharAmount mostAskedList[] = new CharAmount[26];
	
		for(int i=0;i<26;i++)
		{
			mostAskedList[i] = new CharAmount(((char)(i+65))+"",askedCharactersAmount[i]);
		}
		
		Arrays.sort(mostAskedList, Collections.reverseOrder());
		
		return mostAskedList;
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
            
            scoreList.clear();
            
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
            		
            		scoreList.add(new ScoreEntry(parts[0],parts[1],parts[2]));
            		
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
		
		for(int i=0;i<scoreList.size();i++)
		{
			Writer.write(scoreList.get(i).scoreListNames + "-" + scoreList.get(i).scoreListScores + "-" + scoreList.get(i).scoreListWords + "\r\n");
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
