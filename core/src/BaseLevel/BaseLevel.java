package BaseLevel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import GameScene.GameManager;
import GameScene.GameScene;
import GameScene.LevelManager;
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
		terrainUpTexture   = TextureHelper.loadTexture(add + "up.png", gameScene.disposeTextureArray);
		terrainDownTexture = TextureHelper.loadTexture(add + "rep.jpg", gameScene.disposeTextureArray);
		terrainDownTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

		terrain = new Terrain(act, add);
		terrain.loadResources(terrainUpTexture, terrainDownTexture);
		terrain.create(gameScene.spriteBatch, gameScene.polygonSpriteBatch);
	}

	public void create()
	{

	}


	public void run()
	{
		terrain.run();
	}

	public void drawOnBatch(Batch batch)
	{
	}

	public void drawOnPolygonBatch(PolygonSpriteBatch polygonSpriteBatch)
	{
		terrain.drawFirstLayer();
		terrain.drawSecondLayer();
	}
}
