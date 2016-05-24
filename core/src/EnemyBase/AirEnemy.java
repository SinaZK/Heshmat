package EnemyBase;

import com.badlogic.gdx.graphics.g2d.Batch;

import BaseLevel.ShootingMode;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;
import WeaponBase.BaseBullet;
import heshmat.MainActivity;

/**
 * Created by sinazk on 5/6/16.
 * Hi 00:01
 */

public class AirEnemy extends BaseEnemy
{
	public AirEnemy(MainActivity act, int id)
	{
		super(act, id);
	}

	@Override
	public void create(ShootingMode shootingMode)
	{
		super.create(shootingMode);
		hitPoint = MAX_HP;
		isFree = false;
		mainBody.getmBody().setActive(true);
		shouldRelease = false;
		mainBody.getmBody().setTransform(mainBody.getmBody().getPosition(), 0);
	}

	@Override
	public void hitByBullet(String bulletData)
	{
		super.hitByBullet(bulletData);
	}

	@Override
	public void run()
	{
		super.run();
		move();
		attack();
	}

	@Override
	public void draw(Batch batch)
	{
		if(mainBody != null)
			mainBody.draw(batch);
		super.draw(batch);
	}

	@Override
	public void move()
	{

	}

	@Override
	public void attack()
	{

	}

	@Override
	public void release()
	{
		super.release();
		mainBody.getmBody().setActive(false);
		mainBody.getmBody().setLinearVelocity(0, 0);
		mainBody.setPosition(10 / PhysicsConstant.PIXEL_TO_METER, 10 / PhysicsConstant.PIXEL_TO_METER);
		isFree = true;
	}


}
