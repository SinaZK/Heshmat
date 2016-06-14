package DataStore;

public class PlayerStatData 
{
	private long prime = 167 + 253 + 342;
	long money;
	
	public long getMoney()
	{
		return money / prime;
	}
	
	public void setMoney(long m)
	{
		money = m * prime;
	}
	
	public void addMoney(long m)
	{
		money += m * prime;
	}
	
}
