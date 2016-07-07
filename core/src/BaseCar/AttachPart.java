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
	CzakBody body;

	float dX, dY;
	boolean isAttachedAtFirst;

	public boolean isAttached = false;
	WeldJoint weld;

	float width, height;

	public AttachPart(NormalCar car)
	{
		this.car = car;

		body = new CzakBody();
	}

	public void draw(Batch batch)
	{
		body.draw(batch);
	}

	public void addSprite(Sprite s)
	{
		s.setSize(width, height);
		body.addSprite(s);
	}

	float rat = PhysicsConstant.PIXEL_TO_METER;
	public void create(float dX, float dY, float w, float h, boolean attach)
	{
		this.dX = dX;
		this.dY = dY;

		float x = car.body.bodies.get(0).getmBody().getWorldCenter().x * rat + dX;
		float y = car.body.bodies.get(0).getmBody().getWorldCenter().y * rat + dY;

		width = w;
		height = h;

		body.setBody(PhysicsFactory.createBoxBody(car.gameScene.world, x, y, w, h, BodyDef.BodyType.DynamicBody));
		body.setUserData(BodyStrings.CAR_ATTACH_STRING);

		isAttachedAtFirst = attach;
		if(attach)
			attach();
	}

	public void attach()
	{
		if(isAttached)
			return;

		isAttached = true;
		weld = car.body.addBodyWithWeldWithoutAdding(body, "mainBody", car.gameScene.world);

		Log.e("Attach", "attach");
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
		if(isAttached)
			return;

		float x = car.body.bodies.get(0).getmBody().getWorldCenter().x + dX / rat;
		float y = car.body.bodies.get(0).getmBody().getWorldCenter().y + dY / rat;

		body.setPosition(x, y, 0);

		if(isAttachedAtFirst)
			attach();
	}
}
