package wordlemainpackage;

public class scoreEntry implements Comparable<scoreEntry>
{
	String scoreListNames;
	String scoreListScores;
	String scoreListWords;
	
	public scoreEntry()
	{
		scoreListNames = "";
		scoreListScores = "";
		scoreListWords = "";
	}
	
	public scoreEntry(String Name, String Score, String Word)
	{
		scoreListNames = Name;
		scoreListScores = Score;
		scoreListWords = Word;
	}

	@Override
	public int compareTo(scoreEntry se) {
		if(Integer.parseInt(this.scoreListScores)>Integer.parseInt(se.scoreListScores)) 
		{
			return 1;
		}
		if(Integer.parseInt(this.scoreListScores)<Integer.parseInt(se.scoreListScores)) 
		{
			return -1;
		}
		if(Integer.parseInt(this.scoreListScores)==Integer.parseInt(se.scoreListScores)) 
		{
			return 0;
		}
		return 0;
	}
}
