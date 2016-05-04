package Bullets;

import com.badlogic.gdx.physics.box2d.BodyDef;


import Misc.CameraHelper;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import WeaponBase.ThrowBullet;
import heshmat.MainActivity;

public class PistolBullet extends ThrowBullet
{

	public PistolBullet(MainActivity activity, int sz, float shootingSpeed)
	{
		super(activity, sz, shootingSpeed);

		body = new CzakBody(PhysicsFactory.createBoxBody(bulletFactory.mScene.world, 0, 0, size, size, BodyDef.BodyType.DynamicBody),
				bulletFactory.PistolBulletTexture);
		body.getmSprite().get(0).setSize(size, size);
	}


	@Override
	public void create()
	{
		isFree = false;
		bulletType = BulletType.PISTOL;
		body.getmBody().setGravityScale(0);
		body.getmBody().getFixtureList().get(0).setSensor(false);
		body.getmBody().setBullet(true);
		shootingRange = 350;
	}

	@Override
	public void shoot(float x, float y, float teta)
	{
		super.shoot(x, y, teta);


		float V = shootingSpeed;
		body.getmBody().setLinearVelocity(V * (float)Math.cos(Math.toRadians(teta)), V * (float)Math.sin(Math.toRadians(teta)));
	}

	@Override
	public void run()
	{
		if(body != null)
		{
			float x1 = startingPoint.x;
			float y1 = startingPoint.y;
			float x2 = body.getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER;
			float y2 = body.getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER;
			float dist = CameraHelper.distance(x1, y1, x2, y2);
			if(dist > shootingRange)
				release();
		}else
			Log.e("PistolBullet run"," body is null");
	}

}
