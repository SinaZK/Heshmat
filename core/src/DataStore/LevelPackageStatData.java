package DataStore;

import Enums.Enums;

public class LevelPackageStatData
{
	public static long prime = 123 + 351 + 279;

	public Enums.LOCKSTAT lockStat;

	long record;
	long star;
	public boolean [] isLevelLocked;
	long endlessStartingWave;

	public long getEndlessStartingWave()
	{
		return endlessStartingWave;
	}

	public long addToEndlessStartingWave()
	{
		endlessStartingWave++;

		return endlessStartingWave;
	}

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
