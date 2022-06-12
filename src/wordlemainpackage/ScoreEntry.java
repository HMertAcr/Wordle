package wordlemainpackage;

public class ScoreEntry implements Comparable<ScoreEntry>
{
	String scoreListNames;
	String scoreListScores;
	String scoreListWords;
	
	public ScoreEntry()
	{
		scoreListNames = "";
		scoreListScores = "";
		scoreListWords = "";
	}
	
	public ScoreEntry(String Name, String Score, String Word)
	{
		scoreListNames = Name;
		scoreListScores = Score;
		scoreListWords = Word;
	}

	@Override
	public int compareTo(ScoreEntry se)
	{
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
