package Enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import EnemyBase.BaseEnemy;
import EnemyBase.EnemyFactory;
import Entity.AnimatedSprite;
import GameScene.GameManager;
import GameScene.GameScene;
import Misc.BodyStrings;
import Misc.CameraHelper;
import SceneManager.SceneManager;

/**
 * Created by sinazk on 5/6/16.
 * Hi 1:19
 */

public class Worm extends BaseEnemy
{
	AnimatedSprite explosionSprite;
	public static float explosionTime = 0.23f;

	public Worm(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.WORM;

		load("gfx/enemy/11/");

		init(BodyStrings.DrivingEnemy, id, enemyFactory.WormEnemyAnimation);

		explosionSprite = new AnimatedSprite("gfx/explosion.png", 1, 12, 16, explosionTime, gameManager.gameScene.disposeTextureArray);
		explosionSprite.isDisabled = true;
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

		isHIT = false;
		isKillCount = false;
		gameManager.enemyInitCount++;
		explosionSprite.isDisabled = true;

		float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
		float originY = CameraHelper.getYMin(gameManager.gameScene.camera);
		float width  = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;
		float height = SceneManager.WORLD_Y * gameManager.gameScene.camera.zoom;

		setPosition(originX + width + 100, originY + height - 200);

		mainBody.getmBody().setGravityScale(1);
	}

	boolean isHIT = false;
	Timer t = new Timer();
	@Override
	public void hitByCar(Contact contact, String carData)
	{
		super.hitByCar(contact, carData);

		setCurrentState(StateEnum.BOMB_EXPLODE);
		selectedAnimation = animatedSpriteSheet.getAnimationID(EnemyFactory.ENEMY_ANIMATION_EXPLODE_STRING);

		isHIT = true;
		explosionSprite.reset();
		explosionSprite.setPosition(x - fullImageWidth / 2, y - fullImageHeight / 2);

		t.scheduleTask(new Timer.Task()
		{
			@Override
			public void run()
			{
				release(false);
				gameManager.selectedCar.damage(getDamage());
			}
		}, explosionTime);
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

	@Override
	public void draw(Batch batch)
	{
		if(!isHIT)
			super.draw(batch);
		else
			explosionSprite.draw(batch, gameManager.gameScene.getDeltaTime());
	}
}
