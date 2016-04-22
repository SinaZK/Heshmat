package SceneManager;

import com.badlogic.gdx.utils.viewport.ExtendViewport;

import PurchaseIAB.purchaseIAB;
import Scene.BaseScene;
import GameScene.GameSceneNormal;
import Scene.MainMenuScene;
import heshmat.MainActivity;

public class SceneManager 
{
	public static int WORLD_X = 800;
	public static int WORLD_Y = 480;

	public static int MAP_MAX_NUM = 30;
	public static int CAR_MAX_NUM = 50;
	public static int HUMAN_MAX_NUM = 30;

	public static int HUMAN_NUM = 5;
	public static int MAP_NUM = 8;
	public static int CAR_NUM = 20;
	public MainActivity act;

	purchaseIAB.IABInterface purchaseHelper;

	public SceneManager(MainActivity act, purchaseIAB.IABInterface p)
	{
		this.act = act;
		purchaseHelper = p;
	}

	public enum SCENES
	{
		BLANK,
		SPLASH,
		MAIN_MENU,
		GAME_SCENE,
	}

	public SCENES currentScene;
	public BaseScene currentBaseScene;

	BaseScene blankScene;
	MainMenuScene mainMenuScene;
	public GameSceneNormal gameScene;

	public SCENES getCurrentScene(){return currentScene;}
	public void setCurrentScene(SCENES currentScene)
	{
		this.currentScene = currentScene;

		switch (currentScene) 
		{
		case BLANK:
			blankScene = new BaseScene(act, new ExtendViewport(WORLD_X, WORLD_Y));
			currentBaseScene = blankScene;
			break;

		case SPLASH:
			break;

		case MAIN_MENU:
			mainMenuScene = new MainMenuScene(this, new ExtendViewport(WORLD_X, WORLD_Y));
			createAndStuff(mainMenuScene);
			currentBaseScene = mainMenuScene;
			break;
			
		case GAME_SCENE:
			gameScene = new GameSceneNormal(this, new ExtendViewport(WORLD_X, WORLD_Y));
			createAndStuff(gameScene);
			currentBaseScene = gameScene;
			break;

		default:
			break;
		}

	}

	public void createAndStuff(BaseScene mScene)
	{
		mScene.loadResources();
		mScene.create();
	}

	public void run()
	{
		switch (currentScene) 
		{

		case MAIN_MENU:
			mainMenuScene.run();
			break;

		case GAME_SCENE:
			gameScene.run();
			break;

		default:
			break;
		}
	}
}
