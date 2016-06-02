package Human;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import GameScene.GameManager;
import GameScene.GameScene;
import Misc.BodyStrings;
import Physics.CzakBody;
import PhysicsFactory.FilterCat;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;


public class Hand 
{
	GameScene gameScene;
	GameManager gameManager;

	World mPhysicsWorld;
	
	Human parent;

	public Sprite part1Sprite, part2Sprite;
	
	public CzakBody part1, part2;

	RevoluteJoint joint1;

	float torque;

	FixtureDef handFixture;

	float rat = PhysicsConstant.PIXEL_TO_METER;

	public Hand(Human human, World w)
	{
		this.parent = human;
		gameManager = parent.gameManager;
		gameScene = parent.gameScene;
		
		mPhysicsWorld = w;
	}

	float arm2X, arm2Y;
	float revX, revY;
	float width, height, teta1, teta2;
	float x, y, A2DX, A2DY;
	public void create(float x, float y, float width, float height, float teta1, float teta2, float A2DX, float A2DY,
			float TORQUE, Texture txt1, Texture txt2, boolean DRAW)
	{
		this.A2DX = A2DX;
		this.A2DY = A2DY;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.teta1 = teta1;
		this.teta2 = teta2;
		
		Vector2 arm2Vector = RotateV(x + width, y, teta1, x + width / 2, y + height / 2);
		arm2X = arm2Vector.x;
		arm2Y = arm2Vector.y;

		Vector2 revVector = RotateV(x + width, y + height / 2, teta1, x + width / 2, y + height / 2);
		revX = revVector.x;
		revY = revVector.y;

		torque = TORQUE;
		handFixture = PhysicsFactory.createFixtureDef
				(0.01f, 0.5f, 0.51f, false, FilterCat.CATEGORYBIT_HAND, FilterCat.MASKBITS_HAND, (short)0);

		part1Sprite = new Sprite(txt1);
		part1Sprite.setRotation(teta1);
		part1Sprite.setPosition(x, y);
		part1Sprite.setBounds(x, y, width, height);
		
		part2Sprite = new Sprite(txt2);
		part2Sprite.setBounds(arm2X + A2DX, arm2Y + A2DY, width, height);
		part2Sprite.setOrigin(0f, height);
		part2Sprite.setRotation(teta2);
		part2Sprite.setOrigin(part2Sprite.getWidth() / 2, part2Sprite.getHeight() / 2);

		float midX1 = part1Sprite.getX() + part1Sprite.getWidth() / 2;
		float midY1 = part1Sprite.getY() + part1Sprite.getHeight() / 2;
		
		float midX2 = part2Sprite.getX() + part2Sprite.getWidth() / 2;
		float midY2 = part2Sprite.getY() + part2Sprite.getHeight() / 2;
		
		Body tmp1 = PhysicsFactory.createBoxBody
				(mPhysicsWorld, midX1, midY1, width, height, part1Sprite.getRotation(),
						BodyType.DynamicBody, handFixture, PhysicsConstant.PIXEL_TO_METER);

		part1 = new CzakBody(tmp1, part1Sprite);
		part1.setUserData(BodyStrings.HUMAN_STRING + " " + BodyStrings.Human_HandString);

		Body tmp2 = PhysicsFactory.createBoxBody
				(mPhysicsWorld, midX2, midY2, width, height, part2Sprite.getRotation(),
						BodyType.DynamicBody, handFixture, PhysicsConstant.PIXEL_TO_METER);
		part2 = new CzakBody(tmp2, part2Sprite);
		part2.setUserData(BodyStrings.HUMAN_STRING + " " + BodyStrings.Human_HandString);
		
//		part1.setGravityScale(0);
//		part2.setGravityScale(0);
//		part1.isBullet();
//		part2.isBullet();
		
		addJoints(DRAW);
		
		this.x -= parent.startX;
		this.y -= parent.startY;
	}//create Main
	
	public void setSensor(boolean isSensor)
	{
		part1.getmBody().getFixtureList().get(0).setSensor(isSensor);
		part2.getmBody().getFixtureList().get(0).setSensor(isSensor);
	}

	public void addJoints(boolean DRAW)
	{
		RevoluteJointDef mRevolute = new RevoluteJointDef();
		Vector2 pos1 = new Vector2(revX / rat, revY / rat);

		mRevolute.initialize(part1.getmBody(), part2.getmBody(), pos1);
		mRevolute.collideConnected = false;
		mRevolute.maxMotorTorque = torque;
		joint1 = (RevoluteJoint) mPhysicsWorld.createJoint(mRevolute);
	}

	public Vector2 getPart1JoinVector2()
	{
		return part1.getmBody().getWorldPoint(new Vector2(-part1Sprite.getWidth() / 2 / rat, 0));
	}

	Vector2 RotateV(float x1, float y1, float r, float ox, float oy)
	{
		Vector2 ans = new Vector2(x1, y1);

		double teta = (r * Math.PI / 180f);
		double s = Math.sin(teta);
		double c = Math.cos(teta);

		ans.x -= ox;
		ans.y -= oy;

		double xNew = ans.x * c - ans.y * s;
		double yNew = ans.x * s + ans.y * c;

		ans.x = (float)xNew + ox;
		ans.y = (float)yNew + oy;

		return ans;
	}
	
	public Shape getPart1Shape()
	{
		return part1.getmBody().getFixtureList().get(0).getShape();
	}
	
	public Shape getPart2Shape()
	{
		return part2.getmBody().getFixtureList().get(0).getShape();
	}
	
	public void draw(Batch batch)
	{
		part1.draw(batch);
		part2.draw(batch);
	}

	public void setGravityScale(float scale)
	{
		part1.getmBody().setGravityScale(scale);
		part2.getmBody().setGravityScale(scale);
	}

	public void setBodyType(BodyType bodyType)
	{
		part1.getmBody().setType(bodyType);
		part2.getmBody().setType(bodyType);
	}
}//class
