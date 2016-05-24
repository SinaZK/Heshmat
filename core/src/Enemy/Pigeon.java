package Enemy;

import com.badlogic.gdx.physics.box2d.BodyDef;

import BaseLevel.ShootingMode;
import EnemyBase.AirEnemy;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import Physics.CzakBody;
import PhysicsFactory.*;
import SceneManager.SceneManager;
import heshmat.MainActivity;

/**
 * Created by sinazk on 5/6/16.
 * Hi 1:19
 */
public class Pigeon extends AirEnemy
{
	public Pigeon(MainActivity act, int id)
	{
		super(act, id);

		enemyType = EnemyType.Pigeon;

		fullImageWidth = 100;
		fullImageHeight = 40;

		MAX_HP = 10;

		mainBody = new CzakBody(PhysicsFactory.createBoxBody(enemyFactory.mScene.world, 0, 0, fullImageWidth, fullImageHeight, BodyDef.BodyType.DynamicBody),
				enemyFactory.PigeonEnemyTexture);
		mainBody.getmSprite().get(0).setSize(fullImageWidth, fullImageHeight);
		mainBody.getmBody().setGravityScale(0);
		mainBody.setUserData(BodyStrings.ENEMY_STRING + " " + BodyStrings.EnemyPigeon + " " + id);
	}

	@Override
	public void attack()
	{
		super.attack();
	}

	@Override
	public void run()
	{
		super.run();

		if(mainBody != null)
			if(mainBody.getmBody().getWorldCenter().x * ratio + 100 < CameraHelper.getXMin(gameManager.gameScene.camera))
				shouldRelease = true;
	}

	float ratio = PhysicsConstant.PIXEL_TO_METER;
	@Override
	public void create(ShootingMode shootingMode)
	{
		super.create(shootingMode);

		float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
		float originY = CameraHelper.getYMin(gameManager.gameScene.camera);
		float width  = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;
		float height = SceneManager.WORLD_Y * gameManager.gameScene.camera.zoom;

//		Log.e("Pigeon.java", "Origin : " + originX + ", " + originY);
//		Log.e("Pigeon.java", "CarPos : " + shootingMode.firstCarX * ratio + ", " + shootingMode.firstCarY * ratio);
//		Log.e("Pigeon.java", "Creating at : " + (originX + 00) + ", " + (originY + 800));

		setPosition(originX + width + 100, originY + height - 200);
		mainBody.getmBody().setLinearVelocity(-2, 0);
	}

	@Override
	public void hitByBullet(String bulletData)
	{
		super.hitByBullet(bulletData);
	}

	@Override
	public void release()
	{
		super.release();
	}
}
