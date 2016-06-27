package Bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import Entity.AnimatedSprite;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import Sorter.GunSorter;
import WeaponBase.BaseGun;
import WeaponBase.NormalBullet;
import Weapons.RocketLauncher;
import heshmat.MainActivity;

public class RocketBullet extends NormalBullet
{

	float sizeX, sizeY;

	public AnimatedSprite explosionSprite;
	boolean waitForExplosion;
	public float explosionDamageLength;

	public RocketBullet(int id, MainActivity activity, RocketLauncher rocketLauncher)
	{
		super(id, activity, rocketLauncher, GunSorter.GunType.RocketLauncher);

		explosionSprite = new AnimatedSprite("gfx/explosion.png", 1, 4, 16, 0.23f, gameManager.gameScene.disposeTextureArray);
		explosionSprite.isDisabled = true;
		explosionDamageLength = 150;
	}


	@Override
	public void create(BaseGun gun)
	{
		super.create(gun);

		bulletType = GunSorter.GunType.RocketLauncher;
		waitForExplosion = false;
		body.getmBody().setGravityScale(0.6f);
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
		gameManager.enemyFactory.damageArea(new Vector2(body.getmSprite().get(0).getX(), body.getmSprite().get(0).getY()), explosionDamageLength, damage);
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
		explosionSprite.draw(batch, bulletFactory.mScene.getDeltaTime());
	}

	@Override
	public void hitByBullet(String BulletData)
	{
		shouldRelease = true;
	}

	@Override
	public void hitByEnemy(String enemyData)
	{
		shouldRelease = true;
	}
}
