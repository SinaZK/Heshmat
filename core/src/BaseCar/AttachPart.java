package BaseCar;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;

import Misc.BodyStrings;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;

/**
 * Created by sinazk on 7/7/16.
 * 15:38
 */
public class AttachPart
{
	NormalCar car;
	public CzakBody body;

	float dX, dY;
	boolean isAttachedAtFirst;
	float startR;

	float damagePercent;
	float detachPercent;

	public boolean isAttached = false;
	WeldJoint weld;

	float width, height;

	public AttachPart(NormalCar car)
	{
		this.car = car;

		body = new CzakBody();
	}

	int imagePointer = 0;
	public void draw(Batch batch)
	{
		body.draw(batch, imagePointer);


		float percent = car.hitpoint / car.getMaxHitPoint() * 100;

		if(percent <= damagePercent)
			damage();

		if(percent <= detachPercent)
			detach();
	}

	public void addSprite(Sprite s, boolean resize)
	{
		if(resize)
			s.setSize(width, height);

		body.addSprite(s);
	}

	float rat = PhysicsConstant.PIXEL_TO_METER;
	public void create(float dX, float dY, float w, float h, float startR, boolean attach)
	{
		this.dX = dX;
		this.dY = dY;

		width = w;
		height = h;

		float x = car.body.bodies.get(0).getmBody().getWorldCenter().x * rat + dX;
		float y = car.body.bodies.get(0).getmBody().getWorldCenter().y * rat + dY;
		//firstY= 1000 for avoiding the preContact with Ground!
		body.setBody(PhysicsFactory.createBoxBody(car.gameScene.world, x, y, w, h, BodyDef.BodyType.DynamicBody, 0.1f, 0.3f, 0.3f));
		body.setUserData(BodyStrings.CAR_ATTACH_STRING);

		this.startR = startR;
		setRotation(startR);

		isAttachedAtFirst = attach;

		reset();
	}

	public void setPercent(float Damage, float Detach)
	{
		this.damagePercent = Damage;
		this.detachPercent = Detach;
	}

	public void attach()
	{
		if(isAttached)
			return;

		isAttached = true;
		weld = car.body.addBodyWithWeldWithoutAdding(body, "mainBody", car.gameScene.world);
	}

	public void detach()
	{
		if(!isAttached)
			return;

		isAttached = false;
		car.gameScene.world.destroyJoint(weld);
	}

	public void reset()
	{
		imagePointer = 0;

		if(isAttached)
			return;

		float x = car.body.bodies.get(0).getmBody().getWorldCenter().x + dX / rat;
		float y = car.body.bodies.get(0).getmBody().getWorldCenter().y + dY / rat;
		body.setPosition(x, y, startR);
		body.getmBody().setLinearVelocity(0, 0);
		body.getmBody().setAngularVelocity(0);

		if(isAttachedAtFirst)
			attach();
	}

	public void damage()
	{
		imagePointer = 1;
	}

	public void setRotation(float r)
	{
		body.setR(r);
	}
}
