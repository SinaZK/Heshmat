package GameScene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import BaseLevel.BaseLevel;
import heshmat.MainActivity;

/**
 * Created by sinazk on 5/14/16.
 * 17:26
 */

public class LevelManager
{
	MainActivity act;
	GameScene gameScene;
	GameManager gameManager;
	BaseLevel currentLevel;

	public LevelManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
		gameScene = gameManager.gameScene;
		act = this.gameManager.gameScene.act;
	}

	public void createTestLVL()
	{
		currentLevel = new BaseLevel(gameManager);
		currentLevel.load("gfx/lvl/test/");
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
	}


	public enum LevelMode
	{
		Shooting, Driving
	}
}
