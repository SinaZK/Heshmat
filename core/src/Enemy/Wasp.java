package Enemy;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import BaseLevel.Modes.ShootingMode;
import EnemyBase.BaseEnemy;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
import PhysicsFactory.PhysicsConstant;
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
        loadGun();

        gun.bulletTexture = enemyFactory.InvisibleBulletTexture;
        gun.bulletSize = new Vector2(15, 15);
        gun.setBulletSpeed(5);

        gunX = 50;
        gunY = 65;
        gunTeta = 235;

        gun.setClipSize(10000);
        gun.setRateOfFire(5);
        gun.setReloadTime(100000);
        gun.setBulletHP(1);
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

	}

    float attackingDistance;


    @Override
	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr) {
        super.create(shootingMode, level, attr);

        float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
        float width  = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;

        float groundHeight = enemyFactory.gameManager.levelManager.currentLevel.terrain.Points.getLast().y;
        float myHeight = (float) (groundHeight + (Math.random() * 0.12 + 0.2) * SceneManager.WORLD_Y);

        setPosition(originX + width + 100, myHeight);
        attackingDistance = (float) (SceneManager.WORLD_X * (0.20 + Math.random() * 0.05));
	}

    @Override
    public void decide() {
        super.decide();

        float carX = gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().x *
                PhysicsConstant.PIXEL_TO_METER;

        if(x - carX < attackingDistance && currentState != StateEnum.ATTACK)
        {
            setCurrentState(StateEnum.ATTACK);
        }
    }

}
