package GameScene;


import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import BaseCar.BaseCar;
import BaseCar.CarLoader;
import EnemyBase.EnemyFactory;
import Entity.HPBarSprite;
import PhysicsFactory.PhysicsConstant;
import SceneManager.SceneManager;
import WeaponBase.BaseGun;
import WeaponBase.BulletFactory;
import Weapons.Pistol;
import Weapons.RocketLauncher;

public class GameManager
{
	public GameScene gameScene;
	public LevelManager levelManager;
	public BulletFactory bulletFactory;
	public EnemyFactory enemyFactory;
	public GunManager gunManager;


	public BaseCar selectedCar;

	public HPBarSprite hpBarSprite;

	GameManager(GameScene mScene)
	{
		gameScene = mScene;

		bulletFactory = new BulletFactory(gameScene.act, gameScene);
		enemyFactory = new EnemyFactory(gameScene.act, gameScene);
		gunManager = new GunManager(this);
		hpBarSprite = new HPBarSprite("gfx/hpbar.png", 7, 1, gameScene.disposeTextureArray);

		selectedCar = CarLoader.loadTrainFile(this, "gfx/car/train/train.car", gameScene.world, gameScene.disposeTextureArray);

		levelManager = new LevelManager(this);
	}

	public void create()
	{
		gunManager.create();
		levelManager.create("gfx/lvl/test/");
	}



	public void run()
	{
		bulletFactory.run();
		enemyFactory.run();
		levelManager.run();
		gunManager.run();

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
		gunManager.draw(gameScene.getBatch());
		selectedCar.draw(gameScene.getBatch());
		enemyFactory.draw(gameScene.getBatch());
		bulletFactory.draw(gameScene.getBatch());
	}

	public void drawOnPolygonBatch(PolygonSpriteBatch polygonSpriteBatch)
	{
		levelManager.drawOnPolygonSpriteBatch(polygonSpriteBatch);
	}



	public void setInput(InputMultiplexer inputMultiplexer)
	{
		if(levelManager.levelMode == GameScene.LevelMode.Shooting)
		{
			gunManager.setInput(inputMultiplexer);
			inputMultiplexer.addProcessor(gameScene.shootingModeHUD);
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
