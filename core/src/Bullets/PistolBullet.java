package Bullets;

import com.badlogic.gdx.physics.box2d.BodyDef;


import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import WeaponBase.ThrowBullet;
import heshmat.MainActivity;

public class PistolBullet extends ThrowBullet
{
	private static int SHOOTING_RANGE = 850;

	public PistolBullet(int id, MainActivity activity, int sz, float shootingSpeed)
	{
		super(id, activity, sz, shootingSpeed);

		body = new CzakBody(PhysicsFactory.createBoxBody(bulletFactory.mScene.world, 0, 0, size, size, BodyDef.BodyType.DynamicBody),
				bulletFactory.PistolBulletTexture);
		body.getmSprite().get(0).setSize(size, size);
		body.setUserData(BodyStrings.BULLET_STRING + " " + BodyStrings.BulletPistolString + " " + id);

		mGun = gameManager.pistol;
	}


	@Override
	public void create()
	{
		super.create();

		bulletType = BulletType.PISTOL;
		body.getmBody().setGravityScale(0);
		shootingRange = SHOOTING_RANGE;
		mDamage = 4;
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
		if(shouldRelease)
		{
			release();
			return;
		}

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

	@Override
	public void release() {
		super.release();
	}
}
