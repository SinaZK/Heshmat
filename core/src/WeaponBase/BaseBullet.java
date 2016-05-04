package WeaponBase;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import GameScene.GameManager;
import Misc.CameraHelper;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsFactory;
import heshmat.MainActivity;

public abstract class BaseBullet
{
	MainActivity act;
	public GameManager gameManager;
	public BaseGun mGun;
	public BulletFactory bulletFactory;


	public CzakBody body;
	public boolean shouldRelease;
	public boolean isFree;

	public float mDamage;
	public float shootingRange = 100;
	public BulletType bulletType;
	public Vector2 startingPoint = new Vector2();
	public int index;


	BaseBullet(MainActivity act, int id)
	{
		this.act = act;
		gameManager = act.sceneManager.gameScene.gameManager;
		bulletFactory = gameManager.bulletFactory;
		index = id;
	}

	public enum BulletType
	{
		PISTOL, ROCKET_LAUNCHER;
	}

	public void create(){};
	public void shoot(float x, float y, float teta)
	{
		startingPoint.set(x, y);
	}

	public abstract void release();

	public void dispose()
	{
		gameManager.gameScene.world.destroyBody(body.getmBody());
	}

	public abstract void run();

	public abstract void draw(Batch batch);
//	public abstract void hitByEnemy();
	public abstract void hitByGround();

}
