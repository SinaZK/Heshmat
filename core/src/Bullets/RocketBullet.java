package Bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;

import Entity.AnimatedSprite;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import WeaponBase.ThrowBullet;
import Weapons.RocketLauncher;
import heshmat.MainActivity;

public class RocketBullet extends ThrowBullet
{

	float sizeX, sizeY;

	public AnimatedSprite explosionSprite;
	boolean waitForExplosion;

	public RocketBullet(int id, MainActivity activity, int szX, int szY, float shootingSpeed)
	{
		super(id, activity, szX, shootingSpeed);

		sizeX = szX;
		sizeY = szY;

		body = new CzakBody(PhysicsFactory.createBoxBody(bulletFactory.mScene.world, 0, 0, sizeX, sizeY, BodyDef.BodyType.DynamicBody),
				bulletFactory.RocketBulletTexture);
		body.getmSprite().get(0).setSize(sizeX, sizeY);
		body.setUserData(BodyStrings.BulletRocketString + " " + id);

		explosionSprite = new AnimatedSprite("gfx/explosion.png", 1, 12, 24, 0.48f, gameManager.gameScene.disposeTextureArray);
		explosionSprite.isDisabled = true;

		mGun = gameManager.rocketLauncher;
	}


	@Override
	public void create()
	{
		super.create();

		bulletType = BulletType.ROCKET_LAUNCHER;
		waitForExplosion = false;
		shootingRange = 550;

	}

	@Override
	public void shoot(float x, float y, float teta)
	{
		super.shoot(x, y, teta);
		float V = shootingSpeed;
		body.getmBody().setLinearVelocity(V * (float) Math.cos(Math.toRadians(teta)), V * (float) Math.sin(Math.toRadians(teta)));
	}

	@Override
	public void run()
	{
		if(waitForExplosion)
		{
			if(explosionSprite.isDisabled)
				isFree = true;

			return;
		}

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
		}
	}

	public void explode()
	{
		waitForExplosion = true;
		explosionSprite.reset();
		explosionSprite.setPosition(body.getmSprite().get(0).getX() - 30, body.getmSprite().get(0).getY() - 10);
	}

	@Override
	public void release()
	{
//		Log.e("RocketBullet", "release Rocket!");
		explode();
		super.release();
		isFree = false;
		body.getmBody().setActive(false);
	}

	@Override
	public void draw(Batch batch)
	{
		if(!waitForExplosion)
			super.draw(batch);
		explosionSprite.draw(batch);
	}
}
