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
public class Wasp extends BaseEnemy
{
	public Wasp(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.WASP;

		load("gfx/enemy/3/");

		init(BodyStrings.EnemyFly, id, enemyFactory.WaspEnemyAnimation);
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
		float originY = CameraHelper.getYMin(gameManager.gameScene.camera);
		float width  = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;
		float height = SceneManager.WORLD_Y * gameManager.gameScene.camera.zoom;

		setPosition(originX + width + 100, originY + height - 200);
	}

	@Override
	public void release()
	{
		super.release();
	}
}
