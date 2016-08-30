package Cars.Tank;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import Physics.CzakBody;
import PhysicsFactory.*;

/**
 * Created by sinazk on 8/23/16.
 * 20:30
 */
public class TankChain
{
	public static float WIDTH = 23;
	public static float HEIGHT = 6;

	Tank tank;
	CzakBody body;

	public TankChain(Tank tank)
	{
		this.tank = tank;
		body = new CzakBody();

		body.addSprite(new Sprite(tank.chainSpriteTexture));
	}

	public void set(World world, float x, float y, float r)
	{
		FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(0.1f, 0.1f, 1);

		body.setBody(PhysicsFactory.createBoxBody(world, x, y, WIDTH, HEIGHT, r, BodyDef.BodyType.DynamicBody, fixtureDef, PhysicsConstant.PIXEL_TO_METER));
		body.setUserData("" + tank.body.bodies.get(0).getmBody().getUserData());
	}

	public void draw(Batch batch)
	{
		if(body != null)
			body.draw(batch);
	}

	public void addJoints(int i, float rX, float rY)// and i - 1
	{
		Body b = body.getmBody();
		RevoluteJointDef mRevoluteJointDef = new RevoluteJointDef();

		mRevoluteJointDef.collideConnected = false;
//		mRevoluteJointDef.enableLimit = true;
//		mRevoluteJointDef.upperAngle = (float)Math.toRadians(10);
//		mRevoluteJointDef.lowerAngle = (float)Math.toRadians(-10);

//		float x1 = tank.chains.get(i).body.getmBody().getWorldCenter().x;
//		float x2 = b.getWorldCenter().x;

//		float y1 = tank.chains.get(i).body.getmBody().getWorldCenter().y;
//		float y2 = b.getWorldCenter().y;

		rX /= PhysicsConstant.PIXEL_TO_METER;
		rY /= PhysicsConstant.PIXEL_TO_METER;

//		mRevoluteJointDef.initialize(tank.chains.get(i).body.getmBody(), b,
//				new Vector2(rX, rY));

		tank.gameScene.world.createJoint(mRevoluteJointDef);
	}
}
