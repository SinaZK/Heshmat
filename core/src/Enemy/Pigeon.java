package Enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import EnemyBase.BaseEnemy;
import EnemyBase.EnemyFactory;
import Entity.AnimatedSpriteSheet;
import Entity.SizakAnimation;
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
public class Pigeon extends BaseEnemy
{
	public Pigeon(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.PIGEON;

		load("gfx/enemy/13/");

		init(BodyStrings.EnemyFly, id, enemyFactory.PigeonEnemyAnimation);

		loadGun();

		gun.bulletTexture = enemyFactory.FlyBulletTexture;
		gun.bulletSize = new Vector2(15, 15);
		gun.setBulletSpeed(5);

		gunX = 50;
		gunY = 65;
		gunTeta = 235;
		gun.setClipSize(1);
		gun.setRateOfFire(1);
		gun.setReloadTime(100000);
		gunTeta = -90;
		gun.haveReloadSound = false;
	}

	float attackingDistance;
	boolean isGoingOut;

	@Override
	public void attack()
	{
		super.attack();

//		Log.e("Pigeon.java", "Shooting and sound is " + gun.shootingSound);
		setCurrentState(StateEnum.MOVE);
		isGoingOut = true;
	}

	@Override
	public void run()
	{
		super.run();
	}

	@Override
	public void draw(Batch batch)
	{

		if(mainBody != null)
		{
			animationStateTime += gameManager.gameScene.getDeltaTime();
			mainBody.draw(batch, animationStateTime, selectedAnimation);
		}
	}

	@Override
	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr)
	{
		super.create(shootingMode, level, attr);

		isGoingOut = false;

		float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
		float width = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;

		float groundHeight = enemyFactory.gameManager.levelManager.currentLevel.terrain.Points.getLast().y;
		float myHeight = (float) (groundHeight + (Math.random() * 0.1 + 1.1) * SceneManager.WORLD_Y);

		setPosition(originX + width + 100, myHeight);

//		attackingDistance = (float) (SceneManager.WORLD_X * (0.3 + Math.random() * 0.1));

		attackingDistance = gameManager.selectedCar.body.bodies.get(0).getWidth() / 2 - 10;
	}

	@Override
	public void decide()
	{
		super.decide();

		float carX = gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().x *
				PhysicsConstant.PIXEL_TO_METER;

		if(isGoingOut)
			return;

		if(x - carX < attackingDistance && currentState != StateEnum.ATTACK)
		{
			setCurrentState(StateEnum.ATTACK);
		}
	}
}
