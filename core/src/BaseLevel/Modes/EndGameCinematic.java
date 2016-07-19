package BaseLevel.Modes;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import BaseLevel.Modes.CinematicState.CinematicState;
import Enemy.Pigeon;
import Entity.Logo;
import GameScene.LevelManager;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 5/22/16.
 * 4:57
 */
public class EndGameCinematic extends CinematicMode
{
	Pigeon enemy;

	public EndGameCinematic(LevelManager levelManager)
	{
		super(levelManager);

		stage = new Stage(new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));
	}

	boolean runTheLogo = false;
	@Override
	public void run()
	{
		super.run();

		if(runTheLogo)
			logoRun();
	}

	public void logoRun()
	{
		if(logo != null)
		{
			logo.run();
			gameScene.HUD.getBatch().begin();
			logo.draw(gameScene.HUD.getBatch());
			gameScene.HUD.getBatch().end();

			stage.draw();
		}
		else
		{
			logo = gameScene.logo;

//			if(logo.currentAlpha >= 0.9f)
				logo.create();
		}
	}

	@Override
	public void start()
	{
		super.start();

		runTheLogo = false;
		time = fullTime;
		cameraSpeedX = 2;
		cameraSpeedY = 1;

		currentState = 0;

		cameraPos.x = camera.position.x;
		cameraPos.y = camera.position.y;
	}

	@Override
	public void reset()
	{
		super.reset();

		runTheLogo = false;
		isFinished = true;
		isCameraDone = true;
	}


	@Override
	public void setCamera(boolean isSuperCallNeeded)
	{
		super.setCamera(true);
	}

	Logo logo;
	@Override
	public void loadStates()
	{
		float carX = firstCarX * PhysicsConstant.PIXEL_TO_METER;
		float carY = firstCarY * PhysicsConstant.PIXEL_TO_METER;

		float firstZoom = levelManager.currentLevel.terrain.cameraZoom;
		float zoom = firstZoom + 1.2f;
		float cX = carX;
		float cY = carY;

		float [] times = {5, 5};

		CinematicState firstState = new CinematicState(this).init(cX, cY, zoom).setSpeed(0, 0, 1.2f / 60f / times[0]).setTime(times[0]);
		states.add(firstState);

		CinematicState state = new CinematicState(this).init(cX, cY, zoom).setSpeed(0, 0, 0).setTime(times[1]);
		state.runnable = new Runnable()
		{
			@Override
			public void run()
			{
				runTheLogo = true;
				gameManager.selectedCar.isAutoPilot = true;
			}
		};
		states.add(state);

		camera.position.x = carX;
		camera.position.y = carY;
	}

	@Override
	public void onFinished()
	{
		super.onFinished();

		enemy.isFree = true;
	}

	@Override
	public void setCameraOnReset()
	{
		super.setCameraOnReset();
	}
}
