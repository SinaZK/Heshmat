package GameScene;


import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import BaseCar.BaseCar;
import EnemyBase.EnemyFactory;
import Entity.HPBarSprite;
import Human.DriverHuman;
import Human.SimpleHuman;
import Misc.Log;
import PhysicsFactory.PhysicsConstant;
import Scene.LevelSelectorScene;
import Sorter.CarSorter;
import WeaponBase.BulletFactory;
import heshmat.MainActivity;

public class GameManager
{
	public MainActivity activity;
	public GameScene gameScene;
	public LevelManager levelManager;
	public BulletFactory bulletFactory;
	public EnemyFactory enemyFactory;
	public GunManager gunManager;


	public BaseCar selectedCar;
	public SimpleHuman shooterHuman;
	public DriverHuman driverHuman;

	public HPBarSprite hpBarSprite;

	GameManager(GameScene mScene)
	{
		gameScene = mScene;
		activity = gameScene.act;
		enemyInitCount = 0;
	}

	public void create()
	{
		bulletFactory = new BulletFactory(gameScene.act, gameScene);
		enemyFactory = new EnemyFactory(this);
		gunManager = new GunManager(this);
		hpBarSprite = new HPBarSprite(gameScene.disposeTextureArray);

		selectedCar = CarSorter.createSelectedCar(this, gameScene.world, gameScene.disposeTextureArray, gameScene.act.selectorStatData.selectedCar);
		assert selectedCar != null;
		selectedCar.setFirstPos(200 / PhysicsConstant.PIXEL_TO_METER, 300 / PhysicsConstant.PIXEL_TO_METER);
		selectedCar.setFromCarModel(gameScene.carModel);


        levelManager = new LevelManager(this);

        switch (activity.selectorStatData.selectedLevel)
        {
            case LevelSelectorScene.LINE_INT:
                levelManager.create("gfx/lvl/pack" + activity.selectorStatData.selectedLevelPack
                        + "/line/", LevelManager.LevelType.LINE);
                break;
            case LevelSelectorScene.LOOP_INT:
                levelManager.create("gfx/lvl/pack" + activity.selectorStatData.selectedLevelPack
                        + "/loop/", LevelManager.LevelType.LOOP);
                break;

            default:
                levelManager.create("gfx/lvl/pack" + activity.selectorStatData.selectedLevelPack
                        + "/" + activity.selectorStatData.selectedLevel + "/", LevelManager.LevelType.NORMAL);
        }

		gunManager.create();
		enemyFactory.create();

		shooterHuman = new SimpleHuman(this);
		shooterHuman.create(37, 169);

		driverHuman = new DriverHuman(this);

		enemyKilledCount = 0;

		activity.gameStatData.numberOfThisTimePlayed++;
	}

	public void run()
	{
		levelManager.run();
		bulletFactory.run();
		enemyFactory.run();
		gunManager.run();
		shooterHuman.run();
		driverHuman.run();

	}

	public void draw()
	{
		levelManager.drawOnBatch(gameScene.getBatch());
		driverHuman.draw(gameScene.getBatch());
		selectedCar.draw(gameScene.getBatch());
		enemyFactory.draw(gameScene.getBatch());
		bulletFactory.draw(gameScene.getBatch());
		shooterHuman.draw(gameScene.getBatch());
	}

	public void drawOnPolygonBatch(PolygonSpriteBatch polygonSpriteBatch)
	{
		levelManager.drawOnPolygonSpriteBatch(polygonSpriteBatch);
	}

	public void setInput(InputMultiplexer inputMultiplexer)
	{
		if(levelManager.levelModeEnum == GameScene.LevelModeEnum.Shooting)
		{
			inputMultiplexer.addProcessor(gameScene.shootingModeHUD);

			gunManager.setInput(inputMultiplexer);
		}

		if(levelManager.levelModeEnum == GameScene.LevelModeEnum.Driving || levelManager.levelModeEnum == GameScene.LevelModeEnum.Finish)
		{
			inputMultiplexer.addProcessor(gameScene.drivingModeHUD);
		}
	}

	public void drawHUD()
	{
		if(levelManager.levelModeEnum == GameScene.LevelModeEnum.Shooting)
			gameScene.shootingModeHUD.draw();

		if(levelManager.levelModeEnum == GameScene.LevelModeEnum.Driving || levelManager.levelModeEnum == GameScene.LevelModeEnum.Finish)
			gameScene.drivingModeHUD.draw();
	}

	public void pause()
	{
		levelManager.pause();
		gunManager.pause();
		enemyFactory.pause();
		bulletFactory.pause();
		selectedCar.pause();
	}

	public void restart()
	{
		gunManager.restart();
		enemyFactory.restart();
		bulletFactory.restart();
		selectedCar.reset();
		levelManager.restart();
		driverHuman.reset();
		selectedCar.resume();

		enemyKilledCount = 0;
		goldCollect = 0;
		distanceTraveled = 0;

		activity.gameStatData.numberOfThisTimePlayed++;
	}

	public void resume()
	{
		levelManager.resume();
		gunManager.resume();
		enemyFactory.resume();
		bulletFactory.resume();
	}

	public void dispose()
	{
		selectedCar.dispose();
	}

	public int enemyKilledCount, enemyInitCount;
	public long goldCollect = 0;
	public float distanceTraveled;

}
