package WeaponBase;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.*;
import Sorter.GunSorter;
import heshmat.MainActivity;

/*
	Body User String Protocol:
	**Bullet + BulletType + BulletID + SHOOTER
 */

public abstract class BaseBullet
{
	MainActivity act;
	public GameManager gameManager;
	public BaseGun gun;
	public BulletFactory bulletFactory;

	public CzakBody body;
	public boolean shouldRelease;
	public boolean isFree;
	public int index;

	public float hitPoint;
	public float damage;
	public float shootingRange, shootingSpeed;
	public GunSorter.GunType bulletType;
	public Vector2 startingPoint = new Vector2();

	public Texture bulletTexture;

	BaseBullet(MainActivity act, int id)
	{
		this.act = act;
		gameManager = act.sceneManager.gameScene.gameManager;
		bulletFactory = gameManager.bulletFactory;
		index = id;
	}

	public String userPrefixString;
	public void create(BaseGun gun){this.gun = gun;};
	public void shoot(float x, float y, float teta)
	{
		startingPoint.set(x, y);
	}
	public abstract void release();
	public void dispose()
	{
		gameManager.gameScene.world.destroyBody(body.getmBody());
	}

	public void run()
	{
		float x = body.getmBody().getWorldCenter().x * PhysicsConstant.PIXEL_TO_METER;
		float y = body.getmBody().getWorldCenter().y * PhysicsConstant.PIXEL_TO_METER;

		if(CameraHelper.distance(x, y, startingPoint.x, startingPoint.y) >= shootingRange)
			shouldRelease = true;

		if(hitPoint <= 0)
			shouldRelease = true;
	}
	public abstract void draw(Batch batch);
	public abstract void hitByEnemy(String EnemyData);
	public abstract void hitByGround();
	public abstract void hitByCar(String CarData);
	public abstract void hitByBullet(String BulletData);

	public static String getBulletShooter(String fullString)
	{
		return BodyStrings.getPartOf(fullString, 3);
	}

	public static String getBulletType(String fullString)
	{
		return BodyStrings.getPartOf(fullString, 1);
	}

	public static int getBulletID(String fullString)
	{
		return Integer.valueOf(BodyStrings.getPartOf(fullString, 2));
	}
}
