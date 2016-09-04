package Cars.Tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import java.util.ArrayList;

import BaseCar.AttachPart;
import BaseCar.NormalCar;
import DataStore.CarStatData;
import Entity.AnimatedSpriteSheet;
import GameScene.GameManager;
import GameScene.GameScene;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import Misc.TextureHelper;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;

/**
 * Created by sinazk on 8/23/16.
 * 18:19
 */
public class Tank extends NormalCar
{
	public static boolean STATIC = false;

	public Tank(GameManager gm, CarStatData carStatData)
	{
		super(gm, carStatData);
	}

	boolean isReset = false;

	Sprite testSprite;
	float chainWidth = 23;
	float offset = 0;

	float rat = PhysicsConstant.PIXEL_TO_METER;
	ArrayList<Vector2> texturePoints = new ArrayList<Vector2>();
	ArrayList<Vector2> textureActualPoints = new ArrayList<Vector2>();

	public void calcTexturePoints()
	{
		texturePoints.clear();

		//UpChains
		for (int i = 0; i <= 0; i++)
		{
			CzakBody czakBody = body.getBodyByName("upChain");

			float degAngle = (float) Math.toDegrees(czakBody.getmBody().getAngle());
			Vector2 pos = czakBody.getmBody().getWorldCenter();
			pos.scl(rat, rat);

			Vector2 leftDown = new Vector2(pos.x - upChainSize[i].x / 2, pos.y + upChainSize[i].y / 2);
			leftDown = CameraHelper.rotatePoint(pos.x, pos.y, degAngle, leftDown);

			Vector2 rightDown = new Vector2(pos.x + upChainSize[i].x / 2, pos.y + upChainSize[i].y / 2);
			rightDown = CameraHelper.rotatePoint(pos.x, pos.y, degAngle, rightDown);
			texturePoints.add(rightDown);
			texturePoints.add(leftDown);
			//UpChains
		}

		//left Circles
		for (int i = 2; i >= 1; i--)
		{
			Vector2 v = body.getBodyByName("lc" + i).getmBody().getWorldCenter();
			v.scl(rat, rat);

			texturePoints.add(v);
		}
		//left Circles

		//DownChains
		for (int i = 1; i <= 8; i++)
		{
			CzakBody czakBody = body.getBodyByName("downChain" + i);

			float degAngle = (float) Math.toDegrees(czakBody.getmBody().getAngle());
			Vector2 pos = czakBody.getmBody().getWorldCenter();
			pos.scl(rat, rat);

			Vector2 leftDown = new Vector2(pos.x - downChainSize[i].x / 2, pos.y - downChainSize[i].y / 2);
			leftDown = CameraHelper.rotatePoint(pos.x, pos.y, degAngle, leftDown);

			Vector2 rightDown = new Vector2(pos.x + downChainSize[i].x / 2, pos.y - downChainSize[i].y / 2);
			rightDown = CameraHelper.rotatePoint(pos.x, pos.y, degAngle, rightDown);

			texturePoints.add(leftDown);
			if(i == 10)
				texturePoints.add(rightDown);
		}
		//DownChains

		//Right Circles
		for (int i = 1; i <= 3; i++)
		{
			Vector2 v = body.getBodyByName("rc" + i).getmBody().getWorldCenter();
			v.scl(rat, rat);

			texturePoints.add(v);
		}
		//Right Circles

		textureActualPoints.clear();
		textureActualPoints = TextureMath.getNormalizedPointsBySize(texturePoints, chainWidth, offset);
//		textureActualPoints = TextureMath.getNormalizedPoints(texturePoints, 20, 0);
	}

//	static float downChainWidth =

	boolean isRestart = false;

	@Override
	public void create()
	{
		super.create();

		partSheet = new AnimatedSpriteSheet("gfx/car/8/misc.png", gameScene.disposeTextureArray);
		partSheet.addAnimation("gunSprite", 0, 27, 349, 98, 2, 1, -1);
		partSheet.addAnimation("part1", 407, 176, 524, 249, 1, 3, -1);
		partSheet.addAnimation("part2", 475, 5, 553, 75, 1, 3, -1);
		partSheet.addAnimation("part3", 398, 0, 458, 78, 1, 3, -1);
		partSheet.addAnimation("part4", 410, 104, 526, 161, 1, 3, -1);
		addParts();
		chainSpriteTexture = TextureHelper.loadTexture("gfx/car/8/chain.png", gameScene.disposeTextureArray);

		testSprite = new Sprite(chainSpriteTexture);
		testSprite.setSize(chainWidth, (chainWidth / testSprite.getWidth()) * testSprite.getHeight());


		wheelSprite = new Sprite(TextureHelper.loadTexture("gfx/car/8/w.png", gameScene.disposeTextureArray));

		setLimitForJoint("leftRevolute", -10, 10, true);
		setLimitForJoint("rightRevolute", -10, 10, true);
		setLimitForJoint("gunRevolute", -5, 0, false);


		for (int i = 1; i <= 2; i++)
			setLimitForJoint("lr" + i, -10, 10, true);
		for (int i = 1; i <= 3; i++)
			setLimitForJoint("rrc" + i, -10, 10, true);

		offset = 0;

		for (int i = 0; i < body.bodies.size(); i++)
		{
			body.bodies.get(i).getmBody().setBullet(true);
			body.bodies.get(i).getmBody().setSleepingAllowed(false);
		}


		if(STATIC)
			for (int i = 0; i < 1; i++)
				body.bodies.get(i).getmBody().setType(BodyDef.BodyType.StaticBody);
	}

	AttachPart part1, part2, part3, part4;

	public void addParts()
	{
		body.getBodyByName("gunBody").addSprite(partSheet.getAnimation("gunSprite").sprites[0]);

		part1 = new AttachPart(this);
		part1.create(-320, 35, 38, 77, 0, true);
		part1.addSprite(partSheet.getAnimation("part1").sprites[0], true);
		part1.addSprite(partSheet.getAnimation("part1").sprites[2], true);
		part1.setPercent(30, 10);
		attachParts.add(part1);


		part2 = new AttachPart(this);
		part2.create(-230, 158, 26, 73, 0, true);
		part2.addSprite(partSheet.getAnimation("part2").sprites[0], true);
		part2.addSprite(partSheet.getAnimation("part2").sprites[2], true);
		part2.setPercent(50, 30);
		attachParts.add(part2);

		part3 = new AttachPart(this);
		part3.create(-208, 170, 20, 78, 0, true);
		part3.addSprite(partSheet.getAnimation("part3").sprites[0], true);
		part3.addSprite(partSheet.getAnimation("part3").sprites[2], true);
		part3.setPercent(80, 50);
		attachParts.add(part3);

		part4 = new AttachPart(this);
		part4.create(361, 1, 38, 58, 0, true);
		part4.addSprite(partSheet.getAnimation("part4").sprites[0], true);
		part4.addSprite(partSheet.getAnimation("part4").sprites[2], true);
		part4.setPercent(90, 80);
		attachParts.add(part4);
	}

	int ct = 0;


	@Override
	public void drawBody(Batch batch)
	{
		body.flushSprites();

		for (int i = 0; i < attachParts.size(); i++)
			attachParts.get(i).draw(batch);

		offset += tankChainSpeed;

		for (int i = 1; i <= 6; i += 1)
			drawWheel(batch, i, 30, 20);
//		drawWheelAtCircleBody(batch, "wheel6", 25, 15, 30);
		drawWheelAtCircleBody(batch, "leftWheel", 20, 0, 0);
		drawWheelAtCircleBody(batch, "rightWheel", 20, 0, 0);


//		drawTexturePoints(batch);

		if(Math.abs(getSpeedInPixel()) > 1)
		calcTexturePoints();
		drawActualTexturePoints(batch);

		ct++;
		if(ct > 5000)
		{
			for (int i = 0; i < body.bodies.size(); i++)
				body.bodies.get(i).getmBody().setType(BodyDef.BodyType.DynamicBody);
		}

		body.getBodyByName("gunBody").draw(batch);
		for (int i = 0; i < body.bodies.size(); i++)//wheels
		{
			if(body.bodies.get(i).bodyName.equals("gunBody"))
				continue;
			body.bodies.get(i).draw(batch);
		}
	}

	public void drawWheel(Batch batch, int i, float radius, float yOffset)
	{
		Body b = body.getBodyByName("wheel" + i).getmBody();

		Vector2 pos = b.getWorldCenter();
		pos.scl(rat, rat);

		wheelSprite.setOrigin(radius, radius);
		wheelSprite.setRotation(offset);
		wheelSprite.setSize(2 * radius, 2 * radius);
		wheelSprite.setPosition(pos.x - radius, pos.y - radius + yOffset);

		wheelSprite.draw(batch);
	}

	public void drawWheelAtCircleBody(Batch batch, String bodyName, float radius, float xOffset, float yOffset)
	{
		Body b = body.getBodyByName(bodyName).getmBody();

		Vector2 pos = b.getWorldCenter();
		pos.scl(rat, rat);

		wheelSprite.setOrigin(radius, radius);
		wheelSprite.setRotation(offset);
		wheelSprite.setSize(2 * radius, 2 * radius);
		wheelSprite.setPosition(pos.x - radius + xOffset, pos.y - radius + yOffset);

		wheelSprite.draw(batch);
	}

	public void drawTexturePoints(Batch batch)
	{
		for (int i = 0; i < textureActualPoints.size(); i++)
		{
			testSprite.setPosition(textureActualPoints.get(i).x, textureActualPoints.get(i).y);
			testSprite.setSize(15, 15);

			testSprite.setColor(1, 0, 0, 1);
			testSprite.draw(batch);
			testSprite.setColor(1, 1, 1, 1);
//			gameScene.font14.draw(batch, "" + i, testSprite.getX(), testSprite.getY());
		}
	}

	public void drawActualTexturePoints(Batch batch)
	{
		for (int i = 0; i < textureActualPoints.size() - 1; i++)
		{
			Vector2 p0 = textureActualPoints.get(i);
			Vector2 p1 = textureActualPoints.get(i + 1);
			float angle = (float) Math.atan2(p1.y - p0.y, p1.x - p0.x);

			testSprite.setOrigin(0, 0);
			testSprite.setRotation((float) Math.toDegrees(angle));
			testSprite.setPosition(textureActualPoints.get(i).x, textureActualPoints.get(i).y);
			testSprite.setSize(chainWidth, 13);
			testSprite.draw(batch);
		}

		if(textureActualPoints.size() < 1)
			return;

		Vector2 p0 = textureActualPoints.get(textureActualPoints.size() - 1);
		Vector2 p1 = textureActualPoints.get(0);
		float angle = (float) Math.atan2(p1.y - p0.y, p1.x - p0.x);

		testSprite.setOrigin(0, 0);
		testSprite.setRotation((float) Math.toDegrees(angle));
		testSprite.setPosition(textureActualPoints.get(textureActualPoints.size() - 1).x, textureActualPoints.get(textureActualPoints.size() - 1).y);
		testSprite.setSize(chainWidth, 13);
		testSprite.draw(batch);
	}

	AnimatedSpriteSheet partSheet;
	Texture chainSpriteTexture;
	Sprite wheelSprite;

	static float MAX_CHAIN_SPEED = 30;
	static float CHAIN_SPEED_STEP = 4;
	float tankChainSpeed = 0;
	static float minAngle = (float) Math.toRadians(-135);
	static float maxAngle = (float) Math.toRadians(135);

	@Override
	public void hitByGroundPreSolve(Contact contact, Fixture groundFixture, Fixture carFixture, String carFixtureString)
	{
		WorldManifold worldManifold = contact.getWorldManifold();
		float surfaceVelocityModifier = 0;

		if(shouldStop)
			return;

		boolean isWheel = isChain(carFixtureString) || isWheelString(carFixtureString);
		if(!isWheel)
			return;

		Vector2 localNormal = carFixture.getBody().getLocalVector(worldManifold.getNormal());
		float angle = (float) Math.atan2(localNormal.y, localNormal.x);
		if(minAngle < angle && angle < maxAngle)
			surfaceVelocityModifier += tankChainSpeed;

		contact.setTangentSpeed(surfaceVelocityModifier);
	}

	boolean isChain(String data)
	{
		if(BodyStrings.getPartOf(data, 1) == null)
			return false;

		String part = BodyStrings.getPartOf(data, 1);

		return part.contains("downChain");
	}

	boolean isWheelString(String data)
	{
		if(BodyStrings.getPartOf(data, 1) == null)
			return false;

		String part = BodyStrings.getPartOf(data, 1);

		return part.contains("wheel");
	}


	@Override
	public void gas(float rate)
	{
		if(shouldStop)
			return;
		tankChainSpeed = (MAX_CHAIN_SPEED + carStatData.engineLVL * CHAIN_SPEED_STEP) * rate;


//		Log.e("Tank.java", "GAS = " + tankChainSpeed);
	}

	@Override
	public void brake(float rate)
	{
		tankChainSpeed = -(MAX_CHAIN_SPEED + carStatData.engineLVL * CHAIN_SPEED_STEP) * rate;

//		Log.e("Tank.java", "BRAKE = " + tankChainSpeed);
	}

	@Override
	public void relax()
	{
		tankChainSpeed = 0;
	}

	@Override
	public boolean isInAir()
	{
		return false;
	}

	@Override
	public void label()
	{
		body.setCar();

		isLabeled = true;
	}

	public void setLimitForJoint(String name, float lower, float upper, boolean enableMotor)
	{
		RevoluteJoint revoluteJoint1 = (RevoluteJoint) body.getJointByName(name);
		revoluteJoint1.enableLimit(true);
		revoluteJoint1.setLimits((float) Math.toRadians(lower), (float) Math.toRadians(upper));
		revoluteJoint1.enableMotor(enableMotor);
		revoluteJoint1.setMotorSpeed(0);
	}

	Vector2[] downChainSize =
			{
					null,
					new Vector2(104, 18),//1
					new Vector2(70, 20),//2
					new Vector2(70, 20),//3
					new Vector2(70, 20),//4
					new Vector2(70, 20),//5
					new Vector2(70, 20),//6
					new Vector2(70, 20),//7
					new Vector2(114, 20),//8

			};

	Vector2[] upChainSize =
			{
					new Vector2(586, 18),//0
			};

	@Override
	public void runOnGround(boolean isGas, boolean isBrake)
	{
		if(gameScene.gameManager.levelManager.currentLevel.getCurrentPart().mode == GameScene.LevelModeEnum.Shooting)
			return;

		if(isGas)
			gas(1);

		if(isBrake)
			brake(1);
	}

	@Override
	public void reset()
	{
		super.reset();

		isReset = true;
	}

	@Override
	public void onStop()
	{
		if(getSpeedInPixel() < 0 || getSpeedInPixel() < 0.1f)
		{
			return;
		} else
		{
			body.bodies.get(0).getmBody().applyForceToCenter(-10, 0, true);
//			body.bodies.get(0).setType(BodyDef.BodyType.StaticBody);
//			Log.e("Tank.java", "ONSTOP BRAKE SPEED = " + getSpeedInPixel());
		}
	}

	@Override
	public boolean isStopped()
	{
		for (int i = 0; i < 1; i++)
		{
			if(!isBodyStopped(body.bodies.get(i).getmBody(), 0.1f))
				return false;
		}

		float angle = convertAngleToDegrees();

		if(angle > 30 || angle < -30)
			return false;

		if(isInAir())
			return false;

		return true;
	}

	@Override
	public void run(boolean isGas, boolean isBrake, float rate)
	{
		if(body.getBodyByName("mainBody").getmBody().getType() == BodyDef.BodyType.DynamicBody)
			if(isReset)
			{
				isReset = false;

				clearAttachParts();
				addParts();
			}


		super.run(isGas, isBrake, rate);
	}
}
