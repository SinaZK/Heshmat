package Enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;

import EnemyBase.AirEnemy;
import EnemyBase.BaseEnemy;
import EnemyBase.EnemyFactory;
import Misc.BodyStrings;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsFactory;
import WeaponBase.BaseBullet;
import heshmat.MainActivity;

/**
 * Created by sinazk on 5/6/16.
 * Hi 1:19
 */
public class Pigeon extends AirEnemy
{
	public Pigeon(MainActivity act, int id)
	{
		super(act, id);

		enemyType = EnemyType.Pigeon;

		fullImageWidth = 100;
		fullImageHeight = 40;

		MAX_HP = 10;

		mainBody = new CzakBody(PhysicsFactory.createBoxBody(enemyFactory.mScene.world, 0, 0, fullImageWidth, fullImageHeight, BodyDef.BodyType.DynamicBody),
				enemyFactory.PigeonEnemyTexture);
		mainBody.getmSprite().get(0).setSize(fullImageWidth, fullImageHeight);
		mainBody.getmBody().setGravityScale(0);
		mainBody.setUserData(BodyStrings.ENEMY_STRING + " " + BodyStrings.EnemyPigeon + " " + id);
	}

	@Override
	public void draw(Batch batch)
	{
		super.draw(batch);
	}

	@Override
	public void run()
	{
		super.run();

//		Log.e("Tag","hp = " + hitPoint);
	}

	@Override
	public void create()
	{
		super.create();
	}

	@Override
	public void hitByBullet(String bulletData)
	{
		super.hitByBullet(bulletData);
	}

	@Override
	public void release()
	{
		super.release();
	}
}
