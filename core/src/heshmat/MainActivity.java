package heshmat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.compression.lzma.Base;

import java.util.ArrayList;

import javax.xml.crypto.Data;

import Audio.AudioManager;
import BaseCar.BaseCar;
import BaseLevel.LevelPackage;
import Cars.Train;
import DataStore.CarStatData;
import DataStore.DataKeyStrings;
import DataStore.GameStatData;
import DataStore.GunStatData;
import DataStore.LevelPackageStatData;
import DataStore.LevelStatData;
import DataStore.PlayerStatData;
import DataStore.SaveManager;
import DataStore.SelectorStatData;
import DataStore.SettingStatData;
import GoogleServices.IGoogleServices;
import Misc.Log;
import PurchaseIAB.purchaseIAB;
import SceneManager.SceneManager;

public class MainActivity extends ApplicationAdapter 
{
	public AudioManager audioManager;
	public SceneManager sceneManager;
	public SaveManager saveManager;

	public IGoogleServices googleServices;
	public purchaseIAB.IABInterface purchaseHelper;

	public MainActivity(IGoogleServices googleServices, purchaseIAB.IABInterface mHelper)
	{
		purchaseHelper = mHelper;
		this.googleServices = googleServices;
	}
	
	@Override
	public void create () 
	{
//		Gdx.input.setCatchBackKey(true);
		//fonts are for debug
		font22 = new BitmapFont(Gdx.files.internal("font/22w.fnt"));

		saveManager = new SaveManager(false);
		loadSaveAtt();

		gameStatData.numberOfAppRun++;

		sceneManager = new SceneManager(this, purchaseHelper);
		sceneManager.setCurrentScene(SceneManager.SCENES.GARAGE_SCENE, null);
//		sceneManager.setCurrentScene(SceneManager.SCENES.GARAGE_SCENE, null);

		if(playerStatData.getMoney() < 2000)
			playerStatData.setMoney(2000);
//		addMoney(2000, true);

		createShowGold();
	}

	public long renderCT = 0;
	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(0.2f, 0.4f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderCT++;

		sceneManager.run();

		runMoney();//for changing showGold
	}


	public GameStatData gameStatData;
	public PlayerStatData playerStatData;
	public LevelPackageStatData [] levelPackageStatDatas;
	public CarStatData [] carStatDatas;
	public SelectorStatData selectorStatData;
	public SettingStatData settingStatData;
	public GunStatData [] gunStatDatas;

	public ArrayList<LevelStatData> levelStatDatas;//load after level Pack Selection

	public void loadSaveAtt()
	{
		gameStatData = saveManager.loadDataValue(DataKeyStrings.GameStatData, GameStatData.class);
		playerStatData = saveManager.loadDataValue(DataKeyStrings.PlayerStatData, PlayerStatData.class);
		levelPackageStatDatas = new LevelPackageStatData[SceneManager.LVL_PACK_NUM + 1];

		for(int i = 1;i <= SceneManager.LVL_PACK_NUM;i++)
			levelPackageStatDatas[i] = saveManager.loadDataValue(DataKeyStrings.LVLPackStatData[i], LevelPackageStatData.class);

		carStatDatas = new CarStatData[SceneManager.CAR_NUM + 1];
		for(int i = 1;i <= SceneManager.CAR_NUM;i++)
			carStatDatas[i] = saveManager.loadDataValue(DataKeyStrings.CarStatData[i], CarStatData.class);

		gunStatDatas = new GunStatData[SceneManager.GUN_NUM + 1];
		for(int i = 1;i <= SceneManager.GUN_NUM;i++)
			gunStatDatas[i] = saveManager.loadDataValue(DataKeyStrings.GunStatData[i], GunStatData.class);

		selectorStatData = saveManager.loadDataValue(DataKeyStrings.SelectorStatData, SelectorStatData.class);
		settingStatData  = saveManager.loadDataValue(DataKeyStrings.SettingStatData, SettingStatData.class);

		levelStatDatas = new ArrayList<LevelStatData>();
	}

	public void loadLevelData(LevelPackage levelPackage, int levelPackID)
	{
		levelStatDatas.clear();
		levelStatDatas.add(new LevelStatData());

		for(int i = 1;i <= levelPackage.numberOfLevels;i++)
			levelStatDatas.add((LevelStatData) saveManager.loadDataValue(DataKeyStrings.getLevelFromPack(levelPackID, i), LevelStatData.class));
	}

	public void saveCarDatas()
	{
		Log.e("MainActivity.java", "saveCarDatas");
		for(int i = 1;i <= SceneManager.CAR_NUM;i++)
			saveManager.saveDataValue(DataKeyStrings.CarStatData[i], carStatDatas[i]);
	}

	public void saveGunDatas()
	{
		Log.e("MainActivity.java", "saveGunDatas");
		for(int i = 1;i <= SceneManager.GUN_NUM;i++)
			saveManager.saveDataValue(DataKeyStrings.GunStatData[i], gunStatDatas[i]);
	}

	public void saveAllLevelDatas(int levels)
	{
		saveManager.saveDataValue(DataKeyStrings.LVLPackStatData[selectorStatData.selectedLevelPack], levelPackageStatDatas[selectorStatData.selectedLevelPack]);

		for(int i = 1;i <= levels;i++)
		{
			String s = DataKeyStrings.getLevelFromPack(selectorStatData.selectedLevelPack, i);
			saveManager.saveDataValue(s, levelStatDatas.get(i));
		}
	}

	public void saveSelector()
	{
		saveManager.saveDataValue(DataKeyStrings.SelectorStatData, selectorStatData);
	}

	public void saveBeforeGameScene()
	{
		saveCarDatas();
		saveSelector();
	}

	public void saveAfterGameScene()
	{
		savePlayerStatData();
	}

	public void savePlayerStatData()
	{
		saveManager.saveDataValue(DataKeyStrings.PlayerStatData, playerStatData);
	}

	public void addMoney(long money, boolean save)
	{
		playerStatData.addMoney(money);

		if(save)
			savePlayerStatData();
	}

	private long showGold;
	private long showGoldSpeed = 7;

	public long getShowGold()
	{
		return showGold;
	}

	public void runMoney()
	{
		if(showGold == playerStatData.getMoney())
			return;

		if(showGold > playerStatData.getMoney())
		{
			showGold = playerStatData.getMoney();
		}
		else
		{
			showGoldSpeed = playerStatData.getMoney() - showGold;
			showGoldSpeed /= 30;

			if(showGoldSpeed == 0)
				showGoldSpeed = 1;

//			Log.e("MainActivity.java", "money = " + playerStatData.getMoney() + " speed = " + showGoldSpeed);

			showGold += showGoldSpeed;
		}
	}

	private void createShowGold()
	{
		showGold = playerStatData.getMoney();
	}


	public BitmapFont font22;
}
