package Enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;

import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import EnemyBase.BaseEnemy;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 5/6/16.
 * Hi 1:19
 */
public class PigeonLast extends Pigeon
{
	public PigeonLast(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.PIGEON_LAST;

		init(BodyStrings.DrivingEnemy, id, enemyFactory.PigeonEnemyAnimation);

		loadGun();

		gun.bulletTexture = enemyFactory.FlyBulletTexture;
		gun.bulletSize = new Vector2(25, 25);
		gun.setBulletSpeed(10);

		gunX = 50;
		gunY = 65;
		gun.setClipSize(0);
		gun.setRateOfFire(1);
		gun.setReloadTime(10000);
		gunTeta = -90;
		gun.haveReloadSound = false;
	}

	float attackingDistance;
	boolean isGoingOut = false;

	boolean isDone = false;
	boolean hitPointCheck = false;
	@Override
	public void run()
	{
		gun.run();
		if(gun != null)
		{
			gun.setPosition(x - fullImageWidth / 2 + gunX, y - fullImageHeight / 2 + gunY);
			gun.image.setRotation(gunTeta);
			gun.run();
		}

		if(!isDone)
		{
			if(shouldRelease)
				shouldRelease = false;

			if(hitPoint <= 0 && !hitPointCheck)
			{
				hitPointCheck = true;
				setCurrentState(StateEnum.MOVE);
				float zoom = gameManager.gameScene.camera.zoom;
				attackingDistance = (float) (SceneManager.WORLD_X * zoom * (0.1));

				gun.ammo = 1;
				gun.setClipSize(1);
				gun.setRateOfFire(1);
				gun.setReloadTime(100000);
				gun.isReloading = false;
				gun.isShootingEnabled = true;
				gun.setBulletDamage(0);
				gun.bulletTexture = enemyFactory.FlyBulletTexture;
			}

			if(currentState == StateEnum.MOVE)
				move();
			if(currentState == StateEnum.ATTACK)
				attack();

			decide();

			exitRelease();

			return;
		}
		else decide();

		gun.run();
	}

	@Override
	public void draw(Batch batch)
	{
		if(mainBody != null)
		{
			animationStateTime += gameManager.gameScene.getDeltaTime();


			mainBody.draw(batch, animationStateTime, selectedAnimation);
		}

		if(!isDying && hitPoint > 0)
		{
			float paddingX = 5;

			float hpBarX = x - fullImageWidth / 2 + paddingX;
			float hpBarY = y + fullImageHeight / 2 + 6;
			gameManager.hpBarSprite.draw(batch, hpBarX, hpBarY, hitPoint, getMAX_HP(), fullImageWidth - paddingX * 2, 10);
			gameManager.gameScene.font14.draw(batch, "" + level, hpBarX, hpBarY);
		}
	}

	@Override
	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr)
	{
		super.create(shootingMode, level, attr);

		isGoingOut = false;
		isKillCount = false;
		isDone = false;
		hitPointCheck = false;
		isShoot = false;
		isALLDONE = false;

		float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
		float width = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;

		float groundHeight = enemyFactory.gameManager.levelManager.currentLevel.terrain.Points.getLast().y;
		float myHeight = (float) (groundHeight + (Math.random() * 0.1 + 1.1) * SceneManager.WORLD_Y);

		setPosition(originX + width + 100, myHeight);

		float zoom = gameManager.gameScene.camera.zoom;
		attackingDistance = (float) (SceneManager.WORLD_X * zoom * (0.6));
	}

	@Override
	public void decide()
	{
		float carX = gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().x *
				PhysicsConstant.PIXEL_TO_METER;

		if(isGoingOut)
			return;

		if(x - carX < attackingDistance && currentState != StateEnum.ATTACK)
		{
			setCurrentState(StateEnum.ATTACK);
		}

	}

	boolean isShoot = false;
	@Override
	public void attack()
	{
		if(isShoot)
			return;

		if(hitPointCheck)
		{
			isShoot = true;
			gun.ammo = 1;
			gun.shoot();

			setCurrentState(StateEnum.MOVE);
			isGoingOut = true;
		}
	}

	@Override
	public void loadGun()
	{
		super.loadGun();
	}


	boolean isALLDONE = false;
	@Override
	public void exitRelease()
	{
		x = mainBody.getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER;
		y = mainBody.getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER;

		if(x + fullImageWidth < CameraHelper.getXMin(gameManager.gameScene.camera))
		{
			if(isALLDONE)
				return;
			shouldRelease = true;

			release(false);

			isALLDONE = true;
			gameManager.levelManager.currentLevel.getCurrentPart().isCameraDone = true;
			gameManager.levelManager.currentLevel.getCurrentPart().isFinished = true;
		}
	}
}
