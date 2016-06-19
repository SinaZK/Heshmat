package GameScene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import BaseCar.BaseCar;
import EnemyBase.EnemyFactory;
import Entity.HPBarSprite;
import Human.SimpleHuman;
import Misc.Log;
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
		hpBarSprite = new HPBarSprite("gfx/hpbar.png", 7, 1, gameScene.disposeTextureArray);

		selectedCar = CarSorter.createSelectedCar(this, gameScene.world, gameScene.disposeTextureArray, gameScene.act.selectorStatData.selectedCar);
		assert selectedCar != null;
		selectedCar.body.setCenterPosition(200 / PhysicsConstant.PIXEL_TO_METER, 300 / PhysicsConstant.PIXEL_TO_METER);
		selectedCar.setFromCarModel(gameScene.carModel);

		levelManager = new LevelManager(this);

		gunManager.create();
		levelManager.create("gfx/lvl/pack" + activity.selectorStatData.selectedLevelPack + "/" + activity.selectorStatData.selectedLevel + "/");
		enemyFactory.create();

		shooterHuman = new SimpleHuman(this);
		shooterHuman.create(50, 200);
		shooterHuman.setPosition(220, 160);
	}


	public void run()
	{
		bulletFactory.run();
		enemyFactory.run();
		levelManager.run();
		gunManager.run();
		shooterHuman.run();

		if(levelManager.levelMode == GameScene.LevelMode.Shooting)
			gameScene.camera.zoom = levelManager.currentLevel.terrain.cameraZoom;

		if(levelManager.levelMode == GameScene.LevelMode.Driving)
		{
			gameScene.camera.zoom = levelManager.currentLevel.terrain.cameraZoom;
			gameScene.camera.position.x = selectedCar.body.bodies.get(0).getmBody().getPosition().x * PhysicsConstant.PIXEL_TO_METER + 400;
			gameScene.camera.position.y = selectedCar.body.bodies.get(0).getmBody().getPosition().y * PhysicsConstant.PIXEL_TO_METER + 80;
		}

		if(levelManager.levelMode == GameScene.LevelMode.Finish)
			gameScene.camera.zoom = levelManager.currentLevel.terrain.cameraZoom;
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
		if(levelManager.levelMode == GameScene.LevelMode.Shooting)
		{
			inputMultiplexer.addProcessor(gameScene.shootingModeHUD);
			gunManager.setInput(inputMultiplexer);
		}

		if(levelManager.levelMode == GameScene.LevelMode.Driving || levelManager.levelMode == GameScene.LevelMode.Finish)
		{
			inputMultiplexer.addProcessor(gameScene.drivingModeHUD);
		}
	}

	public void drawHUD()
	{
		if(levelManager.levelMode == GameScene.LevelMode.Shooting)
			gameScene.shootingModeHUD.draw();

		if(levelManager.levelMode == GameScene.LevelMode.Driving || levelManager.levelMode == GameScene.LevelMode.Finish)
			gameScene.drivingModeHUD.draw();
	}

}
