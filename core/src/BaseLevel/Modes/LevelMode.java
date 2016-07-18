package BaseLevel.Modes;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import BaseCar.BaseCar;
import Entity.LevelEntities.ModeSplashImage;
import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.Log;

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
	public ModeSplashImage modeSplashImage;

	public OrthographicCamera camera;
	public Vector2 cameraPos = new Vector2();
	public float cameraPosZoom;
	public float cameraSpeedX = 10, cameraSpeedY = 10, cameraZoomSpeed = 1;
	public boolean isCameraDone = false;

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


//		if(car.hitpoint <= 0)
//		{
//			cameraPosZoom = 3;
//			LevelMode.this.setCamera(false);
//			gameScene.EndTheGame(false);
//			return;
//		}

		if(isFinished && !isCameraDone)
		{
			runOnEnd();
		}

//		Log.e("Tag", "isFinished = " + isFinished + " isCameraDOne = " + isCameraDone);

		if(cameraSetCT > 0)
			cameraSetCT--;
		else
			setCamera(true);

		if(modeSplashImage != null)
		{
			levelManager.gameScene.HUD.getBatch().begin();
			modeSplashImage.draw(levelManager.gameScene.HUD.getBatch());
			levelManager.gameScene.HUD.getBatch().end();
		}
		int a = 10;
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
		cameraPosZoom = camera.zoom;

		isFinished = false;
	}

	boolean isEndATTRSet = false;
	public void reset()
	{
		isCameraDone = false;
		isFinished = false;
		isEndATTRSet = false;
		setCameraOnReset();
	}

	public void onFinished(){}

	public void pause(){}

	public void resume(){}

	public void setCamera(boolean isSuperCallNeeded)
	{
		float camX = camera.position.x;
		float camY = camera.position.y;

//		if(isFinished)
//			Log.e("Tag", "mode = " + mode + " FIRST Speed = " + cameraSpeedX + " " + cameraSpeedY + " CAMY = " + camera.position.y);

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

		//ZOOM
		float zoomDiff = cameraPosZoom - camera.zoom;
		if(cameraPosZoom > camera.zoom)
		{
			if(zoomDiff > cameraZoomSpeed)
				camera.zoom += cameraZoomSpeed;
			else
				camera.zoom += zoomDiff;
		}
		else
		{
			zoomDiff = Math.abs(zoomDiff);
			if(zoomDiff > cameraZoomSpeed)
				camera.zoom -= cameraZoomSpeed;
			else
				camera.zoom -= zoomDiff;
		}

//		Log.e("LevelMode.java", "cameraZoom = " + camera.zoom + " Target : " + cameraPosZoom);

//		if(isFinished)
//			Log.e("Tag", "mode " + mode + "SECOND Speed = " + cameraSpeedX + " " + cameraSpeedY + " CAMY = " + camera.position.y);
	}

	public void setCameraOnReset()
	{
		cameraPos.x = camera.position.x;
		cameraPos.y = camera.position.y;
		cameraPosZoom = camera.zoom;
	}

	public void runOnEnd()//isFinished == true & isCameraDone == False
	{
		LevelMode nextPart = levelManager.currentLevel.getNextPart();
		if(nextPart == null)
		{
			isCameraDone = true;
			return;
		}

		nextPart.setCamera(false);
		cameraPos.x = nextPart.cameraPos.x;
		cameraPos.y = nextPart.cameraPos.y;
		cameraPosZoom = nextPart.cameraPosZoom;

		if(!isEndATTRSet)
		{
			float TIME = 1.5f;

			float deltaX = (cameraPos.x - camera.position.x);
			float deltaY = (cameraPos.y - camera.position.y);
//			Log.e("Tag", "Deltas = " + deltaX + " y = " + deltaY);

			cameraSpeedX = Math.abs(cameraPos.x - camera.position.x) / TIME / 60f;
			cameraSpeedY = Math.abs(cameraPos.y - camera.position.y) / TIME / 60f;
			cameraZoomSpeed = Math.abs(camera.zoom - cameraPosZoom) / TIME / 60f;

//			Log.e("Tag", "sX = " + cameraSpeedX + " sY = " + cameraSpeedY);

			isEndATTRSet = true;
		}

		if(Math.abs(cameraPos.x - camera.position.x) < 1 && Math.abs(cameraPos.y - camera.position.y) < 1)
			isCameraDone = true;

		car.onStop();
	}
}
