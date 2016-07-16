package BaseLevel.Modes;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import BaseLevel.Modes.CinematicState.CinematicState;
import Entity.LevelEntities.ModeSplashImage;
import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.LevelManager;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 5/22/16.
 * 4:57
 */
public abstract class CinematicMode extends LevelMode
{
	public float time, fullTime;

	protected GameScene gameScene;
	protected GameManager gameManager;

	protected Stage stage;

	public int currentState = 0;
	protected ArrayList<CinematicState> states = new ArrayList<CinematicState>();

	public CinematicMode(LevelManager levelManager)
	{
		super(levelManager);
		mode = GameScene.LevelModeEnum.Cinematic;
		modeSplashImage = new ModeSplashImage(levelManager, levelManager.ShootingModeSplashTexture);

		gameScene = levelManager.gameScene;
		gameManager = levelManager.gameManager;

		loadStates();
	}

	@Override
	public void run()
	{
		super.run();

		if(isFinished)
		{
			return;
		}

		if(currentState < states.size())
		{
			states.get(currentState).run();

			if(states.get(currentState).isFinished)
			{
				currentState++;
				if(currentState >= states.size())
					isFinished = true;
				else
					states.get(currentState).start();
			}
		}

		time -= levelManager.gameScene.getDeltaTime();

		if(time <= 0)
		{
			isFinished = true;
			onFinished();
		}



	}

	@Override
	public void start()
	{
		time = fullTime;
		cameraSpeedX = 100;
		cameraSpeedY = 100;

		super.start();

		modeSplashImage.set(0.8f, 1.2f, 0.02f, 0.1f);

		levelManager.act.audioManager.playCinematicMusic();
	}

	@Override
	public void reset()
	{
		super.reset();

		currentState = 0;
		for(int i = 0;i < states.size();i++)
			states.get(i).reset();
	}

	@Override
	public void setCamera(boolean isSuperCallNeeded)
	{
		if(!isFinished)
		{
			cameraPos.x = states.get(currentState).camPosX;
			cameraPos.y = states.get(currentState).camPosY;
			cameraPosZoom = states.get(currentState).camZoom;

			cameraSpeedX = states.get(currentState).camSpeedX;
			cameraSpeedY = states.get(currentState).camSpeedY;
			cameraZoomSpeed = states.get(currentState).camZoomSpeed;
		}

		if(isSuperCallNeeded)
			super.setCamera(false);
	}

	@Override
	public void setCameraOnReset()
	{
		super.setCameraOnReset();
	}

	public abstract void loadStates();
}
