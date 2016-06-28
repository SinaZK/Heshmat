package GameScene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import BaseLevel.*;
import Misc.Log;
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
	public GameScene.LevelMode levelMode;

	public boolean isLost;
	public boolean isLevelCompleted;

	public LevelManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
		gameScene = gameManager.gameScene;
		act = this.gameManager.gameScene.act;
	}

	public void create(String add)
	{
		currentLevel = new BaseLevel(gameManager);
		currentLevel.load(add);

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
	}

	public void pause()
	{
		currentLevel.pause();
	}

	public void restart()
	{
		currentLevel.restart();

//		Log.e("LevelManager.java", "levelMode = " + levelMode);
	}

	public void resume()
	{
		currentLevel.resume();
	}
}
