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
import Terrain.Terrain;

/**
 * Created by sinazk on 7/4/16.
 * 06:54
 */
public class Coin extends DrivingModeEnemy
{
	public Coin(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.StreetLight;

		load("gfx/enemy/driving/coin/");
		initFromAnimation(BodyStrings.DrivingEnemy, id, gameManager.enemyFactory.DrivingEnemiesSpriteSheet.getAnimation(EnemyFactory.ENEMY_DRIVING_COIN));
	}

	@Override
	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr)
	{
		super.create(shootingMode, level, attr);
        toY = -1;
	}

	@Override
	public void draw(Batch batch)
	{
        if(currentState >= stateCount)
            currentState = stateCount - 1;
        states.get(currentState).drawWithoutHpBar(batch);
	}

    float toY;
	@Override
	public void run()
	{
		super.run();

        Terrain terrain = gameManager.levelManager.currentLevel.terrain;
        if(terrain.getXof(x) == -1){}
        else {
            if(toY == -1) {
                setPosition(x, y + terrain.points.get(terrain.getXof(x)).y + 50);
                toY = 1;
            }
        }

		if(currentState == 1)
			isAttachedToGround = false;
	}

	@Override
	public void hitByCar(Contact contact, String carData)
	{
		super.hitByCar(contact, carData);
	}
}
