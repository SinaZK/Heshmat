package BaseLevel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import BaseLevel.Modes.*;
import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.LevelManager;
import Misc.Log;
import Misc.TextureHelper;
import Scene.EndGameScene;
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
	public ArrayList<LevelMode> levelParts = new ArrayList<LevelMode>();

	public Terrain terrain;
	Texture terrainUpTexture, terrainDownTexture;
    public float carIntenseMult = 1;//always one, lineLevel -> 3

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
        terrain.BGSprite = new Sprite(TextureHelper.loadTexture("gfx/lvl/pack" + act.selectorStatData.selectedLevelPack + "/back.png", gameScene.disposeTextureArray));

        terrain.level = this;

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

		if(levelParts.get(currentPart).isFinished && levelParts.get(currentPart).isCameraDone)
		{
			if(gameManager.selectedCar.hitpoint <= 0)
			{
				gameScene.EndTheGame(false);
				return;
			}
			currentPart++;
			if(currentPart < levelParts.size())
				levelParts.get(currentPart).start();
			else
				finishTheLevel();
		}

//		Log.e("BaseLevel", "currentPart = " + currentPart + " isFinished = " + getCurrentPart().isFinished + " isCameraDone = " + getCurrentPart().isCameraDone);

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
		String path = "gfx/lvl/pack" + act.selectorStatData.selectedLevelPack + "/";
		terrainUpTexture   = TextureHelper.loadTexture(path + "up.png", gameScene.disposeTextureArray);
		terrainDownTexture = TextureHelper.loadTexture(path + "rep.jpg", gameScene.disposeTextureArray);

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

	public void pause()
	{
		getCurrentPart().pause();
	}

	public void restart()
	{
		terrain.restart();


		for(int i = 0;i < levelParts.size();i++)
			levelParts.get(i).reset();

		currentPart = 0;
		levelParts.get(0).start();
	}

	public LevelMode getNextPart()
	{
		if(currentPart + 1 >= levelParts.size())
			return null;

		return levelParts.get(currentPart + 1);
	}

	public void resume()
	{
		getCurrentPart().resume();
	}

	public void endGameWithWin()
	{

	}

	public void endGameWithLose()
	{

	}

    public EndGameScene createEndGameScene()
    {
        return new EndGameScene(gameScene);
    }
}
