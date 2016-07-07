package GameScene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import BaseLevel.BaseLevel;
import BaseLevel.EndlessLevel;
import Misc.Log;
import Misc.TextureHelper;
import heshmat.MainActivity;


/**
 * Created by sinazk on 5/14/16.
 * 17:26
 */

public class LevelManager
{
	public MainActivity act;
	public GameScene gameScene;
	public GameManager gameManager;
	public BaseLevel currentLevel;
	public GameScene.LevelModeEnum levelModeEnum;

	public boolean isLost;
	public boolean isLevelCompleted;
	public LevelType levelType;

	public LevelManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
		gameScene = gameManager.gameScene;
		act = this.gameManager.gameScene.act;
	}

	public void create(String add, LevelType levelType)
	{
		loadTextures();

		if(levelType == LevelType.NORMAL)
		{
			currentLevel = new BaseLevel(gameManager);
			currentLevel.load(add);
		}
		else if(levelType == LevelType.ENDLESS)
		{
			EndlessLevel lvl = new EndlessLevel(gameManager, act.levelPackageStatDatas[act.selectorStatData.selectedLevelPack].getEndlessStartingWave());
			lvl.load(add);
			currentLevel = lvl;
		}

		currentLevel.levelParts.get(0).start();
	}

	public void drawOnBatch(Batch batch)
	{
		currentLevel.drawOnBatch(batch);
	}

	public void drawOnPolygonSpriteBatch(PolygonSpriteBatch polygonSpriteBatch)
	{
		currentLevel.drawOnPolygonBatch(polygonSpriteBatch);
	}

	public void run()
	{
		if(isLost)
			lost();

		if(isLevelCompleted)
			levelCompleted();


		currentLevel.run();

		if(currentLevel.currentPart >= currentLevel.levelParts.size())
			return;
	}

	public void lost()
	{
//		Log.e("LevelManager.java", "You Lost");
	}

	public void levelCompleted()
	{
		Log.e("LevelManager.java", "You Win");

		gameScene.EndTheGame(true);
	}

	public void pause()
	{
		currentLevel.pause();
	}

	public void restart()
	{
		isLevelCompleted = false;
		currentLevel.restart();

//		Log.e("LevelManager.java", "levelModeEnum = " + levelModeEnum);
	}

	public void resume()
	{
		currentLevel.resume();
	}

	public Texture DrivingModeSplashTexture;
	public Texture ShootingModeSplashTexture;

	public void loadTextures()
	{
		DrivingModeSplashTexture  = TextureHelper.loadTexture("gfx/lvl/mode/driving.png", gameScene.disposeTextureArray);
		ShootingModeSplashTexture = TextureHelper.loadTexture("gfx/lvl/mode/shooting.png", gameScene.disposeTextureArray);
	}

	public enum LevelType
	{
		NORMAL, ENDLESS,
	}
}
