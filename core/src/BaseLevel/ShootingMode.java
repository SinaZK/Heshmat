package BaseLevel;

import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;

/**
 * Created by sinazk on 5/22/16.
 * 06:02
 */
public class ShootingMode extends LevelMode
{
	public int numberOfWaves;
	public int enemyCount, enemyMax, enemyDied;//enemyAlive = enemyCount - enemyDied
	public ArrayList<BaseWave> waves = new ArrayList<BaseWave>();

	float shootingModeTimeCounter = 0;
	public Timer shootingModeTimer = new Timer();

	public ShootingMode(LevelManager levelManager)
	{
		super(levelManager);
	}

	@Override
	public void run()
	{
		for(int i = 0;i < numberOfWaves;i++)
		{
			if(!waves.get(i).isReleased)
				if(waves.get(i).releaseTime <= shootingModeTimeCounter)
					waves.get(i).release();

			waves.get(i).run();
		}

		if(enemyDied == enemyMax)
			isFinished = true;
	}

	float ratio = PhysicsConstant.PIXEL_TO_METER;
	@Override
	public void start()
	{
		for(int i = 0;i < numberOfWaves;i++)
			enemyMax += waves.get(i).numberOfEnemies;
		enemyCount = 0;
		enemyDied = 0;

		shootingModeTimer.clear();
		shootingModeTimeCounter = 0;
		shootingModeTimer.scheduleTask(new Timer.Task()
		{
			@Override
			public void run()
			{
				shootingModeTimeCounter+= 0.1f;
			}
		}, 0, 0.1f);

		levelManager.levelMode = GameScene.LevelMode.Shooting;

		super.start();

//		levelManager.gameManager.gunManager.reposition(this);
	}
}
