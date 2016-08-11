package Enemy;

import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import EnemyBase.BaseEnemy;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
import PhysicsFactory.PhysicsConstant;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 5/6/16.
 * Hi 1:19
 */

public class BossBird extends BaseEnemy
{
	public BossBird(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.BOSS_BIRD;

		load("gfx/enemy/10/");

		init(BodyStrings.EnemyFly, id, enemyFactory.BossBirdEnemyAnimation);
	}

    @Override
    public void run() {
        super.run();
    }

	float enemyCounter = 0;
	float wormRate = 3;

    @Override
	public void attack()
	{
		enemyCounter += gameManager.gameScene.getDeltaTime();

//		Log.e("Tag", "enemyCounter" + enemyCounter);

		if(enemyCounter >= wormRate)
		{
			enemyCounter = 0;

			Worm worm = enemyFactory.getWorm(level, null);
			worm.setPosition(x, y);
//			Log.e("INIT new Worm", "DAMAGE = " + worm.DAMAGE);
		}
	}

	@Override
	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr)
	{
		super.create(shootingMode, level, attr);

        float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
        float width = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;

        float groundHeight = enemyFactory.gameManager.levelManager.currentLevel.terrain.points.getLast().y;
        float myHeight = (float) (groundHeight + (Math.random() * 0.2 + 1.2) * SceneManager.WORLD_Y) - 20;

        setPosition(originX + width + 100, myHeight);

		float zoom = gameManager.gameScene.camera.zoom;
		attackingDistance = (float) (SceneManager.WORLD_X * zoom * (0.7 + Math.random() * 0.1));

		enemyCounter = wormRate;
	}

	float attackingDistance;
	@Override
	public void decide()
	{
		super.decide();

		float carX = gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().x *
				PhysicsConstant.PIXEL_TO_METER;

		if(x - carX < attackingDistance && currentState != StateEnum.ATTACK)
		{
			setCurrentState(StateEnum.ATTACK);
		}
	}

	@Override
	public void loadGun()
	{
		super.loadGun();
	}
}


