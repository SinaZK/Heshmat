package Enemy;

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
public class Bat extends BaseEnemy
{
	public Bat(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.BAT;

		load("gfx/enemy/9/");

		init(BodyStrings.EnemyFly, id, enemyFactory.BatEnemyAnimation);
	}

	boolean isShoot;
	@Override
	public void run()
	{
		super.run();

		float myX = mainBody.getmBody().getWorldCenter().x * PhysicsConstant.PIXEL_TO_METER;
		float carX = gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().x * PhysicsConstant.PIXEL_TO_METER;

		if(!isShoot)
		{
			if(Math.abs(carX - myX) < 10)
			{
				isShoot = true;
				dropBomb();
			}
		}
	}

	private void dropBomb()
	{
		float myX = mainBody.getmBody().getWorldCenter().x * PhysicsConstant.PIXEL_TO_METER;

		Bomb b = enemyFactory.getBomb(level, null);
		b.setPosition(myX, y);
	}

	@Override
	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr)
	{
		super.create(shootingMode, level, attr);

		isShoot = false;
        float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
        float width = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;

        float groundHeight = enemyFactory.gameManager.levelManager.currentLevel.terrain.Points.getLast().y;
        float myHeight = (float) (groundHeight + (Math.random() * 0.1 + 1.1) * SceneManager.WORLD_Y);

        setPosition(originX + width + 100, myHeight);
	}

	@Override
	public void attack()
	{
	}

	@Override
	public void move()
	{
		super.move();
	}

	@Override
	public void release()
	{
		super.release();
	}

	@Override
	public void setLevel(int level)
	{
		super.setLevel(level);
	}
}
