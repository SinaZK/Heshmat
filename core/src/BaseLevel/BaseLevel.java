package BaseLevel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import java.util.ArrayList;

import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.Log;
import Misc.TextureHelper;
import Terrain.Terrain;
import heshmat.MainActivity;

/**
 * Created by sinazk on 5/14/16.
 * 17:27
 */
public class BaseLevel
{
	MainActivity act;
	GameScene gameScene;
	GameManager gameManager;
	LevelManager levelManager;
	public int currentPart;
	public ArrayList <LevelMode> levelParts = new ArrayList<LevelMode>();

	public Terrain terrain;
	Texture terrainUpTexture, terrainDownTexture;

	public BaseLevel(GameManager gameManager)
	{
		this.gameManager = gameManager;
		act = gameManager.gameScene.act;
		levelManager = gameManager.levelManager;
		gameScene = gameManager.gameScene;
	}

	public void load(String add)
	{
		loadTerrain(add);

		LevelLoader.loadLVLFile(this, gameManager, add, gameScene.world, gameScene.disposeTextureArray);
	}

	public void start()
	{
		levelParts.get(0).start();
	}


	public void run()
	{
		terrain.run();

		if(currentPart >= levelParts.size())
			return;

		if(levelParts.get(currentPart).isFinished)
		{
			currentPart++;
			Log.e("BaseLevel.java", "CurrentPart = " + currentPart);
			if(currentPart < levelParts.size())
				levelParts.get(currentPart).start();
			else
			{
				finishTheLevel();
			}
		}

		if(currentPart < levelParts.size())
			levelParts.get(currentPart).run();
	}

	public void drawOnBatch(Batch batch)
	{
	}

	public void drawOnPolygonBatch(PolygonSpriteBatch polygonSpriteBatch)
	{
		terrain.drawFirstLayer();
		terrain.drawSecondLayer();
	}

	public void loadTerrain(String add)
	{
		terrainUpTexture   = TextureHelper.loadTexture(add + "up.png", gameScene.disposeTextureArray);
		terrainDownTexture = TextureHelper.loadTexture(add + "rep.jpg", gameScene.disposeTextureArray);
		terrainDownTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		terrain = new Terrain(act, add);
		terrain.loadResources(terrainUpTexture, terrainDownTexture);
		terrain.create(gameScene.spriteBatch, gameScene.polygonSpriteBatch);
	}

	public void finishTheLevel()
	{
		Log.e("BaseLevel.Java", "LEVEL Parts Finished!!!");
		levelManager.isLevelCompleted = true;
	}

	public LevelMode getCurrentPart()
	{
		if(currentPart >= levelParts.size())
			return levelParts.get(0);
		return levelParts.get(currentPart);
	}
}
