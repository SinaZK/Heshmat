package BaseLevel;


import java.util.Random;

import Entity.LevelEntities.ModeSplashImage;
import GameScene.GameScene;
import GameScene.LevelManager;
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

	public DrivingMode(LevelManager levelManager)
	{
		super(levelManager);
		mode = GameScene.LevelMode.Driving;
		modeSplashImage = new ModeSplashImage(levelManager, levelManager.ShootingModeSplashTexture);
	}

	@Override
	public void run()
	{
		if(isFinished)
			return;

		super.run();

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
				isFinished = true;
		}

		levelManager.gameScene.drivingModeHUD.getBatch().begin();
		levelManager.gameScene.font16.draw(levelManager.gameScene.drivingModeHUD.getBatch(), "time = " + time, 10, 400);
		levelManager.gameScene.font16.draw(levelManager.gameScene.drivingModeHUD.getBatch(), "dist = " + getCurrentPos(), 10, 450);
		levelManager.gameScene.drivingModeHUD.getBatch().end();

	}

	@Override
	public void start()
	{
		time = fullTime;
		cameraSpeedX = 100;
		cameraSpeedY = 100;

		super.start();

		modeSplashImage.set(0.8f, 1.2f, 0.02f, 0.1f);
	}

	public float getCurrentPos()
	{
		float diff = car.body.bodies.get(0).getmBody().getWorldCenter().x - firstCarX;
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
	}

	@Override
	public void setCamera()
	{
		camera.zoom = levelManager.currentLevel.terrain.cameraZoom;
		cameraPos.x = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER + 400;
		cameraPos.y = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER + 80;

		super.setCamera();
	}

	@Override
	public void setCameraOnReset()
	{
		camera.position.x = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER + 400;
		camera.position.y = gameManager.selectedCar.body.bodies.get(0).getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER + 80;
		super.setCameraOnReset();
	}
}
