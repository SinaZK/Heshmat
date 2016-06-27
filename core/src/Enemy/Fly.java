package Enemy;

import java.util.ArrayList;

import BaseLevel.ShootingMode;
import EnemyBase.BaseEnemy;
import GameScene.GameManager;
import Misc.BodyStrings;
import Misc.CameraHelper;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;
import Scene.Garage.GunSelectEntity;
import SceneManager.SceneManager;
import Sorter.GunSorter;
import WeaponBase.BaseGun;

/**
 * Created by sinazk on 5/6/16.
 * Hi 1:19
 */
public class Fly extends BaseEnemy
{
	public Fly(GameManager gameManager, int id)
	{
		super(gameManager, id);

		enemyType = EnemyType.FLY;

		load("gfx/enemy/2/");

		init(BodyStrings.EnemyFly, id, enemyFactory.FlyEnemyAnimation);
        gun = GunSorter.createGunByType(gameManager, GunSorter.GunType.Pistol);
        gun.rateOfFire = fireRate;
        gun.ammo = 100;
	}

    BaseGun gun;
    float fireRate = 1;

	@Override
	public void attack()
	{
        gun.shoot();
        Log.e("Fly.java", "attack");
	}

	@Override
	public void run()
	{
		super.run();

        gun.setPosition(x, y);
	}

	@Override
	public void create(ShootingMode shootingMode, int level, ArrayList<String> attr)
	{
		super.create(shootingMode, level, attr);

        float originX = CameraHelper.getXMin(gameManager.gameScene.camera);
        float width  = SceneManager.WORLD_X * gameManager.gameScene.camera.zoom;

      super.decide();  float groundHeight = enemyFactory.gameManager.levelManager.currentLevel.terrain.Points.getLast().y *
                PhysicsConstant.PIXEL_TO_METER;
        float myHeight = (float) (groundHeight + (Math.random() * 0.25 + 0.5) * SceneManager.WORLD_Y);

        setPosition(originX + width + 100, myHeight);
	}

    @Override
    public void decide() {
        super.decide();

        float carX = gameManager.selectedCar.body.bodies.get(0).getmBody().getWorldCenter().x *
                PhysicsConstant.PIXEL_TO_METER;

        if(x - carX < SceneManager.WORLD_X * 0.3) {
            setCurrentState(StateEnum.ATTACK);
        }

//        Log.e("Fly.java", "State = " + currentState);
    }

    @Override
	public void release()
	{
		super.release();
	}
}
