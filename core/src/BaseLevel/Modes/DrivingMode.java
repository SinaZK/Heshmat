package BaseLevel.Modes;


import java.util.ArrayList;
import java.util.Random;

import Enemy.EnemyState.StreetLight;
import EnemyBase.BaseEnemy;
import Entity.LevelEntities.ModeSplashImage;
import Enums.Enums;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.CameraHelper;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;

/**
 * Created by sinazk on 5/22/16.
 * 4:57
 */
public class DrivingMode extends LevelMode
{
	public float distance;
	public float time, fullTime;

	boolean isEnding;

//	public static int StreetLightDist = 1000;
//	public int streetLightPos = StreetLightDist;

	public ArrayList<DrivingEnemyQuery> queries = new ArrayList<DrivingEnemyQuery>();

	public DrivingMode(LevelManager levelManager)
	{
		super(levelManager);
		mode = GameScene.LevelModeEnum.Driving;
		modeSplashImage = new ModeSplashImage(levelManager, levelManager.DrivingModeSplashTexture);
	}

	@Override
	public void run()
	{
		super.run();

		if(isFinished)
		{
			car.stop();
			return;
		}

		time -= levelManager.gameScene.getDeltaTime();

		if(time <= 0)
			levelManager.isLost = true;

		if(getCurrentPos() >= distance)
		{
			isEnding = true;
		}

		if(isEnding)
		{
			if(!car.isStopped())
				car.stop();
			else
			{
				gameManager.distanceTraveled += distance;
				isFinished = true;
			}
		}

		levelManager.gameScene.drivingModeHUD.getBatch().begin();
		levelManager.gameScene.drawDist(levelManager.gameScene.drivingModeHUD.getBatch(), getCurrentPos(), distance);
		levelManager.gameScene.drivingModeHUD.getBatch().end();

		for(int i = 0;i < queries.size();i++)
			queries.get(i).run(getCurrentPos());

	}

	@Override
	public void start()
	{
		time = fullTime;
		cameraSpeedX = 100;
		cameraSpeedY = 100;

		super.start();

		modeSplashImage.set(0.8f, 1.2f, 0.02f, 2.0f);
//		Log.e("DrivingMode : " , "settingTime");

		for(int i = 0;i < queries.size();i++)
			queries.get(i).reset();
	}

	public float getCurrentPos()
	{
		float diff = car.body.bodies.get(0).getmBody().getWorldCenter().x - firstCarX;
		diff *= PhysicsConstant.PIXEL_TO_METER;
		return diff;
	}

	public float getCurrentPosFull()
	{
		float diff = car.body.bodies.get(0).getmBody().getWorldCenter().x;
		diff *= PhysicsConstant.PIXEL_TO_METER;

		return diff;
	}

	public float getEndDistance()
	{
		return firstCarX * PhysicsConstant.PIXEL_TO_METER + distance;
	}
	Random random = new Random();

	@Override
	public void reset()
	{
		super.reset();
		isEnding = false;

		for (int i = 0;i < queries.size();i++)
			queries.get(i).reset();
	}

	@Override
	public void setCamera(boolean isSuperCallNeeded)
	{
		if(!isFinished)
		{
			camera.zoom = levelManager.currentLevel.terrain.cameraZoom;
			cameraPos.x = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER + 400;
			cameraPos.y = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER + 80;
		}

		if(isSuperCallNeeded)
			super.setCamera(false);
	}

	@Override
	public void setCameraOnReset()
	{
		camera.position.x = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER + 400;
		camera.position.y = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER + 80;
		super.setCameraOnReset();
	}
}
