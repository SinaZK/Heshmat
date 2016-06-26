package SceneManager;

import com.badlogic.gdx.utils.viewport.ExtendViewport;

import Dialog.DialogManager;
import GameScene.GameScene;
import BaseCar.SizakCarModel;
import PurchaseIAB.purchaseIAB;
import Scene.BaseScene;
import Scene.EndGameScene;
import Scene.Garage.GarageScene;
import Scene.LevelPackageScene;
import Scene.LevelSelectorScene;
import Scene.MainMenuScene;
import Scene.SplashScene;
import Scene.WeaponScene;
import heshmat.MainActivity;

public class SceneManager
{

	public static int WORLD_X = 800;
	public static int WORLD_Y = 480;

	public static int LVL_PACK_MAX_NUM = 2;
	public static int LVL_MAX_NUM = 12;
	public static int CAR_MAX_NUM = 2;
	public static int GUN_MAX_NUM = 5;

	public static int LVL_PACK_NUM = 2;
	public static int CAR_NUM = 2;
	public static int GUN_SLOT_NUM = 1;
	public static int GUN_NUM = 5;

	public MainActivity act;
	public DialogManager dialogManager;

	purchaseIAB.IABInterface purchaseHelper;

	public SceneManager(MainActivity act, purchaseIAB.IABInterface p)
	{
		this.act = act;
		purchaseHelper = p;
		dialogManager = new DialogManager(act);
	}

	public enum SCENES
	{
		BLANK,
		SPLASH,
		MAIN_MENU,
		GAME_SCENE,
		LEVEL_PACKAGE_SELECTOR,
		LEVEL_SELECTOR,
		GARAGE_SCENE,
		WEAPON_SCENE,
		END_GAME_SCENE,
	}

	public SCENES currentScene;
	public BaseScene currentBaseScene;

	BaseScene blankScene;
	SplashScene splashScene;
	MainMenuScene mainMenuScene;
	LevelPackageScene levelPackageScene;
	LevelSelectorScene levelSelectorScene;

	GarageScene garageScene;
	WeaponScene weaponScene;
	EndGameScene endGameScene;

	public GameScene gameScene;

	public SCENES getCurrentScene()
	{
		return currentScene;
	}

	public void setCurrentScene(SCENES currentScene, SizakCarModel carModel)
	{
		this.currentScene = currentScene;
		switch (currentScene)
		{
			case BLANK:
				blankScene = new BaseScene(act, new ExtendViewport(WORLD_X, WORLD_Y));
				currentBaseScene = blankScene;
				break;

			case END_GAME_SCENE:
				endGameScene = new EndGameScene(this, new ExtendViewport(WORLD_X, WORLD_Y));
				currentBaseScene = endGameScene;
				break;

			case WEAPON_SCENE:
				weaponScene = new WeaponScene(this, new ExtendViewport(WORLD_X, WORLD_Y));
				currentBaseScene = weaponScene;
				break;

			case GARAGE_SCENE:
				garageScene = new GarageScene(this, new ExtendViewport(WORLD_X, WORLD_Y));

				currentBaseScene = garageScene;
				break;

			case LEVEL_SELECTOR:
				levelSelectorScene = new LevelSelectorScene(this, new ExtendViewport(WORLD_X, WORLD_Y));
				currentBaseScene = levelSelectorScene;
				break;

			case LEVEL_PACKAGE_SELECTOR:
				levelPackageScene = new LevelPackageScene(this, new ExtendViewport(WORLD_X, WORLD_Y));
				currentBaseScene = levelPackageScene;
				break;

			case SPLASH:
				splashScene = new SplashScene(this, new ExtendViewport(WORLD_X, WORLD_Y));
				currentBaseScene = splashScene;
				break;

			case MAIN_MENU:
				mainMenuScene = new MainMenuScene(this, new ExtendViewport(WORLD_X, WORLD_Y));
				currentBaseScene = mainMenuScene;
				break;

			case GAME_SCENE:
				gameScene = new GameScene(this, new ExtendViewport(WORLD_X, WORLD_Y), carModel);
				currentBaseScene = gameScene;
				break;

			default:
				break;
		}

		createAndStuff(currentBaseScene);

	}

	public void createAndStuff(BaseScene mScene)
	{
		mScene.loadResources();
		mScene.create();
	}

	public void run()
	{
		currentBaseScene.run();
		dialogManager.draw();
		dialogManager.run();
	}
}
