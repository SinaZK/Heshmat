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
public class RedBird extends BaseEnemy
{
	public RedBird(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.RED_BIRD;

		load("gfx/enemy/4/");

		init(BodyStrings.EnemyFly, id, enemyFactory.RedBirdEnemyAnimation);
	}

	@Override
	public void attack()
	{

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

        float groundHeight = enemyFactory.gameManager.levelManager.currentLevel.terrain.Points.getLast().y;
        float myHeight = (float) (groundHeight + (Math.random() * 0.15 + 0.55) * SceneManager.WORLD_Y);

        setPosition(originX + width + 100, myHeight);

	}

    @Override
	public void release()
	{
		super.release();
	}
}
