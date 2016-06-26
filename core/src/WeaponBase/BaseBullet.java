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
import PhysicsFactory.PhysicsFactory;
import Sorter.GunSorter;
import heshmat.MainActivity;

/*
	Body User String Protocol:
	**Bullet + BulletType + BulletID
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
	public abstract void hitByEnemy(String EnemyData);
	public abstract void hitByGround();
	public abstract void hitByCar(String CarData);
	public abstract void hitByBullet(String BulletData);

	public static String getBulletType(String fullString)
	{
		return BodyStrings.getPartOf(fullString, 1);
	}

	public static int getBulletID(String fullString)
	{
		return Integer.valueOf(BodyStrings.getPartOf(fullString, 2));
	}
}
