package BaseLevel.Modes;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import Entity.LevelEntities.ModeSplashImage;
import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.LevelManager;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 5/22/16.
 * 4:57
 */
public class CinematicMode extends LevelMode
{
	public float time, fullTime;

	protected GameScene gameScene;
	protected GameManager gameManager;

	protected Stage stage;

	public CinematicMode(LevelManager levelManager)
	{
		super(levelManager);
		mode = GameScene.LevelModeEnum.Cinematic;
		modeSplashImage = new ModeSplashImage(levelManager, levelManager.ShootingModeSplashTexture);

		gameScene = levelManager.gameScene;
		gameManager = levelManager.gameManager;
		stage = new Stage(new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));
	}

	@Override
	public void run()
	{
		if(isFinished)
		{
			isCameraDone = true;
			return;
		}

		time -= levelManager.gameScene.getDeltaTime();

		if(time <= 0)
		{
			isFinished = true;
			onFinished();
		}

		super.run();

		stage.act();
		stage.draw();

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

	@Override
	public void reset()
	{
		super.reset();
	}

	@Override
	public void setCamera(boolean isSuperCallNeeded)
	{
		super.setCamera(false);
	}

	@Override
	public void setCameraOnReset()
	{
		super.setCameraOnReset();
	}
}
