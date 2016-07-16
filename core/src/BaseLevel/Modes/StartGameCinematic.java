package BaseLevel.Modes;


import BaseLevel.Modes.CinematicState.CinematicState;
import Enemy.Fly;
import Enemy.Pigeon;
import EnemyBase.BaseEnemy;
import GameScene.LevelManager;
import Misc.CameraHelper;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;

/**
 * Created by sinazk on 5/22/16.
 * 4:57
 */
public class StartGameCinematic extends CinematicMode
{
	Pigeon enemy;

	public StartGameCinematic(LevelManager levelManager)
	{
		super(levelManager);
	}

	@Override
	public void run()
	{
		super.run();
	}

	@Override
	public void start()
	{
		super.start();

		time = fullTime;
		cameraSpeedX = 2;
		cameraSpeedY = 1;

		currentState = 0;
	}

	@Override
	public void reset()
	{
		super.reset();

		isFinished = true;
		isCameraDone = true;
	}

	@Override
	public void setCamera(boolean isSuperCallNeeded)
	{
		super.setCamera(true);
	}

	@Override
	public void loadStates()
	{
		float carX = firstCarX * PhysicsConstant.PIXEL_TO_METER;
		float carY = firstCarY * PhysicsConstant.PIXEL_TO_METER;

		float firstZoom = levelManager.currentLevel.terrain.cameraZoom;
		float zoom = firstZoom + 1.2f;
		float cX = carX;
		float cY = carY;

		float [] times = {5, 5, 18, 5};

		CinematicState firstState = new CinematicState(this).init(cX, cY, zoom).setSpeed(0, 0, 1.2f / 60f / times[0]).setTime(times[0]);
		states.add(firstState);

		cX += times[1] * 60 * 3;
		CinematicState state = new CinematicState(this).init(cX, cY, zoom).setSpeed(3, 0, 0).setTime(times[1]);
		states.add(state);

		state = new CinematicState(this).init(cX, cY, zoom).setSpeed(3, 0, 0).setTime(times[2]);
		states.add(state);
		state.runnable = new Runnable()
		{
			@Override
			public void run()
			{
				enemy = (Pigeon)gameManager.enemyFactory.getDrivingEnemy(BaseEnemy.EnemyType.PIGEON, 25, null);
				enemy.setPosition(CameraHelper.getXMax(camera) - 100, CameraHelper.getYMax(camera) - 300);
			}
		};

		CinematicState lastState = new CinematicState(this).init(cX, cY, levelManager.currentLevel.terrain.cameraZoom).
				setSpeed(0, 0, 1.2f / 60f / times[3]).setTime(times[3]);
		states.add(lastState);


		camera.position.x = carX;
		camera.position.y = carY;
	}

	@Override
	public void onFinished()
	{
		super.onFinished();

		enemy.isFree = true;
	}

}
