package EnemyBase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import BaseLevel.ShootingMode;
import Entity.AnimatedSpriteSheet;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import WeaponBase.BaseBullet;

/*
	Body User String Protocol:
	**Enemy + EnemyType + EnemyID
	*
	* Position: Middle of Enemy!
 */

public abstract class BaseEnemy
{
	public enum EnemyType
	{
		MOSQUITO, FLY, WASP, RED_BIRD, HATTY_BIRD, MASK_BIRD, FURRY_BIRD, FIRE_BIRD, BAT, BOSS_BIRD, WORM, BOMB
	}

	public GameManager gameManager;
	public EnemyFactory enemyFactory;
	public ShootingMode shootingMode;

	public CzakBody mainBody;
	public boolean shouldRelease;
	public  boolean isFree;
	public int index;

	public float speed;
	public float hitPoint, MAX_HP;
	public float seeSight;

	public float fullImageWidth, fullImageHeight;
	public float x, y;

	public boolean isDying;
	public float animationStateTime;
	public int selectedAnimation;
	public AnimatedSpriteSheet animatedSpriteSheet;

	public EnemyType enemyType;

	public BaseEnemy(GameManager gameManager, int id)
	{
		this.gameManager = gameManager;
		enemyFactory = gameManager.enemyFactory;
		index = id;
	}

	public void create(ShootingMode shootingMode, ArrayList<String> attr)
	{
		this.shootingMode = shootingMode;
		isFree = false;
		hitPoint = MAX_HP;
		mainBody.getmBody().setActive(true);
		shouldRelease = false;
		selectedAnimation = 0;
	}
	public void hitByBullet(String bulletData)
	{
		int bulletID = BaseBullet.getBulletID(bulletData);
		damage(gameManager.bulletFactory.bullets.get(bulletID).mDamage);
	}
	public void hitByCar()
	{
	}
	public abstract void move();
	public abstract void attack();
	public void release()
	{
		shootingMode.enemyDied++;

		mainBody.getmBody().setActive(false);
		mainBody.getmBody().setLinearVelocity(0, 0);
		mainBody.setPosition(10 / PhysicsConstant.PIXEL_TO_METER, 10 / PhysicsConstant.PIXEL_TO_METER);
		isFree = true;
	};

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

		if(isDying)
			return;

		if(hitPoint <= 0)
		{
			if(animatedSpriteSheet.getAnimation("die") != null)
			{
				isDying = true;
				mainBody.getmBody().setLinearVelocity(0, 0);
				mainBody.getmBody().setActive(false);
				selectedAnimation = animatedSpriteSheet.getAnimationID("die");
				Timer t = new Timer();
				t.scheduleTask(new Timer.Task()
				{
					@Override
					public void run()
					{
						isDying = false;
						shouldRelease = true;
					}
				}, animatedSpriteSheet.getAnimation("die").animDuration * 2);

			}
			else
				shouldRelease = true;
		}

		x = mainBody.getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER;
		y = mainBody.getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER;

		if(mainBody != null)
			if(x + fullImageWidth < CameraHelper.getXMin(gameManager.gameScene.camera))
				shouldRelease = true;

		move();
		attack();
	};

	public void draw(Batch batch)
	{
		if(mainBody != null)
		{
			animationStateTime += Gdx.graphics.getDeltaTime();
			mainBody.draw(batch, animationStateTime, selectedAnimation);
		}

		if(!isDying)
		{
			float paddingX = 5;
			gameManager.hpBarSprite.draw(batch, x - fullImageWidth / 2 + paddingX, y + fullImageHeight / 2 + 6, hitPoint, MAX_HP, fullImageWidth - paddingX * 2, 10);
		}
	}

	public void damage(float damage)
	{
		hitPoint -= damage;
	}

	public static String getEnemyType(String fullString)
	{
		return BodyStrings.getPartOf(fullString, 1);
	}

	public static int getEnemyID(String fullString)
	{
		return Integer.valueOf(BodyStrings.getPartOf(fullString, 2));
	}

	public void init(String userData, int id, Texture texture)
	{
		mainBody = new CzakBody(PhysicsFactory.createBoxBody(enemyFactory.gameScene.world, 0, 0, fullImageWidth, fullImageHeight, BodyDef.BodyType.DynamicBody),
				texture);
		mainBody.getmSprite().get(0).setSize(fullImageWidth, fullImageHeight);
		mainBody.getmBody().setGravityScale(0);
		mainBody.setUserData(BodyStrings.ENEMY_STRING + " " + userData + " " + id);
	}

	public void init(String userData, int id, AnimatedSpriteSheet texture)
	{
		mainBody = new CzakBody(PhysicsFactory.createBoxBody(enemyFactory.gameScene.world, 0, 0, fullImageWidth, fullImageHeight, BodyDef.BodyType.DynamicBody),
				texture);
		animatedSpriteSheet = texture;
		animatedSpriteSheet.setSize(fullImageWidth, fullImageHeight);
		mainBody.getmBody().setGravityScale(0);
		mainBody.setUserData(BodyStrings.ENEMY_STRING + " " + userData + " " + id);
	}
}
