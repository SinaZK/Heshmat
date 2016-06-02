package Human;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

import BaseCar.BaseCar;
import GameScene.GameManager;
import GameScene.GameScene;
import Misc.BodyStrings;
import Misc.Log;
import Misc.TextureHelper;
import Physics.CzakBody;
import Physics.SizakBody;
import PhysicsFactory.FilterCat;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;


public class  Human
{
	GameManager gameManager;
	GameScene gameScene;
	World mPhysicsWorld;
	OrthographicCamera mCamera;

	public CzakBody mainBody;
	public Sprite mainBodySprite;
	FixtureDef bodyFixtureDef;

	boolean haveCar;
	BaseCar car;

	public Head head;
	public WeldJoint headJoint;
	HumanResizer HR;

	public Hand hand1, hand2;
	public RevoluteJoint hand1Joint, hand2Joint;;
	public Hand leg1, leg2;
	public RevoluteJoint leg1Joint, leg2Joint;;

	public SizakBody sizakBody;

	int weldJointCT = 0;
	public WeldJoint [] outWeldJoint = new WeldJoint[20];
	int RevJointCT = 0;
	public WheelJoint [] outRevJoint = new WheelJoint[20];
	private static float MAX_TORQUE = 1000 * 1000 * 1000;

	float rat = PhysicsConstant.PIXEL_TO_METER;

	public boolean isFree;
	public boolean isContactable;

	public Texture bodyTextureRegion, headTextureRegion, leg1TextureRegion, hand1TextureRegion;
	public Texture neckTextureRegion, leg2TextureRegion, hand2TextureRegion;

	public Human(GameManager gameManager, World w, OrthographicCamera camera)
	{
		this.gameManager = gameManager;
		this.gameScene = gameManager.gameScene;

		mPhysicsWorld = w;
		mCamera = camera;
		
		head = new Head(this, w);

		hand1 = new Hand(this, w);
		hand2 = new Hand(this, w);

		leg1 = new Hand(this, w);
		leg2 = new Hand(this, w);

	}

	public void loadResources(String path)
	{
		bodyTextureRegion = TextureHelper.loadTexture(path + "body.png", gameScene.disposeTextureArray);
		headTextureRegion = TextureHelper.loadTexture(path + "head.png", gameScene.disposeTextureArray);
		leg1TextureRegion = TextureHelper.loadTexture(path + "leg1.png", gameScene.disposeTextureArray);
		leg2TextureRegion = TextureHelper.loadTexture(path + "leg2.png", gameScene.disposeTextureArray);
		hand1TextureRegion = TextureHelper.loadTexture(path + "hand1.png", gameScene.disposeTextureArray);
		hand2TextureRegion = TextureHelper.loadTexture(path + "hand2.png", gameScene.disposeTextureArray);
		neckTextureRegion = TextureHelper.loadTexture(path + "neck.png", gameScene.disposeTextureArray);
	}

	public float startX, startY;

	public void create(float x, float y, String pathToHumanDir, String PathToCarHuman)
	{
		startX = x;
		startY = y;
		
		isContactable = true;

		loadResources(pathToHumanDir);

		HR = new HumanResizer(gameManager);
		HR.loadATT(PathToCarHuman);

		x += HR.fullDX;
		y -= HR.fullDY;

		bodyFixtureDef = PhysicsFactory.createFixtureDef
				(1.0f, 0.5f, 0.51f, false, FilterCat.CATEGORYBIT_HUMAN, FilterCat.MASKBITS_HUMAN, (short)0);

		mainBodySprite = new Sprite(bodyTextureRegion);
		mainBodySprite.setBounds(x, y, HR.bodyWidth, HR.bodyHeight);

		float midX = mainBodySprite.getX() + mainBodySprite.getWidth() / 2;
		float midY = mainBodySprite.getY() - mainBodySprite.getHeight() / 2;

		Body tmpMainBody = PhysicsFactory.createBoxBody(mPhysicsWorld, midX, midY, mainBodySprite.getWidth(),
				mainBodySprite.getHeight(), 0,	BodyType.DynamicBody, bodyFixtureDef, 
				PhysicsConstant.PIXEL_TO_METER);
		mainBody = new CzakBody(tmpMainBody, mainBodySprite);
		mainBody.setUserData(BodyStrings.HUMAN_STRING + " " + BodyStrings.Human_MainBodyString);

		leg1. create(x + HR.leg1DX, y + HR.leg1DY, HR.legWidth, HR.legHeight, HR.leg1Arm1R, HR.leg1Arm2R,
				HR.leg1Arm2DX, HR.leg1Arm2DY,
				0, leg1TextureRegion, leg2TextureRegion, false);

		leg2. create(x + HR.leg2DX, y + HR.leg2DY, HR.legWidth, HR.legHeight, HR.leg2Arm1R, HR.leg2Arm2R,
				HR.leg2Arm2DX, HR.leg2Arm2DY,
				0, leg1TextureRegion, leg2TextureRegion, false);

		hand1.create(x + HR.hand1DX, y + HR.hand1DY, HR.handWidth, HR.handHeight, HR.hand1Arm1R, HR.hand1Arm2R,
				HR.hand1Arm2DX, HR.hand1Arm2DY,
				0, hand1TextureRegion, hand2TextureRegion, false);

		hand2.create(x + HR.hand2DX, y + HR.hand2DY, HR.handWidth, HR.handHeight, HR.hand2Arm1R, HR.hand2Arm2R,
				HR.hand2Arm2DX, HR.hand2Arm2DY,
				0, hand1TextureRegion, hand2TextureRegion, false);

		head.create(x + 7 + HR.headDX, y - 3 + HR.headDY,
				HR.neckWidth, HR.neckHeight * 5, HR.headRadius, headTextureRegion, neckTextureRegion);

		sizakBody = new SizakBody();
		sizakBody.addBody(mainBody);
		sizakBody.addBody(head.headBody);
		sizakBody.addBody(head.neckBody);
		sizakBody.addBody(hand1.part1);
		sizakBody.addBody(hand1.part2);
		sizakBody.addBody(hand2.part1);
		sizakBody.addBody(hand2.part2);
		sizakBody.addBody(leg1.part1);
		sizakBody.addBody(leg1.part2);
		sizakBody.addBody(leg2.part1);
		sizakBody.addBody(leg2.part2);

		addALLJoints();
		sizakBody.addJoint(hand1Joint);
		sizakBody.addJoint(hand2Joint);
		sizakBody.addJoint(leg1Joint);
		sizakBody.addJoint(leg2Joint);
		sizakBody.addJoint(headJoint);
	}

	public void addALLJoints()
	{
		hand1Joint = addJoints(hand1);
		hand2Joint = addJoints(hand2);
		leg1Joint = addJoints(leg1);
		leg2Joint = addJoints(leg2);
		addJoints(head);

	}

	public void setSensor(boolean isSensor)
	{
		mainBody.getmBody().getFixtureList().get(0).setSensor(isSensor);

		hand1.setSensor(isSensor);
		hand2.setSensor(isSensor);
		leg1.setSensor(isSensor);
		leg2.setSensor(isSensor);

		head.setSensor(isSensor);
	}

	public RevoluteJoint addJoints(Hand h)
	{
		RevoluteJointDef mRevolute = new RevoluteJointDef();
		mRevolute.initialize(mainBody.getmBody(), h.part1.getmBody(), h.getPart1JoinVector2());
		mRevolute.collideConnected = false;
		mRevolute.enableMotor = false;
		return (RevoluteJoint) mPhysicsWorld.createJoint(mRevolute);
	}

	public void addJoints(Head h)
	{
		WeldJointDef mRevolute = new WeldJointDef();
		mRevolute.initialize(mainBody.getmBody(), h.neckBody.getmBody(), h.getNeckJointVector2());
		mRevolute.collideConnected = false;

		headJoint = (WeldJoint) mPhysicsWorld.createJoint(mRevolute);
	}

	public Vector2 getHandJointPos(Hand hand)
	{
		return hand.getPart1JoinVector2();
	}

	public void attachWithWeld(Hand hand, Body b)
	{
		WeldJointDef weld = new WeldJointDef();
		weld.initialize(hand.part1.getmBody(), b, hand.getPart1JoinVector2());
		weld.collideConnected = false;

		outWeldJoint[weldJointCT++] = (WeldJoint) mPhysicsWorld.createJoint(weld);


		weld = new WeldJointDef();
		weld.initialize(hand.part2.getmBody(), b, hand.getPart1JoinVector2());
		weld.collideConnected = false;

		outWeldJoint[weldJointCT++] = (WeldJoint) mPhysicsWorld.createJoint(weld);
	}

	public void attachWithRev(Hand hand, Body b)
	{
		WheelJointDef weld = new WheelJointDef();
		weld.initialize(hand.part2.getmBody(), b, hand.getPart1JoinVector2(), new Vector2(0.0f, 1.0f));
		weld.collideConnected = false;
		weld.enableMotor = true;
		weld.maxMotorTorque = MAX_TORQUE;
		weld.motorSpeed = 0;
		weld.frequencyHz = 10;

		outRevJoint[RevJointCT++] = (WheelJoint) mPhysicsWorld.createJoint(weld);

		weld = new WheelJointDef();
		weld.initialize(hand.part1.getmBody(), b, hand.getPart1JoinVector2(), new Vector2(0.0f, 1.0f));
		weld.collideConnected = false;
		weld.enableMotor = true;
		weld.maxMotorTorque = MAX_TORQUE;
		weld.motorSpeed = 0;
		weld.frequencyHz = 10;

		outRevJoint[RevJointCT++] = (WheelJoint) mPhysicsWorld.createJoint(weld);
	}

	public void attachWithWeld(Head head, Body b)
	{
		WeldJointDef weld = new WeldJointDef();
		weld.initialize(head.headBody.getmBody(), b, head.getNeckJointVector2());
		weld.collideConnected = false;

		outWeldJoint[weldJointCT++] = (WeldJoint) mPhysicsWorld.createJoint(weld);
	}

	public void attachWithRev(Head head, Body b)
	{
		WheelJointDef weld = new WheelJointDef();
		weld.initialize(head.headBody.getmBody(), b, head.getNeckJointVector2(), new Vector2(0.0f, 1.0f));
		weld.collideConnected = false;
		weld.enableMotor = true;
		weld.maxMotorTorque = MAX_TORQUE;
		weld.motorSpeed = 0;
		weld.frequencyHz = 10;

		outRevJoint[RevJointCT++] = (WheelJoint) mPhysicsWorld.createJoint(weld);
	}

	public void attachWithWeld(Body b)
	{
		if(b == null)
		{
			return;
		}
		
		if(mainBody.getmBody() == null)
		{
			return;
		}
		
		WeldJointDef weld = new WeldJointDef();
		weld.initialize(mainBody.getmBody(),
				b, mainBody.getmBody().getWorldCenter());
		weld.collideConnected = false;

		outWeldJoint[weldJointCT++] = (WeldJoint) mPhysicsWorld.createJoint(weld);
	}

	public void attachWithRev(Body b)
	{
		WheelJointDef weld = new WheelJointDef();
		weld.initialize(mainBody.getmBody(), b, mainBody.getmBody().getWorldCenter(), new Vector2(0.0f, 1.0f));
		weld.collideConnected = false;
		weld.enableMotor = true;
		weld.maxMotorTorque = MAX_TORQUE;
		weld.motorSpeed = 0;
		weld.frequencyHz = 10;

		outRevJoint[RevJointCT++] = (WheelJoint) mPhysicsWorld.createJoint(weld);
	}

	public void attachToCar(BaseCar car)
	{
		this.car = car;
		haveCar = true;

		attachWithWeld(car.body.bodies.get(0).getmBody());
		attachWithWeld(hand1, car.body.bodies.get(0).getmBody());
		attachWithWeld(hand2, car.body.bodies.get(0).getmBody());
		attachWithWeld(leg1, car.body.bodies.get(0).getmBody());
		attachWithWeld(leg2, car.body.bodies.get(0).getmBody());

		isFree = false;

		setSensor(true);

		if(car.carType == BaseCar.CarType.MotorCycle)
			head.setSensor(false);
	}

	public void lockCameraOn()
	{
		Log.e("Tag", "LOCKING CAMERA LATER");
	}

	public void freeTheHuman(boolean shoot)
	{
		if(isFree)
			return;

		isFree = true;
		haveCar = false;

		addALLJoints();

		for(int i = 0;i < weldJointCT;i++)
		{
			final int I = i;

			if(outWeldJoint[i] != null)
			{
				mPhysicsWorld.destroyJoint(outWeldJoint[I]);
				outWeldJoint[I] = null;
			}//if
		}//for

		for(int i = 0;i < RevJointCT;i++)
		{
			final int I = i;

			if(outRevJoint[i] != null)
			{
				mPhysicsWorld.destroyJoint(outRevJoint[I]);
				outRevJoint[I] = null;
			}//if
		}//for

		if(shoot)
			shootTheHuman();

		//		lockCameraOn();

		car.isDead = true;

	}//Free

	public void shootTheHuman()
	{
		float VX = car.body.bodies.get(0).getmBody().getLinearVelocity().x;
		float VY = car.body.bodies.get(0).getmBody().getLinearVelocity().y;

		Vector2 SPEED = new Vector2(12, -5);
		SPEED.rotate(car.body.bodies.get(0).getmBody().getAngle() * 180f / (float)Math.PI);

		mainBody.getmBody().setLinearVelocity(VX + SPEED.x, VY + SPEED.y);
		head.neckBody.getmBody().setLinearVelocity(VX + SPEED.x, VY + SPEED.y);
		head.headBody.getmBody().setLinearVelocity(VX + SPEED.x, VY + SPEED.y);
		hand1.part1.getmBody().setLinearVelocity(VX + SPEED.x, VY + SPEED.y);
		hand2.part1.getmBody().setLinearVelocity(VX + SPEED.x, VY + SPEED.y);
		leg1.part2.getmBody().setLinearVelocity(VX + SPEED.x, VY + SPEED.y);
		leg2.part2.getmBody().setLinearVelocity(VX + SPEED.x, VY + SPEED.y);
	}
	
	public void kill()
	{
		head.joint.setLimits(-70 * (float) Math.PI / 180f, 70 * (float) Math.PI / 180f);
	}
	
	public void draw(Batch batch)
	{
		mainBody.draw(batch);
		hand1.draw(batch);
		hand2.draw(batch);
		head.draw(batch);
		leg1.draw(batch);
		leg2.draw(batch);
	}

	public void setGravityScale(float gravityScale)
	{
		mainBody.getmBody().setGravityScale(gravityScale);
		hand1.setGravityScale(gravityScale);
		hand2.setGravityScale(gravityScale);
		leg1.setGravityScale(gravityScale);
		leg2.setGravityScale(gravityScale);
		head.setGravityScale(gravityScale);
	}

	public void dispose()
	{
	}

	public void setCenterPosition(float cX, float cY)
	{
		float worldX = cX / PhysicsConstant.PIXEL_TO_METER;
		float worldY = cY / PhysicsConstant.PIXEL_TO_METER;
		sizakBody.setCenterPosition(worldX, worldY);
	}

	public void setBodyType(BodyType bodyType)
	{
		mainBody.getmBody().setType(bodyType);
		head.setBodyType(bodyType);
		hand1.setBodyType(bodyType);
		hand2.setBodyType(bodyType);
		leg1.setBodyType(bodyType);
		leg2.setBodyType(bodyType);
	}

}//Class
