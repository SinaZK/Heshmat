package GameScene;

import BaseLevel.LevelPackage;
import Countly.CountlyStrings;
import DataStore.SelectorStatData;
import Enums.Enums;
import Misc.Log;
import heshmat.MainActivity;

/**
 * Created by sinazk on 7/10/16.
 * 19:39
 */
public class StarManager
{
	MainActivity activity;

	SelectorStatData selectorStatData;
	LevelPackage levelPackage;

	public StarManager(MainActivity activity)
	{
		this.activity = activity;
	}

	public void create()
	{
		this.selectorStatData = activity.selectorStatData;
		this.levelPackage = new LevelPackage(activity);
		levelPackage.load("gfx/lvl/pack" + selectorStatData.selectedLevelPack + "/level.pckg");

	}

	public int getStarNum()
	{
		int packageNum = selectorStatData.selectedLevelPack;
		int star = 0;

		for(int i = 1;i <= levelPackage.numberOfLevels;i++)
			star += activity.levelStatDatas.get(i).getStar();

		return star;
	}

	public void completeNormalLevel(int stars)
	{
		activity.levelStatDatas.get(selectorStatData.selectedLevel).setStar(stars);

		if(unlockLevel(selectorStatData.selectedLevel + 1))
			Log.e("Tag", "next level Unlocked");
	}

	public boolean unlockLevel(int levelID)
	{
		if(levelID > levelPackage.numberOfLevels)
			return  false;
		if(activity.levelStatDatas.get(levelID).lockStat == Enums.LOCKSTAT.UNLOCK)
			return false;

		activity.levelStatDatas.get(levelID).lockStat = Enums.LOCKSTAT.UNLOCK;
		activity.levelStatDatas.get(levelID).setStar(0);

		activity.saveAllLevelDatas(levelPackage.numberOfLevels);

		activity.googleServices.Countly("U " + CountlyStrings.LevelString + " " + levelID);

		return true;
	}
}
