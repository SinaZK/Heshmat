package EnemyBase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.plaf.nimbus.State;

import BaseLevel.ShootingMode;
import Entity.AnimatedSpriteSheet;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.FileLoader;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.PhysicsConstant;
import PhysicsFactory.PhysicsFactory;
import Sorter.GunSorter;
import WeaponBase.BaseBullet;
import WeaponBase.BaseGun;

import static java.lang.StrictMath.max;

/**
	Body User String Protocol:
	**Enemy + EnemyType + EnemyID
	*
	* Position: Middle of Enemy!
 **/

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

	public static float RAGE_PERCENT = 20;
	public static float LEVEL_UPGRADE_PERCENT = 10;

	public int level;
	public float fullImageWidth, fullImageHeight;
	private float BASE_SPEED, MAX_HP, DAMAGE, FIRE_RATE, weakspeedcoef, HIT_STUN = 0, HIT_RAGE = 0;;
	public float speed;
	public float hitPoint;

	public boolean isStun, isRage;
	private float stunCounter, rageCounter;//forTiming

	public StateEnum currentState;

	public float x, y;

	public boolean isDying;
	public float animationStateTime;
	public int selectedAnimation;
	public AnimatedSpriteSheet animatedSpriteSheet;

	public EnemyType enemyType;

	public BaseGun gun;
	public float gunX = 0, gunY = 0;
	public float gunTeta = 235;
	public BaseEnemy(GameManager gameManager, int id)
	{
		this.gameManager = gameManager;
		enemyFactory = gameManager.enemyFactory;
		index = id;
	}

	public void loadGun()
	{
		gun = GunSorter.createEnemyGun(gameManager);
		gun.rateOfFire = getFIRE_RATE();
		gun.ammo = 100;
		gun.bulletDamage = getDamage();
		gun.setShooter(BodyStrings.Shooter_ENEMY);
	}

	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr)
	{
		setLevel(level);
		currentState = StateEnum.MOVE;
		this.shootingMode = shootingMode;
		isFree = false;
		hitPoint = getMAX_HP();
		speed = getBASE_SPEED();
		mainBody.getmBody().setActive(true);
		shouldRelease = false;
		selectedAnimation = 0;
		isStun = false;
		isRage = false;

//        Log.e("BaseEnemy.java", "enemy create : " + enemyType);
	}
	public void hitByBullet(String bulletData)
	{
		int bulletID = BaseBullet.getBulletID(bulletData);
		damage(gameManager.bulletFactory.bullets.get(bulletID).damage);

		if(getHIT_RAGE() > 0)
			rage();

		if(getHIT_STUN() > 0)
			stun();
	}
	public void hitByCar()
	{
	}

	public void move()
	{
		float rat = PhysicsConstant.PIXEL_TO_METER;
		float x = mainBody.getmBody().getWorldCenter().x;
		float y = mainBody.getmBody().getWorldCenter().y;

		x -= speed * gameManager.gameScene.getDeltaTime() / rat;

		mainBody.getmBody().setTransform(x, y, mainBody.getmBody().getAngle());
	}

	public void attack()
	{
		gun.shoot();
	}
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
		if(gun != null)
		{
			gun.setPosition(x - fullImageWidth / 2 + gunX, y - fullImageHeight / 2 + gunY);
			gun.image.setRotation(gunTeta);
			gun.run();
		}

		if(shouldRelease)
		{
			release();
			return;
		}

		if(isDying)
			return;

		decide();

		if(!isStun)
		{
			switch (currentState)
			{
				case DEATH:
					break;
				case ATTACK:
					attack();
                    break;
				case MOVE:
					move();
					break;
				case STUN:
					stun();
					break;
			}
		}

		speed = calculateSpeedByweakspeedcoef();

		if(isStun)
		{
			stunCounter += gameManager.gameScene.getDeltaTime();

			if(stunCounter >= getHIT_STUN())
			{
				isStun = false;
				setCurrentState(StateEnum.MOVE);
			}
		}

		if(isRage)
		{
			speed = max(getBASE_SPEED(), calculateSpeedByweakspeedcoef()) * (100 + RAGE_PERCENT) / 100f;

			rageCounter += gameManager.gameScene.getDeltaTime();
			if(rageCounter >= getHIT_RAGE())
			{
				isRage = false;
				setCurrentState(StateEnum.MOVE);
			}
		}

		exitRelease();

//		Log.e("BaseEnemy.java", "speed = " + speed);
	}

	private float calculateSpeedByweakspeedcoef()
	{
		float ratio = 1 - hpRatio();
		float diff = -1 + getWeakspeedcoef();

		float newSpeed = (diff * ratio + 1) * getBASE_SPEED();

		return newSpeed;
	}

	public void exitRelease()
	{
		x = mainBody.getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER;
		y = mainBody.getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER;

		if(x + fullImageWidth < CameraHelper.getXMin(gameManager.gameScene.camera))
				shouldRelease = true;
	}

	public void stun()
	{
		isStun = true;
		stunCounter = 0;
	}

	public void rage()
	{
		isRage = true;
		rageCounter = 0;
	}

	public void decide()
	{
		if(hitPoint <= 0)
		{
			isStun = false;
			isRage = false;
			setCurrentState(StateEnum.DEATH);
			if(animatedSpriteSheet.haveAnimation(EnemyFactory.ENEMY_ANIMATION_DIE_STRING))
			{
				isDying = true;
				mainBody.getmBody().setLinearVelocity(0, 0);
				mainBody.getmBody().setActive(false);

				Timer t = new Timer();

				t.scheduleTask(new Timer.Task()
				{
					@Override
					public void run()
					{
						isDying = false;
						shouldRelease = true;
					}
				}, animatedSpriteSheet.getAnimation(EnemyFactory.ENEMY_ANIMATION_DIE_STRING).animDuration * 2);

			}
			else
				shouldRelease = true;
		}



		if(isStun)
			return;

		switch (currentState)
		{
			case DEATH:
				if(animatedSpriteSheet.haveAnimation(EnemyFactory.ENEMY_ANIMATION_DIE_STRING))
					selectedAnimation = animatedSpriteSheet.getAnimationID(EnemyFactory.ENEMY_ANIMATION_DIE_STRING);
				break;
			case ATTACK:
				if(animatedSpriteSheet.haveAnimation(EnemyFactory.ENEMY_ANIMATION_ATTACK_STRING))
					selectedAnimation = animatedSpriteSheet.getAnimationID(EnemyFactory.ENEMY_ANIMATION_ATTACK_STRING);
                break;
			case MOVE:
				if(animatedSpriteSheet.haveAnimation(EnemyFactory.ENEMY_ANIMATION_MOVE_STRING))
					selectedAnimation = animatedSpriteSheet.getAnimationID(EnemyFactory.ENEMY_ANIMATION_MOVE_STRING);
				break;
		}
	}

	public float hpRatio()
	{
		return hitPoint / getMAX_HP();
	}

	public void draw(Batch batch)
	{
		if(mainBody != null)
		{
			animationStateTime += gameManager.gameScene.getDeltaTime();
			mainBody.draw(batch, animationStateTime, selectedAnimation);
		}

		if(!isDying)
		{
			float paddingX = 5;

			float hpBarX = x - fullImageWidth / 2 + paddingX;
			float hpBarY = y + fullImageHeight / 2 + 6;
			gameManager.hpBarSprite.draw(batch, hpBarX, hpBarY, hitPoint, getMAX_HP(), fullImageWidth - paddingX * 2, 10);
			gameManager.gameScene.font24Gold.draw(batch, "" + level, hpBarX, hpBarY);
		}

	}

	public void setCurrentState(StateEnum state)
	{
		currentState = state;
		animationStateTime = 0;
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

	public void load(String path)
	{
		FileLoader fileLoader = new FileLoader();
		fileLoader.loadFile(path + "enemy.enemy");

		fullImageWidth = fileLoader.getFloat(0, 1);
		fullImageHeight = fileLoader.getFloat(0, 2);

		BASE_SPEED = fileLoader.getFloat(1, 1);
		MAX_HP = fileLoader.getFloat(2, 1);
		DAMAGE = fileLoader.getFloat(3, 1);
		FIRE_RATE = fileLoader.getFloat(4, 1);
		HIT_STUN = fileLoader.getFloat(5, 1);
		HIT_RAGE = fileLoader.getFloat(6, 1);
		weakspeedcoef = fileLoader.getFloat(7, 1);
	}

	public float getMAX_HP()
	{
		return MAX_HP * levelMul;
	}

	public float getBASE_SPEED()
	{
		return BASE_SPEED;
	}

	public float getDamage()
	{
		return DAMAGE * levelMul;
	}

	public float getFIRE_RATE()
	{
		return FIRE_RATE;
	}

	public float getWeakspeedcoef()
	{
		return weakspeedcoef;
	}

	public float getHIT_STUN()
	{
		return HIT_STUN;
	}

	public float getHIT_RAGE()
	{
		return HIT_RAGE;
	}

	float levelMul = 1;
	public void setLevel(int level)
	{
		this.level = level;
		double a = (100 + LEVEL_UPGRADE_PERCENT) / 100;
		double b = level - 1;
		levelMul = (float)Math.pow(a, b);
	}

	public enum StateEnum
	{
		MOVE, ATTACK, STUN, DEATH,
	}

}