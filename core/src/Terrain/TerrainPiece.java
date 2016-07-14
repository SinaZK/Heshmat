package Terrain;


import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import Misc.BodyStrings;
import Misc.CameraHelper;
import PhysicsFactory.PhysicsFactory;
import heshmat.MainActivity;

public class TerrainPiece
{
	MainActivity act;
	Terrain mTerrain;
	World mPhysicsWorld;
	PolygonRegion texturePolygon, grassTexturedPolygon;
	float grassHeight = 33;
	float grassYOffset = 0;

	float x1, y1, x2, y2;

	public Body mBody;

	float TEXTURE_HEIGHT = 1600;

	public TerrainPiece(MainActivity pActivity, Terrain pTerrain, World pWorld) 
	{
		act = pActivity;
		mTerrain = pTerrain;
		mPhysicsWorld = pWorld;
		mTerrain.polygonSpriteBatch = mTerrain.polygonSpriteBatch;
	}

	public Body create(float pX1, float pY1, float pX2, float pY2)
	{
		x1 = pX1;
		y1 = pY1;
		x2 = pX2;
		y2 = pY2;

		mBody = PhysicsFactory.createLineBody(mPhysicsWorld, x1, y1, x2, y2,
				PhysicsFactory.createFixtureDef(1, 0.2f, 0.7f));
		mBody.setUserData(BodyStrings.GroundString);

		float [] VX = new float[4];
		float [] VY = new float[4];
		VX[0] = x1;
		VY[0] = y1;

		VX[1] = x2;
		VY[1] = y2;

		VX[2] = x2;
		VY[2] = y2 - TEXTURE_HEIGHT;

		VX[3] = x1;
		VY[3] = y1 - TEXTURE_HEIGHT;

		float [] vertices = new float[8];
		for(int i = 0;i < 4;i++)
		{
			vertices[i * 2]     = VX[i];
			vertices[i * 2 + 1] = VY[i];
		}

		texturePolygon = new PolygonRegion(new TextureRegion(mTerrain.repeatingTextureRegion), vertices,
				new EarClippingTriangulator().computeTriangles(vertices).toArray());
		
		VX[0] = x1;
		VY[0] = y1 + grassYOffset;

		VX[1] = x2;
		VY[1] = y2 + grassYOffset;

		VX[2] = x2;
		VY[2] = y2 - grassHeight + grassYOffset;

		VX[3] = x1;
		VY[3] = y1 - grassHeight + grassYOffset;

		float [] vertices2 = new float[8];
		for(int i = 0;i < 4;i++)
		{
			vertices2[i * 2]     = VX[i];
			vertices2[i * 2 + 1] = VY[i];
		}

		
		grassTexturedPolygon = new PolygonRegion(new TextureRegion(mTerrain.upTextureRegion), vertices2,
				new EarClippingTriangulator().computeTriangles(vertices2).toArray());
		
		
		return mBody;
	}

	public void drawFirst()//asli
	{
		float last  = CameraHelper.getXMax(mTerrain.mCamera) + 100;
		float first = CameraHelper.getXMin(mTerrain.mCamera) - 100;

		float firstX = texturePolygon.getVertices()[0];
		float lastX = firstX + mTerrain.xSize;

		boolean firstIf  = (last >= firstX) && (first <= firstX);
		boolean secondIf = (last >= lastX) && (first <= lastX);

		if(firstIf && secondIf)
		{
			mTerrain.polygonSpriteBatch.draw(texturePolygon, 0, 0);
		}
	}

	public void drawSecond()//grass
	{
		float last  = CameraHelper.getXMax(mTerrain.mCamera) + 20;
		float first = CameraHelper.getXMin(mTerrain.mCamera) - 20;

		float firstX = texturePolygon.getVertices()[0];
		float lastX = firstX + mTerrain.xSize;

		boolean firstIf  = (last >= firstX) && (first <= firstX);
		boolean secondIf = (last >= lastX) && (first <= lastX);

		if(firstIf && secondIf)
			mTerrain.polygonSpriteBatch.draw(grassTexturedPolygon, 0, 0);
	}

	public void dispose(boolean withBody)
	{
		if(withBody)
			if(mBody != null && mPhysicsWorld != null)
				mPhysicsWorld.destroyBody(mBody);
		
		act = null;
		mTerrain = null;
		texturePolygon = null;
		grassTexturedPolygon = null;
		mBody = null;
	}
}
