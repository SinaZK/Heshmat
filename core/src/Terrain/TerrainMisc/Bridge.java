package Terrain.TerrainMisc;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import GameScene.GameScene;
import PhysicsFactory.PhysicsFactory;

public class Bridge extends BaseObject 
{
	ArrayList<BridgePiece> pieces;
	Body firstStaticBody, lastStaticBody;

	public static int pieceW = 60;
	public static int pieceH = 25;
	
	public Bridge(float x, float y, int numberOfPieces, World w, GameScene pSceneNormal, Texture t)
	{
		super(w, pSceneNormal);
		
		y -= pieceH;
		
		pieces = new ArrayList<BridgePiece>();

		firstStaticBody = PhysicsFactory.createBoxBody(world, x - 105, y, 100, 100, BodyType.StaticBody,
				PhysicsFactory.createFixtureDef(1f, 0.1f, 1));
		lastStaticBody = PhysicsFactory.createBoxBody(world, x + numberOfPieces * pieceW + 5, y, 100, 100, BodyType.StaticBody,
				PhysicsFactory.createFixtureDef(1f, 0.1f, 1));
		firstStaticBody.setUserData("bridgeStatic");
		lastStaticBody.setUserData("bridgeStatic");
		
		firstStaticBody.getFixtureList().get(0).setSensor(true);
		lastStaticBody.getFixtureList().get(0).setSensor(true);
		
		
		for(int i = 0;i < numberOfPieces;i++)
		{
			pieces.add(new BridgePiece(x + i * pieceW + pieceW / 2, y + pieceH / 2, world, mScene, pieceW, pieceH, t));
			
			if(i > 0)
				addJoints(i);
		}
		
		addJoints(0, firstStaticBody);
		addJoints(pieces.size() - 1, lastStaticBody);
	}
	
	public void addJoints(int i)// and i - 1
	{
		RevoluteJointDef mRevoluteJointDef = new RevoluteJointDef();
		
		mRevoluteJointDef.collideConnected = true;
		
		float x1 = pieces.get(i).mBody.getmBody().getWorldCenter().x;
		float x2 = pieces.get(i - 1).mBody.getmBody().getWorldCenter().x;
		
		float y1 = pieces.get(i).mBody.getmBody().getWorldCenter().y;
		float y2 = pieces.get(i - 1).mBody.getmBody().getWorldCenter().y;
		
		mRevoluteJointDef.initialize(pieces.get(i).mBody.getmBody(), pieces.get(i - 1).mBody.getmBody(),
				new Vector2((x1 + x2) / 2, (y1 + y2)/2));
		
		world.createJoint(mRevoluteJointDef);
	}
	
	public void addJoints(int i, Body b)// and i - 1
	{
		RevoluteJointDef mRevoluteJointDef = new RevoluteJointDef();
		
		mRevoluteJointDef.collideConnected = true;
		
		float x1 = pieces.get(i).mBody.getmBody().getWorldCenter().x;
		float x2 = b.getWorldCenter().x;
		
		float y1 = pieces.get(i).mBody.getmBody().getWorldCenter().y;
		float y2 = b.getWorldCenter().y;
		
		mRevoluteJointDef.initialize(pieces.get(i).mBody.getmBody(), b,
				new Vector2((x1 + x2) / 2, (y1 + y2)/2));
		
		world.createJoint(mRevoluteJointDef);
	}
	
	@Override
	public void run(SpriteBatch batch) 
	{
		for(int i = 0;i < pieces.size();i++)
			pieces.get(i).run(batch);
	}
	
	@Override
	public float getPositionX() //for remove! + 100 is for safety
	{
		return pieces.get(pieces.size() - 1).getPositionX() + 5000;
	}

	@Override
	public void dispose(boolean disposeBody) 
	{
		for(int i = 0;i < pieces.size();i++)
		{
			pieces.get(i).dispose(true);
		}
	}
}
