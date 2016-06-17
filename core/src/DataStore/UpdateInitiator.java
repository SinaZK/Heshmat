package DataStore;


import Enums.Enums;
import SceneManager.SceneManager;

public class UpdateInitiator
{
	SaveManager saveManager;
	
	public UpdateInitiator(SaveManager s)
	{
		saveManager = s;
	}
	
	//First Place V1.0
	public void updateFirstPlace()
	{
		GameStatData GSD = new GameStatData();
		GSD.numberOfAppRun = 1;
		GSD.Version = 1.0f;

		saveManager.saveDataValue(DataKeyStrings.GameStatData, GSD);
		
		saveManager.saveDataValue(DataKeyStrings.CarStatData[1], createCar(Enums.LOCKSTAT.UNLOCK));
		for(int i = 2;i <= SceneManager.CAR_MAX_NUM;i++)
			saveManager.saveDataValue(DataKeyStrings.CarStatData[i], createCar(Enums.LOCKSTAT.LOCK));

		saveManager.saveDataValue(DataKeyStrings.GunStatData[1], createGun(Enums.LOCKSTAT.UNLOCK));
		for(int i = 2;i <= SceneManager.GUN_MAX_NUM;i++)
			saveManager.saveDataValue(DataKeyStrings.GunStatData[i], createGun(Enums.LOCKSTAT.LOCK));

		saveManager.saveDataValue(DataKeyStrings.LVLPackStatData[1], createLevelPackage(Enums.LOCKSTAT.UNLOCK));
		for(int i = 2;i <= SceneManager.LVL_PACK_MAX_NUM;i++)
			saveManager.saveDataValue(DataKeyStrings.LVLPackStatData[i], createLevelPackage(Enums.LOCKSTAT.LOCK));

		for(int i = 1;i <= SceneManager.LVL_PACK_MAX_NUM;i++)
		{
			saveManager.saveDataValue(DataKeyStrings.getLevelFromPack(i, 1), createLVL(Enums.LOCKSTAT.UNLOCK));
			for(int j = 2;j <= SceneManager.LVL_MAX_NUM;j++)
				saveManager.saveDataValue(DataKeyStrings.getLevelFromPack(i, j), createLVL(Enums.LOCKSTAT.LOCK));
		}

		SettingStatData SSD = new SettingStatData();
		SSD.isMusicOn = true;
		SSD.isSoundOn = true;
		SSD.isVibrateOn = true;
		saveManager.saveDataValue(DataKeyStrings.SettingStatData, SSD);
		
		SelectorStatData selector = new SelectorStatData();
		selector.selectedCar = 1;
		selector.selectedGun = 1;
		selector.selectedLevelPack = 1;
		selector.selectedLevel = 1;
		saveManager.saveDataValue(DataKeyStrings.SelectorStatData, selector);
		
		PlayerStatData pp = new PlayerStatData();
		pp.setMoney(0);
		saveManager.saveDataValue(DataKeyStrings.PlayerStatData, pp);
	}

	public GunStatData createGun(Enums.LOCKSTAT lockStat)
	{
		GunStatData ret = new GunStatData();

		ret.lockStat = lockStat;

		return ret;
	}

	public LevelStatData createLVL(Enums.LOCKSTAT lockStat)
	{
		LevelStatData ret = new LevelStatData();
		
		ret.lockStat = lockStat;

		return ret;
	}

	public LevelPackageStatData createLevelPackage(Enums.LOCKSTAT lockStat)
	{
		LevelPackageStatData ret = new LevelPackageStatData();

		ret.lockStat = lockStat;

		return ret;
	}
	
	public CarStatData createCar(Enums.LOCKSTAT lockStat)
	{
		CarStatData ret = new CarStatData();

		for(int i = 0;i < 10;i++)
			for(int j = 0;j < 4;j++)
				ret.gunSlotLockStats[i][j] = Enums.LOCKSTAT.LOCK;
		
		ret.lockStat = lockStat;
		
		return ret;
	}
}
