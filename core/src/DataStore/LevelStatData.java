package DataStore;

import Enums.Enums;

public class LevelStatData
{
	public static long prime = 123 + 351 + 279;

	public Enums.LOCKSTAT lockStat;

	long record;
	long star;//0-1-2-3

	public long getRecord()
	{
		return record / prime;
	}

	public void setRecord(long record)
	{
		if(record * prime > this.record)
			this.record = record * prime;
	}

	public long getStar()
	{
		return star / prime;
	}

	public void setStar(int star)
	{
		if(star * prime > this.star)
			this.star = star * prime;
	}

}
