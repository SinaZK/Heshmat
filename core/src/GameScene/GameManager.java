package GameScene;


import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import BaseCar.BaseCar;
import EnemyBase.EnemyFactory;
import Entity.HPBarSprite;
import Human.SimpleHuman;
import PhysicsFactory.PhysicsConstant;
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
	SimpleHuman shooterHuman;

	public HPBarSprite hpBarSprite;

	GameManager(GameScene mScene)
	{
		gameScene = mScene;
		activity = gameScene.act;
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

		gunManager.create();
		enemyFactory.create();

		if(activity.selectorStatData.selectedLevel == -1)//endless
		{
			levelManager.create("gfx/lvl/pack" + activity.selectorStatData.selectedLevelPack + "/endless/", LevelManager.LevelType.ENDLESS);
		}
		else
			levelManager.create("gfx/lvl/pack" + activity.selectorStatData.selectedLevelPack + "/" + activity.selectorStatData.selectedLevel + "/", LevelManager.LevelType.NORMAL);

		shooterHuman = new SimpleHuman(this);
		shooterHuman.create(50, 200);
		shooterHuman.setPosition(220, 160);
	}


	public void run()
	{
		levelManager.run();
		bulletFactory.run();
		enemyFactory.run();
		gunManager.run();
		shooterHuman.run();
	}

	public void draw()
	{
		levelManager.drawOnBatch(gameScene.getBatch());
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
	}

	public void restart()
	{
		gunManager.restart();
		enemyFactory.restart();
		bulletFactory.restart();
		selectedCar.reset();
		levelManager.restart();
	}

	public void resume()
	{
		levelManager.resume();
		gunManager.resume();
		enemyFactory.resume();
		bulletFactory.resume();
	}

}
