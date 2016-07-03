package Enemy;

import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import EnemyBase.BaseEnemy;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
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
	public void attack()
	{

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
