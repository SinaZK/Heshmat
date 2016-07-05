package Enemy.EnemyState;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Contact;

import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import EnemyBase.DrivingModeEnemy;
import EnemyBase.EnemyFactory;
import GameScene.GameManager;
import Misc.BodyStrings;

/**
 * Created by sinazk on 7/4/16.
 * 06:54
 */
public class TrafficLight extends DrivingModeEnemy
{
	public TrafficLight(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.TrafficLight;

		load("gfx/enemy/driving/traffic_light/");
		initFromAnimation(BodyStrings.DrivingEnemy, id, gameManager.enemyFactory.DrivingEnemiesSpriteSheet.getAnimation(EnemyFactory.ENEMY_DRIVING_TRAFFIC_LIGHT));
	}

	@Override
	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr)
	{
		super.create(shootingMode, level, attr);

	}

	@Override
	public void draw(Batch batch)
	{
		super.draw(batch);
	}

	@Override
	public void run()
	{
		super.run();

		if(currentState == 1)
			isAttachedToGround = false;
	}

	@Override
	public void hitByCar(Contact contact, String carData)
	{
		super.hitByCar(contact, carData);
	}
}
