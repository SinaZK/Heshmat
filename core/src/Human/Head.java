package Human;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import GameScene.GameManager;
import GameScene.GameScene;
import Misc.BodyStrings;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.FilterCat;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;


public class Head 
{
	GameManager gameManager;
	GameScene gameScene;
	World mPhysicsWorld;
	Human parent;
	
	CzakBody headBody;
	public CzakBody neckBody;
	public Sprite neckBodySprite, headSprite;
	Vector2 headCenter;
	float headRad;

	RevoluteJoint joint;

	FixtureDef headFixtureDef;
	float rat = PhysicsConstant.PIXEL_TO_METER;

	public Head(Human parent, World w)
	{
		this.parent = parent;
		gameManager = parent.gameManager;
		gameScene = parent.gameScene;
		mPhysicsWorld = w;
	}

	public void create(float x, float y, float width, float height, float Radius, Texture headTXT,
			Texture neckTXT)
	{
		headFixtureDef = PhysicsFactory.createFixtureDef
				(0.01f, 0.5f, 0.51f, false, FilterCat.CATEGORYBIT_HEAD, FilterCat.MASKBITS_HEAD, (short)0);

		neckBodySprite = new Sprite(neckTXT);
		neckBodySprite.setBounds(x, y, width, height);
		
		float midX1 = neckBodySprite.getX() + neckBodySprite.getWidth() / 2;
		float midY1 = neckBodySprite.getY() + neckBodySprite.getHeight() / 2;
		
		Body tmpneckBody = PhysicsFactory.createBoxBody(mPhysicsWorld, midX1, midY1, neckBodySprite.getWidth(),
				neckBodySprite.getHeight(), 0f,
				BodyType.DynamicBody, headFixtureDef, PhysicsConstant.PIXEL_TO_METER);
		neckBody = new CzakBody(tmpneckBody, neckBodySprite);
		neckBody.setUserData(BodyStrings.HUMAN_STRING + " " + BodyStrings.Human_NeckString);

		headRad = Radius;
		headCenter = new Vector2( (x + width / 2), (y - headRad));
		
		float headX = headCenter.x - headRad;
		float midX2 = headX + headRad / 2;
		
		headSprite = new Sprite(headTXT);
		headSprite.setBounds(headCenter.x - headRad, headCenter.y + headRad, 2 * headRad, 2 * headRad);
		
		float midY2 = headSprite.getY() + headRad / 2 + headRad;
		
		
		Body tmpHeadBody = PhysicsFactory.createCircleBody(mPhysicsWorld, midX2, midY2, 
				headRad, BodyType.DynamicBody, headFixtureDef, PhysicsConstant.PIXEL_TO_METER);
		headBody = new CzakBody(tmpHeadBody, headSprite);
		headBody.setUserData(BodyStrings.HUMAN_STRING + " " + BodyStrings.Human_HeadString);
		
		
		addJoints();
	}//create
	
	public void setSensor(boolean isSensor)
	{
		neckBody.getmBody().getFixtureList().get(0).setSensor(isSensor);
		headBody.getmBody().getFixtureList().get(0).setSensor(isSensor);
	}

	public void addJoints()
	{
		RevoluteJointDef mRevolute = new RevoluteJointDef();
		
		Vector2 pos = headBody.getmBody().getWorldPoint(new Vector2(0, -headRad / rat));
		
		mRevolute.initialize(headBody.getmBody(), neckBody.getmBody(), pos);
		mRevolute.collideConnected = false;
		mRevolute.enableLimit = true;
		mRevolute.lowerAngle = -5 * (float) (Math.PI / 180);
		mRevolute.upperAngle = 5 * (float) (Math.PI / 180);
//		mRevolute.enableMotor = true;
//		mRevolute.motorSpeed = 1;
//		mRevolute.maxMotorTorque = 1000 * 1000 * 1000;
		joint = (RevoluteJoint) mPhysicsWorld.createJoint(mRevolute);
	}

	public Vector2 getNeckJointVector2()
	{
		return neckBody.getmBody().getWorldPoint(new Vector2(neckBodySprite.getWidth() / 2 / rat, 0)) ;
	}

	public void setZindex(int Z)
	{
		Log.e("Tag", "WARNING!!!!! SETZINDEX ON HEAD");
	}
	
	public void draw(Batch batch)
	{
		headBody.draw(batch);
//		neckBody.draw(batch);
	}

	public void setGravityScale(float scale)
	{
		headBody.getmBody().setGravityScale(scale);
		neckBody.getmBody().setGravityScale(scale);
	}

	public void setBodyType(BodyType bodyType)
	{
		headBody.getmBody().setType(bodyType);
		neckBody.getmBody().setType(bodyType);
	}
}
