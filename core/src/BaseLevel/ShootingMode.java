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

	public ShootingMode(LevelManager levelManager)
	{
		super(levelManager);

		mode = GameScene.LevelMode.Shooting;
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

		shootingModeTimeCounter += levelManager.gameScene.getDeltaTime();
//		Log.e("ShootingMode.java", "" + shootingModeTimeCounter + " NoWs = " + numberOfWaves + " time = " + waves.get(1).releaseTime);

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

		shootingModeTimeCounter = 0;

		super.start();
	}

	@Override
	public void reset()
	{
		super.reset();
//		shootingModeTimer.clear();
//		shootingModeTimeCounter = 0;

		for(int i = 0;i < waves.size();i++)
			waves.get(i).reset();
	}

	@Override
	public void pause()
	{
		super.pause();
	}

	@Override
	public void resume()
	{
		super.resume();
	}
}
