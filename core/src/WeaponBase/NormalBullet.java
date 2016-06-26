package WeaponBase;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.Locale;

import Misc.BodyStrings;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import Sorter.GunSorter;
import heshmat.MainActivity;

public class NormalBullet extends BaseBullet
{
	public NormalBullet(int BulletFactoryQid, MainActivity act, BaseGun gun, GunSorter.GunType type)
	{
		super(act, BulletFactoryQid);

		this.bulletType = type;
		this.gun = gun;

		this.shootingSpeed = gun.bulletSpeed;
		this.damage = gun.bulletDamage;
		this.hitPoint = gun.bulletHP;

		body = new CzakBody(PhysicsFactory.createBoxBody(bulletFactory.mScene.world, 0, 0, gun.bulletSize.x, gun.bulletSize.y, BodyDef.BodyType.DynamicBody),
				gun.bulletTexture);
		body.getmSprite().get(0).setSize(gun.bulletSize.x, gun.bulletSize.y);
		body.setUserData(BodyStrings.BULLET_STRING + " " + GunSorter.getBulletBodyString(type) + " " + BulletFactoryQid);

		shootingRange = 1000;
	}

	@Override
	public void create()
	{
		isFree = false;
		body.getmBody().getFixtureList().get(0).setSensor(false);
		body.getmBody().setBullet(true);
		body.getmBody().setActive(true);
		body.getmBody().setGravityScale(0);
		shouldRelease = false;
		this.hitPoint = gun.bulletHP;
	}

	@Override
	public void shoot(float x, float y, float teta)
	{
		super.shoot(x, y, teta);
		body.setPosition(x / PhysicsConstant.PIXEL_TO_METER, y / PhysicsConstant.PIXEL_TO_METER);

		float V = shootingSpeed;
		body.getmBody().setLinearVelocity(V * (float) Math.cos(Math.toRadians(teta)), V * (float) Math.sin(Math.toRadians(teta)));
	}

	@Override
	public void run() {
		if(shouldRelease)
			release();
	}

	@Override
	public void release()
	{

		body.getmBody().setActive(false);
		body.getmBody().setLinearVelocity(0, 0);
		body.getmBody().getFixtureList().get(0).setSensor(true);
		body.setPosition(10 / PhysicsConstant.PIXEL_TO_METER, 10 / PhysicsConstant.PIXEL_TO_METER);
		isFree = true;
	}

	@Override
	public void draw(Batch batch) {
		if(body != null)
			body.draw(batch);
	}

	@Override
	public void hitByEnemy(String enemyData)
	{
		int enemyID = Integer.parseInt(BodyStrings.getPartOf(enemyData, 2));
		hitPoint -= gameManager.enemyFactory.enemies.get(enemyID).hitPoint;

//		Log.e("NormalBullet.java", "HitByEnemy : " + enemyID + " hitPoint = " + hitPoint + " and enemyHP = " + gameManager.enemyFactory.enemies.get(enemyID).hitPoint);

		if(hitPoint <= 0)
			shouldRelease = true;
	}

	@Override
	public void hitByGround()
	{
		shouldRelease = true;
	}

	@Override
	public void hitByCar(String CarData)
	{
		shouldRelease = true;
	}

	@Override
	public void hitByBullet(String BulletData)
	{
		shouldRelease = true;
	}

}
