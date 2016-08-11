package Terrain.TerrainMisc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import GameScene.GameScene;
import Misc.BodyStrings;
import Physics.CzakBody;
import PhysicsFactory.PhysicsFactory;

public class BridgePiece extends BaseObject 
{
	public BridgePiece(float centerX, float centerY, World w, GameScene pSceneNormal, float width, float height, Texture t)
	{
		super(w, pSceneNormal);

		Body tmp = PhysicsFactory.createBoxBody(world, centerX, centerY, width, height, 0, BodyType.DynamicBody,
				PhysicsFactory.createFixtureDef(1f, 0.1f, 1f), rat);
		
		Sprite tmpSprite = new Sprite(t);
		tmpSprite.setSize(width, height);
		mBody = new CzakBody(tmp, tmpSprite);
		mBody.setUserData(BodyStrings.GroundString);
	}

}
