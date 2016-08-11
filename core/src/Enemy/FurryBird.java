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
public class FurryBird extends BaseEnemy
{
	public FurryBird(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.FURRY_BIRD;

		load("gfx/enemy/7/");

		init(BodyStrings.EnemyFly, id, enemyFactory.FurryBirdEnemyAnimation);
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

        float groundHeight = enemyFactory.gameManager.levelManager.currentLevel.terrain.points.getLast().y;
        float myHeight = (float) (groundHeight + (Math.random() * 0.1 + 1) * SceneManager.WORLD_Y);

        setPosition(originX + width + 100, myHeight);
    }
}
