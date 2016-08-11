package Terrain.TerrainMisc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import GameScene.GameScene;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;

public class BaseObject
{
	public CzakBody mBody;
	World world;
	float rat;
	GameScene mScene;
	
	public BaseObject(World w, GameScene pSceneNormal)
	{
		mScene = pSceneNormal;
		world = w;
		rat = PhysicsConstant.PIXEL_TO_METER;
	}
	
	public void run(SpriteBatch batch)
	{
		mBody.draw(batch);
	}
	
	public float getPositionX()
	{
		return mBody.getmBody().getWorldCenter().x * rat;
	}
	
	public void dispose(boolean disposeBody) 
	{
		if(disposeBody)
			PhysicsFactory.removeBodySafely(world, mBody.getmBody());
	}
}
