package BaseLevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import EnemyBase.BaseEnemy;
import EnemyBase.EnemyFactory;
import GameScene.GameManager;
import Misc.BodyStrings;

/**
 * Created by sinazk on 5/14/16.
 * 18:05
 * <p/>
 * Group of same Enemy!
 */
public class BaseWave
{
	GameManager gameManager;
	ShootingMode parentMode;

	public int numberOfEnemies;
	public int enemyLevel;

	ArrayList<String> attr = new ArrayList<String>();
	String enemyType = new String();

	public float waveTimeCounter;

	public int numberOfReleasedEnemy;
	public boolean isReleased;
	public float releaseTime;
	public float diffTime;//time between releasing two enemy of this wave!
	ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();

	public boolean shouldInfoCardShown;

	public BaseWave(GameManager gameManager, ShootingMode shootingMode)
	{
		parentMode = shootingMode;
		this.gameManager = gameManager;
	}

	public void create(String type, int level, int count, BufferedReader dis)
	{
		enemyType = type;
		numberOfEnemies = count;
		enemyLevel = level;
		shouldInfoCardShown = false;

		try
		{
			int ct = 0;
			while (true)
			{
				String read = dis.readLine();

				if(read.equals(LevelLoader.EOF))
					break;

				attr.add(read);

				if(ct == 0)//timeBetween
					diffTime = Float.valueOf(BodyStrings.getPartOf(read, 1));

				if(ct == 1)//releaseTime
					releaseTime = Float.valueOf(BodyStrings.getPartOf(read, 1));

				if(ct == 2)
					if(read.equals("INFO"))
						shouldInfoCardShown = true;

				ct++;
			}


		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void release()
	{
		if(isReleased)
			return;

		isReleased = true;

		if(shouldInfoCardShown)
		{
			gameManager.activity.sceneManager.dialogManager.addInfoCardDialog(gameManager.gameScene,
					gameManager.enemyFactory.getInfoSprite(EnemyFactory.StringToEnemy(enemyType)));
		}
	}

	public void run()
	{
		if(!isReleased)
			return;

		waveTimeCounter += parentMode.levelManager.gameScene.getDeltaTime();

		if(waveTimeCounter >= diffTime)
		{
//			Log.e("BaseWave.java", "waveCT = " + waveTimeCounter + " diffTime = " + diffTime);
			waveTimeCounter = 0;
			if(numberOfReleasedEnemy < numberOfEnemies)
				createEnemy();
		}
	}

	public void createEnemy()
	{
		numberOfReleasedEnemy++;
		parentMode.enemyCount ++;
		enemies.add(gameManager.enemyFactory.getEnemy(enemyType, enemyLevel, attr));
	}

	public void reset()
	{
		numberOfReleasedEnemy = 0;
		waveTimeCounter = 0;
		isReleased = false;
	}

}
