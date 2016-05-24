package WeaponBase;

import com.badlogic.gdx.graphics.g2d.Batch;

import Physics.CzakBody;

import Physics.CzakBody;
import PhysicsFactory.*;
import heshmat.MainActivity;

public class ThrowBullet extends BaseBullet
{
	public int size;
	public float shootingSpeed;

	public ThrowBullet(int id, MainActivity act, int sz, float shootingSpeed)
	{
		super(act, id);

		this.shootingSpeed = shootingSpeed;
		size = sz;
		bulletFactory = gameManager.bulletFactory;
	}

	@Override
	public void create()
	{
		isFree = false;
		body.getmBody().getFixtureList().get(0).setSensor(false);
		body.getmBody().setBullet(true);
		body.getmBody().setActive(true);
		shouldRelease = false;
	}

	@Override
	public void shoot(float x, float y, float teta)
	{
		super.shoot(x, y, teta);
		body.setPosition(x / PhysicsConstant.PIXEL_TO_METER, y / PhysicsConstant.PIXEL_TO_METER);
	}

	@Override
	public void run() {

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
