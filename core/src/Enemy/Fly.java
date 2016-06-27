package Enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import BaseLevel.ShootingMode;
import EnemyBase.BaseEnemy;
import EnemyBase.EnemyFactory;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;
import SceneManager.SceneManager;
import Sorter.GunSorter;
import WeaponBase.BaseGun;

/**
 * Created by sinazk on 5/6/16.
 * Hi 1:19
 */
public class Fly extends BaseEnemy
{
	public Fly(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.FLY;

		load("gfx/enemy/2/");

		init(BodyStrings.EnemyFly, id, enemyFactory.FlyEnemyAnimation);

		loadGun();

		gun.bulletTexture = enemyFactory.FlyBulletTexture;
		gun.bulletSize = new Vector2(35, 35);
		gun.bulletSpeed = 5;

		gunX = 50;
		gunY = 65;
		gunTeta = 235;
	}

	@Override
	public void attack()
	{
		super.attack();
	}

	@Override
	public void draw(Batch batch)
	{
		super.draw(batch);

//		gun.draw(batch);
	}

	@Override
	public void run()
	{
		super.run();
	}

	@Override
	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr)
	{
		super.create(shootingMode, level, attr);

		float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
		float width = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;

		float groundHeight = enemyFactory.gameManager.levelManager.currentLevel.terrain.Points.getLast().y *
				PhysicsConstant.PIXEL_TO_METER;
		float myHeight = (float) (groundHeight + (Math.random() * 0.25 + 0.5) * SceneManager.WORLD_Y);

		setPosition(originX + width + 100, myHeight);
	}

	@Override
	public void decide()
	{
		super.decide();

		float carX = gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().x *
				PhysicsConstant.PIXEL_TO_METER;

		if(x - carX < SceneManager.WORLD_X * 0.3 && currentState != StateEnum.ATTACK)
		{
			setCurrentState(StateEnum.ATTACK);
		}
	}

	@Override
	public void release()
	{
		super.release();
	}
}
