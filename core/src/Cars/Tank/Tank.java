package Cars.Tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import BaseCar.NormalCar;
import DataStore.CarStatData;
import Entity.AnimatedSpriteSheet;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.Log;
import Misc.TextureHelper;
import Physics.CzakBody;

/**
 * Created by sinazk on 8/23/16.
 * 18:19
 */
public class Tank extends NormalCar
{
	static boolean STATIC = true;

	public Tank(GameManager gm, CarStatData carStatData)
	{
		super(gm, carStatData);
	}

	@Override
	public void create()
	{
		super.create();

		Log.e("Tank.java", "BodySize = " + body.bodies.size());

//		setLimitForJoint("rightRevolute", -5, 5, true);
//		setLimitForJoint("leftRevolute", -5, 5, true);


//		for(int i = 1;i <= 9;i++)
//			setLimitForJoint("r" + i, -5, 5, false);

//		setLimitForJoint("ldr", -5, 5, false);
//		setLimitForJoint("rdr", -5, 5, false);

		for (int i = 1; i <= 8; i++)
		{
			CzakBody wheel = body.getBodyByName("wheel" + i);
			Sprite wheelSprite = new Sprite(TextureHelper.loadTexture("gfx/car/8/w.png", gameScene.disposeTextureArray));
			wheelSprite.setSize(26, 26);

			wheel.addSprite(wheelSprite);
		}

//		for(int i = 1;i <= 10;i++)
//		{
//			String chainName = "downChain" + i;
//			CzakBody b = body.getBodyByName(chainName);
//
//			Log.e("Tank.java", "User" + i + ": " + b.getUserData());
//			b.setUserData((String) b.getUserData() + "downChain");
//		}


		if(STATIC)
			for (int i = 0; i < body.bodies.size(); i++)
				body.bodies.get(i).getmBody().setType(BodyDef.BodyType.StaticBody);
	}

	public void setLimitForJoint(String name, float lower, float upper, boolean enableMotor)
	{
		RevoluteJoint revoluteJoint1 = (RevoluteJoint) body.getJointByName(name);
		revoluteJoint1.enableLimit(true);
		revoluteJoint1.setLimits((float) Math.toRadians(lower), (float) Math.toRadians(upper));
		revoluteJoint1.enableMotor(enableMotor);
	}

	int ct = 0;

	@Override
	public void drawBody(Batch batch)
	{
		super.drawBody(batch);

//		RevoluteJoint revoluteJoint1 = (RevoluteJoint)body.getJointByName("ldr");
//		Log.e("Tank.java", "LeftRev angle = " + Math.toDegrees(revoluteJoint1.getJointAngle()));
//		revoluteJoint1 = (RevoluteJoint)body.getJointByName("rdr");
//		Log.e("Tank.java", "rightRev angle = " + Math.toDegrees(revoluteJoint1.getJointAngle()));

		ct++;

		if(ct > 100)
		{
			for (int i = 0; i < body.bodies.size(); i++)
				body.bodies.get(i).getmBody().setType(BodyDef.BodyType.DynamicBody);
//			body.bodies.get(0).getmBody().setType(BodyDef.BodyType.DynamicBody);
		}

		gameScene.camera.zoom = 1.2f;
	}

	AnimatedSpriteSheet partSheet;
	Texture chainSpriteTexture;

	public void addChain()
	{
		partSheet = new AnimatedSpriteSheet("gfx/car/8/misc.png", gameScene.disposeTextureArray);

		chainSpriteTexture = TextureHelper.loadTexture("gfx/car/8/chain.png", gameScene.disposeTextureArray);

	}


	static float MAX_CHAIN_SPEED = 10;
	float tankChainSpeed = 0;
	static float minAngle = (float) Math.toRadians(-135);
	static float maxAngle = (float) Math.toRadians(135);

	@Override
	public void hitByGroundPreSolve(Contact contact, Fixture groundFixture, Fixture carFixture, String carFixtureString)
	{
		WorldManifold worldManifold = contact.getWorldManifold();
		float surfaceVelocityModifier = 0;

		if(isChain(carFixtureString))
			return;

		Vector2 localNormal = carFixture.getBody().getLocalVector(worldManifold.getNormal());
		float angle = (float) Math.atan2(localNormal.y, localNormal.x);
		if(minAngle < angle && angle < maxAngle)
			surfaceVelocityModifier += tankChainSpeed;

//		localNormal = carFixture.getBody().getLocalVector(worldManifold.getNormal().scl(-1, -1));
//		angle = (float) Math.atan2(localNormal.y, localNormal.x);
//		if(minAngle < angle && angle < maxAngle)
//			surfaceVelocityModifier += tankChainSpeed;

		contact.setTangentSpeed(surfaceVelocityModifier);
	}

	boolean isChain(String data)
	{
		if(BodyStrings.getPartOf(data, 1) == null)
			return false;

		String part = BodyStrings.getPartOf(data, 1);

		return part.contains("downChain");
	}


	@Override
	public void gas(float rate)
	{
		tankChainSpeed = MAX_CHAIN_SPEED;
	}

	@Override
	public void brake(float rate)
	{
		tankChainSpeed = -MAX_CHAIN_SPEED;
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
	public void setFirstPos(float x, float y)
	{
		super.setFirstPos(x, y);

		addChain();
	}

	@Override
	public void label()
	{
		body.setCar();

		isLabeled = true;
	}
}
