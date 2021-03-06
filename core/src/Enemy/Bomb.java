package Enemy;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import EnemyBase.BaseEnemy;
import EnemyBase.EnemyFactory;
import GameScene.GameManager;
import GameScene.GameScene;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
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

		init(BodyStrings.DrivingEnemy, id, enemyFactory.BombEnemyAnimation);

		speed = 0;
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

		if(shootingMode == null)
		{
			if(gameManager.levelManager.currentLevel.getCurrentPart().mode == GameScene.LevelModeEnum.Shooting)
			{
				shootingMode = (ShootingMode)gameManager.levelManager.currentLevel.getCurrentPart();
//				Log.e("Bomb.java", "enemyMax = " + shootingMode.enemyMax);
			}
		}

		isKillCount = false;

		gameManager.enemyInitCount++;

		float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
		float originY = CameraHelper.getYMin(gameManager.gameScene.camera);
		float width  = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;
		float height = SceneManager.WORLD_Y * gameManager.gameScene.camera.zoom;

		mainBody.getmBody().setGravityScale(1);

//		mainBody.getmBody().setLinearVelocity(0, -1);
	}

	@Override
	public void move()
	{
	}


	Timer t = new Timer();
	@Override
	public void hitByCar(Contact contact, String carData)
	{
		super.hitByCar(contact, carData);

		setCurrentState(StateEnum.BOMB_EXPLODE);
		selectedAnimation = animatedSpriteSheet.getAnimationID(EnemyFactory.ENEMY_ANIMATION_EXPLODE_STRING);

		t.scheduleTask(new Timer.Task()
		{
			@Override
			public void run()
			{
				release(false);
				gameManager.selectedCar.damage(getDamage());
			}
		}, animatedSpriteSheet.getAnimation(EnemyFactory.ENEMY_ANIMATION_EXPLODE_STRING).animDuration);
	}

	@Override
	public void hitBy(Contact contact)
	{
		String s1 = (String)contact.getFixtureA().getBody().getUserData();
		String s2 = (String)contact.getFixtureB().getBody().getUserData();

		if(BodyStrings.isEnemy(s1) && BodyStrings.isEnemy(s2))
			contact.setEnabled(false);
		
		super.hitBy(contact);
	}
}
