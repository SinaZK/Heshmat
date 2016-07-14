package BaseLevel.Modes.CinematicState;

import com.badlogic.gdx.graphics.OrthographicCamera;

import BaseLevel.Modes.CinematicMode;
import BaseLevel.Modes.LevelMode;

/**
 * Created by sinazk on 7/13/16.
 * 23:45
 */
public class CinematicState
{
	LevelMode mode;

	public float camPosX, camPosY, camZoom;
	public float camSpeedX, camSpeedY, camZoomSpeed;

	public float time;
	private float timeCounter;
	public Runnable runnable;

	OrthographicCamera camera;

	public CinematicState(LevelMode mode)
	{
		this.mode = mode;

		camera = mode.camera;

		camSpeedX = 1;
		camSpeedY = 1;
		camZoomSpeed = 0.01f;
	}

	public CinematicState init(float camX, float camY, float camZoom)
	{
		camPosX = camX;
		camPosY = camY;
		this.camZoom = camZoom;

		return this;
	}

	public CinematicState setSpeed(float sX, float sY, float sZ)
	{
		camSpeedX = sX;
		camSpeedY = sY;
		camZoomSpeed = sZ;

		return this;
	}

	public CinematicState setTime(float t)
	{
		time = t;

		return this;
	}

	public boolean isFinished;
	public void run()
	{
		timeCounter += mode.gameScene.getDeltaTime();

		if(timeCounter >= time)
			isFinished = true;
	}

	public void start()
	{
		isFinished = false;

		if(runnable != null)
			runnable.run();
	}

	public void reset()
	{
		timeCounter = 0;
		isFinished = false;
	}

}
