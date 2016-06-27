package BaseLevel;


import java.util.Random;

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
	}

	@Override
	public void run()
	{
		if(isFinished)
			return;

		time -= levelManager.gameScene.getDeltaTime();
//		Log.e("DrivingMode.java", "Delta = " + levelManager.gameScene.getDeltaTime());

		if(time <= 0)
			levelManager.isLost = true;

//		if(Math.abs(random.nextInt() % 1000) < 30)
//			Log.e("DrivingMode.java", "Pos = " + getCurrentPos() + " max = " + distance);

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
		levelManager.gameScene.drivingModeHUD.getBatch().end();
	}

	@Override
	public void start()
	{
		time = fullTime;
		super.start();
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
}
