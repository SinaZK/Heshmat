package Enemy;

import java.util.ArrayList;

import BaseLevel.ShootingMode;
import EnemyBase.BaseEnemy;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 5/6/16.
 * Hi 1:19
 */
public class Bomb extends BaseEnemy
{
	public Bomb(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.BOMB;

		load("gfx/enemy/12/");

		init(BodyStrings.EnemyFly, id, enemyFactory.BombEnemyAnimation);
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

		shootingMode.enemyMax++;

		float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
		float originY = CameraHelper.getYMin(gameManager.gameScene.camera);
		float width  = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;
		float height = SceneManager.WORLD_Y * gameManager.gameScene.camera.zoom;

		mainBody.getmBody().setLinearVelocity(0, -1);
	}

	@Override
	public void move()
	{

	}

	@Override
	public void release()
	{
		super.release();
	}
}
