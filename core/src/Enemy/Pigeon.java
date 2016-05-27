package Enemy;

import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.ArrayList;

import BaseLevel.ShootingMode;
import EnemyBase.BaseEnemy;
import EnemyBase.EnemyFactory;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.*;
import SceneManager.SceneManager;
import WeaponBase.BulletFactory;
import heshmat.MainActivity;

/**
 * Created by sinazk on 5/6/16.
 * Hi 1:19
 */
public class Pigeon extends BaseEnemy
{
	public Pigeon(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.Pigeon;

		fullImageWidth = 100;
		fullImageHeight = 40;

		MAX_HP = 10;

		init(BodyStrings.EnemyPigeon, id, enemyFactory.PigeonEnemyTexture);
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
