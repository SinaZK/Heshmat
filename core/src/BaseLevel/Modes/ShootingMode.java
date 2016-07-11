package BaseLevel.Modes;

import java.util.ArrayList;

import Entity.LevelEntities.ModeSplashImage;
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
	public ArrayList<BaseLevel.BaseWave> waves = new ArrayList<BaseLevel.BaseWave>();

	float shootingModeTimeCounter = 0;

	public ShootingMode(LevelManager levelManager)
	{
		super(levelManager);

		mode = GameScene.LevelModeEnum.Shooting;
		modeSplashImage = new ModeSplashImage(levelManager, levelManager.ShootingModeSplashTexture);
	}

	@Override
	public void run()
	{
		super.run();

		for(int i = 0;i < numberOfWaves;i++)
		{
			if(!waves.get(i).isReleased)
				if(waves.get(i).releaseTime <= shootingModeTimeCounter)
					waves.get(i).release();

			waves.get(i).run();
		}

		shootingModeTimeCounter += levelManager.gameScene.getDeltaTime();

		if(enemyDied == enemyMax)
			isFinished = true;
	}

	float ratio = PhysicsConstant.PIXEL_TO_METER;
	@Override
	public void start()
	{
		cameraSpeedX = 5;
		cameraSpeedY = 0.5f;

		enemyCount = 0;
		enemyDied = 0;
		enemyMax = 0;

		for(int i = 0;i < numberOfWaves;i++)
			enemyMax += waves.get(i).numberOfEnemies;

		shootingModeTimeCounter = 0;

		super.start();

		modeSplashImage.set(0.5f, 1.5f, 0.02f, 2f);
	}

	@Override
	public void reset()
	{
		super.reset();

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

	@Override
	public void setCamera(boolean isSuperCallNeeded)
	{
		if(!isFinished)
		{
			camera.zoom = levelManager.currentLevel.terrain.cameraZoom;

			cameraPos.x = gameManager.shooterHuman.standBody.getmBody().getWorldCenter().x * PhysicsConstant.PIXEL_TO_METER + 600;
			cameraPos.y = gameManager.shooterHuman.standBody.getmBody().getWorldCenter().y * PhysicsConstant.PIXEL_TO_METER + 300;
		}


		if(isSuperCallNeeded)
			super.setCamera(false);
	}
}
