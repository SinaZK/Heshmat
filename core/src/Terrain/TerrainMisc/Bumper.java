package Terrain.TerrainMisc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import GameScene.GameScene;
import Physics.CzakBody;
import PhysicsFactory.PhysicsFactory;

public class Bumper extends BaseObject
{
	int SIZE;
	public Bumper(float x, float y, World w, GameScene pSceneNormal, int radius, Texture t)
	{
		super(w, pSceneNormal);
		
		this.SIZE = radius;

		Body tmp = PhysicsFactory.createCircleBody(world, x, y, SIZE, BodyType.StaticBody,
				PhysicsFactory.createFixtureDef(0.3f, 0.1f, 1));
		
		Sprite tmpSprite = new Sprite(t);
		tmpSprite.setSize(SIZE * 2, SIZE * 2);
		mBody = new CzakBody(tmp, tmpSprite);
		mBody.setUserData("bumper");
	}
}
