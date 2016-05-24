package GameScene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import BaseLevel.BaseLevel;
import BaseLevel.DrivingMode;
import BaseLevel.ShootingMode;
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

	public LevelManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
		gameScene = gameManager.gameScene;
		act = this.gameManager.gameScene.act;
	}

	public void create(String add)
	{
		currentLevel = new BaseLevel(gameManager);
		currentLevel.load("gfx/lvl/test/");

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
		currentLevel.run();

		if(currentLevel.currentPart >= currentLevel.levelParts.size())
			return;

		currentLevel.levelParts.get(currentLevel.currentPart).run();

//		Log.e("LevelManager.java", "Mode = " + levelMode);
	}
}
