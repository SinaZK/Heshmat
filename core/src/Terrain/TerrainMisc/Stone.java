package Terrain.TerrainMisc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

import GameScene.GameScene;
import Physics.CzakBody;
import PhysicsFactory.PhysicsFactory;


public class Stone extends BaseObject
{
	float x, y;
	int SIZE = 30;
	
	public Stone(float x, float y, World w, GameScene pScene, int size, Texture t)
	{
		super(w, pScene);
		
		this.x = x;
		this.y = y;
		
		this.SIZE = size;

        Body tmp = PhysicsFactory.createCircleBody(world, x, y, SIZE, BodyType.DynamicBody,
				PhysicsFactory.createFixtureDef(0.3f, 0.1f, 1));
		
		Sprite tmpSprite = new Sprite(t);
		tmpSprite.setSize(SIZE * 2, SIZE * 2);
		mBody = new CzakBody(tmp, tmpSprite);
		mBody.setUserData("stone");
	}
	
}
