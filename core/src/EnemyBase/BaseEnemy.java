package EnemyBase;

import com.badlogic.gdx.graphics.g2d.Batch;

import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import heshmat.MainActivity;

/*
	Body User String Protocol:
	**Enemy + EnemyType + EnemyID
	*
	* Position: Middle of Enemy!
 */

public abstract class BaseEnemy
{
	MainActivity act;
	public GameManager gameManager;
	public EnemyFactory enemyFactory;

	public CzakBody mainBody;
	public boolean shouldRelease;
	public  boolean isFree;
	public int index;

	public float speed;
	public float hitPoint, MAX_HP;
	public float seeSight;

	public float fullImageWidth, fullImageHeight;
	public float x, y;

	public EnemyType enemyType;

	public BaseEnemy(MainActivity act, int id)
	{
		this.act = act;
		gameManager = act.sceneManager.gameScene.gameManager;
//		if(gameManager == null)
//			Log.e("BaseEnemy.java", "game manager is null");
//		if(this.act.sceneManager.gameScene.gameManager == null)
//			Log.e("BaseEnemy.java", "act is null");
		enemyFactory = gameManager.enemyFactory;
		index = id;
	}

	public abstract void create();
	public abstract void hitByBullet(String bulletData);
	public abstract void move();
	public abstract void attack();
	public abstract void release();

	public void setPosition(float X, float Y)
	{
		x = X;
		y = Y;
		mainBody.setPosition(x / PhysicsConstant.PIXEL_TO_METER, y / PhysicsConstant.PIXEL_TO_METER);
	}

	public void run()
	{
		if(shouldRelease)
		{
			release();
			return;
		}
		if(hitPoint <= 0)
			shouldRelease = true;

		x = mainBody.getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER;
		y = mainBody.getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER;
	};

	public void draw(Batch batch)
	{
		gameManager.hpBarSprite.draw(batch, x - fullImageWidth / 2, y + fullImageHeight / 2 + 3, hitPoint, MAX_HP);
	}

	public void damage(float damage)
	{
		hitPoint -= damage;
	}

	public enum EnemyType
	{
		Pigeon, Rat;
	}

	public static String getEnemyType(String fullString)
	{
		return BodyStrings.getPartOf(fullString, 1);
	}

	public static int getEnemyID(String fullString)
	{
		return Integer.valueOf(BodyStrings.getPartOf(fullString, 2));
	}
}
