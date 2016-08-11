package Terrain.TerrainMisc;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Enemy.EnemyState.Coin;
import Enemy.EnemyState.WaterBox;
import EnemyBase.BaseEnemy;
import GameScene.GameScene;
import Misc.CameraHelper;
import Misc.TextureHelper;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import Terrain.Terrain;

public class ObjectManager 
{
	public ArrayList<BaseObject> objects;

	GameScene mScene;
    Terrain terrain;
	World world;

	Texture stoneTexture;
	Texture bumperTexture;
	Texture bridgeTexture;
	
	public ObjectManager(GameScene pSceneNormal, World w, Terrain terrain)
	{
		objects = new ArrayList<BaseObject>();
		
		world = w;
		mScene = pSceneNormal;
        this.terrain = terrain;

		stoneTexture = TextureHelper.loadTexture("gfx/lvl/misc/stone.png", mScene.disposeTextureArray);
		bumperTexture = TextureHelper.loadTexture("gfx/lvl/misc/bumper.png", mScene.disposeTextureArray);
		bridgeTexture = TextureHelper.loadTexture("gfx/lvl/misc/bridge.jpg", mScene.disposeTextureArray);
	}
	
	public void run(Batch batch)
	{
		float xMin = CameraHelper.getXMin(mScene.camera, 2.5f);
		for(int i = 0;i < objects.size();i++)
		{
			if(xMin - 200 > objects.get(i).getPositionX())
			{
//				Log.e("Tag", "Object " + i + " removed");
				objects.get(i).dispose(true);
				objects.remove(i);
				i--;
			}
			else
				objects.get(i).run((SpriteBatch)batch);
		}
	}
	
	public Stone addStone(float x, float y, int sz)
	{
		Stone st = new Stone(x, y, world, mScene, sz, stoneTexture);
		objects.add(st);
		
		return st;
	}
	
	public void addBumper(float x, float y, int sz)
	{
		objects.add(new Bumper(x, y, world, mScene, sz, bumperTexture));
	}
	
	public void addBridge(float x, float y, int sz)
	{
		objects.add(new Bridge(x, y, sz, world, mScene, bridgeTexture));

        Vector2 lastPoint = terrain.points.getLast();
        int lastBridgeX = (int) (lastPoint.x + sz * Bridge.pieceW);
        terrain.points.add(new Vector2(lastBridgeX, lastPoint.y));
        terrain.addLastPiece();

	}
	
	public void addSand(float x, int number)
	{
		int j = (int) terrain.getXof(x);
		float y1 = terrain.points.get(j).y;
		float y2 = terrain.points.get(j + 1).y;
		
		float maxY = Math.max(y2, y1);
		
		int rad = 3;
		float y = maxY;
		
		while(number > 0)
		{
			for(int i = 0;i < terrain.xSize && number > 0; i += rad, number--)
			{
				addStone(x + i, y, rad);
				objects.get(objects.size() - 1).mBody.getmBody().setUserData("Sand");
			}
			
			y += rad;
		}
		for(int i = 0;i < number;i++)
		{
			addStone(x, y + i * 2, 4);
			objects.get(objects.size() - 1).mBody.getmBody().setUserData("Sand");
		}
	}

    public void addBoxShelf(float startX, float startY, int level, int size)
    {
        int lineNumber = size;
        float lineTab = 45;
        float paddingX = 90;//pad = 30
        float paddingY = 111;
        float currentX = startX;
        float currentY = startY;
        for(int i = 0;i < size;i++)
        {
            for(int j = 0;j < lineNumber;j++)
            {
                WaterBox waterBox = (WaterBox) mScene.gameManager.enemyFactory.getDrivingEnemy(BaseEnemy.EnemyType.WaterBox, level, null);
                waterBox.setPosition(currentX + j * paddingX, currentY);
            }

            lineNumber--;
            currentY += paddingY;
            currentX += lineTab;
        }
    }

    public void addCoin(float firstX, int rowSize, int num, int value)
    {
        int ct = 0;
        float paddingX = 100;
        float paddingY = 100;
        float cX = firstX;
        float cY = 0;//terrain.points.getLast().y;
        for(int i = 0;i < num;i++)
        {
            Coin coin = (Coin) mScene.gameManager.enemyFactory.getDrivingEnemy(BaseEnemy.EnemyType.Coin, 1, null);
            coin.setBodyType(BodyDef.BodyType.StaticBody);
            coin.setPosition(cX, cY);
            coin.GOLD = value;
            ct++;

            cX += paddingX;

            if(ct % rowSize == 0)
            {
                cX = firstX;
                cY += paddingY;
            }
        }
    }
	
	public void restart()
	{
		objects.clear();
	}
	
	public void dispose()
	{
		objects.clear();
		if(bumperTexture != null)
			bumperTexture.dispose();
		if(stoneTexture != null)
			stoneTexture.dispose();
		if(bridgeTexture != null)
			bridgeTexture.dispose();
	}
}
