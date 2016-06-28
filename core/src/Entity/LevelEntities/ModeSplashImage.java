package Entity.LevelEntities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import Enums.Enums;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.Log;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 6/28/16.
 * 20:42
 */
public class ModeSplashImage extends Sprite
{
	public ModeSplashImage(LevelManager levelManager)
	{
		super();

		this.levelManager = levelManager;
	}

	GameScene gameScene;
	LevelManager levelManager;

	public ModeSplashImage(LevelManager levelManager, Texture texture)
	{
		super(texture);

		this.levelManager = levelManager;
		this.gameScene = levelManager.gameScene;
	}

	public float scaleMax = 1.5f, scaleMin = 0.5f, scaleSpeed = 0.1f;
	public float currentScale = 1;
	public float scaleSign = 1;

	float timeCounter = 0;
	public float time = 1;

	public void set(float sMin, float sMax, float sSpeed, float time)
	{
		this.scaleMax = sMax;
		this.scaleMin = sMin;
		this.time = time;
		this.scaleSpeed = sSpeed;
		timeCounter = 0;
	}

	@Override
	public void draw(Batch batch)
	{
		timeCounter += gameScene.getDeltaTime();
//		Log.e("ModeSplashImage.java", "time = " + timeCounter + " " + time + " " + gameScene.getDeltaTime());

		if(timeCounter > time)
		{
			return;
		}

		currentScale += scaleSpeed * scaleSign;

		if(scaleSign > 0)
			if(currentScale >= scaleMax)
				changeScaleSign();

		if(scaleSign < 0)
			if(currentScale <= scaleMin)
				changeScaleSign();

		setCenter();
		setScale(currentScale);

		super.draw(batch);
	}

	public void setCenter()
	{
		float worldW = SceneManager.WORLD_X;
		float worldH = SceneManager.WORLD_Y;

		float imageW = getWidth();
		float imageH = getHeight();

//		Log.e("ModeSplashImage.java", "imageW = " + imageW);

		setPosition((worldW - imageW) / 2, (worldH - imageH) / 2);
	}

	public void changeScaleSign()
	{
		scaleSign *= -1;
	}
}
