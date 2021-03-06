package Enemy.EnemyState;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;

import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import EnemyBase.DrivingModeEnemy;
import EnemyBase.EnemyFactory;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.Log;
import Terrain.Terrain;

/**
 * Created by sinazk on 7/4/16.
 * 06:54
 */
public class WaterBox extends DrivingModeEnemy
{
	public WaterBox(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.WaterBox;

		load("gfx/enemy/driving/water_box/");
		initFromAnimation(BodyStrings.DrivingEnemy, id, gameManager.enemyFactory.DrivingEnemiesSpriteSheet.getAnimation(EnemyFactory.ENEMY_DRIVING_WATER_BOX));
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

        Terrain terrain = gameManager.levelManager.currentLevel.terrain;
        if(terrain.getXof(x) == -1)
            setBodyType(BodyDef.BodyType.StaticBody);
        else
            setBodyType(BodyDef.BodyType.DynamicBody);

		if(currentState == 1)
			isAttachedToGround = false;
	}

    @Override
	public void attachToGround(float x)
	{
		setPosition(x);
	}

	@Override
	public void hitByCar(Contact contact, String carData)
	{
		super.hitByCar(contact, carData);
	}

}
