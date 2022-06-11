package wordlemainpackage;

public class CharAmount implements Comparable<CharAmount>
{
	String character;
	int amount;
	
	public CharAmount()
	{
		character = "";
		amount = 0;
	}
	
	public CharAmount(String character, int amount)
	{
		this.character = character;
		this.amount = amount;
	}

	@Override
	public int compareTo(CharAmount se) {
		if(this.amount > se.amount) 
		{
			return 1;
		}
		if(this.amount < se.amount) 
		{
			return -1;
		}
		if(this.amount == se.amount) 
		{
			return 0;
		}
		return 0;
	}
}