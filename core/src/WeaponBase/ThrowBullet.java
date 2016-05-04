package WeaponBase;

import com.badlogic.gdx.graphics.g2d.Batch;

import Physics.CzakBody;

import Physics.CzakBody;
import PhysicsFactory.*;
import heshmat.MainActivity;

public class ThrowBullet extends BaseBullet
{
	public CzakBody body;
	public int size;
	public float shootingSpeed;

	public ThrowBullet(MainActivity act, int sz, float shootingSpeed)
	{
		super(act);

		this.shootingSpeed = shootingSpeed;
		size = sz;
		bulletFactory = gameManager.bulletFactory;
	}

	@Override
	public void create()
	{
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
	public void release() {

		body.getmBody().setGravityScale(0);
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
	public void hitByGround() {

	}
}
