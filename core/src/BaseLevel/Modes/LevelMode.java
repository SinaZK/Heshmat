package BaseLevel.Modes;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import BaseCar.BaseCar;
import Entity.LevelEntities.ModeSplashImage;
import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.LevelManager;

/**
 * Created by sinazk on 5/22/16.
 * -
 */
public abstract class LevelMode
{
	public GameScene gameScene;
	public GameManager gameManager;
	public LevelManager levelManager;
	public boolean isFinished;
	public float firstCarX, firstCarY;
	public BaseCar car;
	public GameScene.LevelModeEnum mode;
	ModeSplashImage modeSplashImage;

	public OrthographicCamera camera;
	public Vector2 cameraPos = new Vector2();
	public float cameraSpeedX = 10, cameraSpeedY = 10;

	public LevelMode(LevelManager levelManager)
	{
		this.levelManager = levelManager;
		gameManager = levelManager.gameManager;
		gameScene = levelManager.gameScene;
		car = levelManager.gameManager.selectedCar;
		camera = levelManager.gameScene.camera;
		if(levelManager.currentLevel == null)
			camera.zoom = 1;
		else
			camera.zoom = levelManager.currentLevel.terrain.cameraZoom;
	}

	public void run()
	{
		if(cameraSetCT > 0)
			cameraSetCT--;
		else
			setCamera();

		if(modeSplashImage != null)
		{
			levelManager.gameScene.HUD.getBatch().begin();
			modeSplashImage.draw(levelManager.gameScene.HUD.getBatch());
			levelManager.gameScene.HUD.getBatch().end();
		}

//		Log.e("LevelModeEnum.java", "run" + mode);
	}

	int cameraSetCT;

	public void start()
	{
		levelManager.levelModeEnum = mode;
		gameScene.setInput();
		firstCarX = car.body.bodies.get(0).getmBody().getWorldCenter().x;
		firstCarY = car.body.bodies.get(0).getmBody().getWorldCenter().y;

		cameraSetCT = 2;
		cameraPos.x = camera.position.x;
		cameraPos.y = camera.position.y;
	}

	public void reset()
	{
		isFinished = false;
		setCameraOnReset();
	}

	public void onFinished(){}

	public void pause(){}

	public void resume(){}

	public void setCamera()
	{
		float camX = camera.position.x;
		float camY = camera.position.y;

		float diffX = cameraPos.x - camX;
		float diffY = cameraPos.y - camY;

		if(diffX > 0)
			if(diffX > cameraSpeedX)
				camX += cameraSpeedX;
			else
				camX += diffX;
		if(diffX < 0)
			if(Math.abs(diffX) > cameraSpeedX)
				camX -= cameraSpeedX;
			else
				camX += diffX;

		if(diffY > 0)
			if(diffY > cameraSpeedY)
				camY += cameraSpeedY;
			else
				camY += diffY;
		if(diffY < 0)
			if(Math.abs(diffY) > cameraSpeedY)
				camY -= cameraSpeedY;
			else
				camY += diffY;

		camera.position.set(camX, camY, 0);

//		Log.e("LevelModeEnum.java", "SetCamera" + mode);
	}

	public void setCameraOnReset()
	{
		cameraPos.x = camera.position.x;
		cameraPos.y = camera.position.y;
	}

}
