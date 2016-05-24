package BaseLevel;


import java.util.Random;

import BaseCar.BaseCar;
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

	boolean isEnding;

	public DrivingMode(LevelManager levelManager)
	{
		super(levelManager);
	}

	@Override
	public void run()
	{
		if(isFinished)
			return;
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
	}

	@Override
	public void start()
	{
//		Log.e("DrivingMode.java", "Starting Driving mode");
		levelManager.levelMode = GameScene.LevelMode.Driving;
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
}
