package BaseLevel;

import com.badlogic.gdx.utils.Timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import Enemy.Pigeon;
import EnemyBase.BaseEnemy;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.Log;

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

	ArrayList<String> attr = new ArrayList<String>();
	String enemyType = new String();

	public Timer waveTimer = new Timer();

	public boolean isReleased;
	public float releaseTime;
	public float diffTime;//time between releasing two enemy of this wave!
	ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();

	public BaseWave(GameManager gameManager, ShootingMode shootingMode)
	{
		parentMode = shootingMode;
		this.gameManager = gameManager;
	}

	public void create(String type, int count, BufferedReader dis)
	{
		enemyType = type;
		numberOfEnemies = count;

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

		waveTimer.scheduleTask(new Timer.Task()
		{
			@Override
			public void run()
			{
				createEnemy();
			}
		}, 0, diffTime, numberOfEnemies - 1);
	}

	public void run()
	{
		if(!isReleased)
			return;

	}

	public void createEnemy()
	{
		parentMode.enemyCount ++;
		enemies.add(gameManager.enemyFactory.getEnemy(enemyType, attr));
	}

}
