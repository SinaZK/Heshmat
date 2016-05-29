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
public class RedBird extends BaseEnemy
{
	public RedBird(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.RED_BIRD;

		fullImageWidth = 200;
		fullImageHeight = 133;

		MAX_HP = 10;

		init(BodyStrings.EnemyPigeon, id, enemyFactory.RedBirdEnemyAnimation);
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
	public void create(ShootingMode shootingMode, ArrayList<String> attr)
	{
		super.create(shootingMode, attr);

		float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
		float originY = CameraHelper.getYMin(gameManager.gameScene.camera);
		float width  = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;
		float height = SceneManager.WORLD_Y * gameManager.gameScene.camera.zoom;

		setPosition(originX + width + 100, originY + height - 200);
		mainBody.getmBody().setLinearVelocity(-2, 0);
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
