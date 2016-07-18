package SceneManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import BaseCar.CarLoader;
import BaseCar.SizakCarModel;
import Cars.DenaCar;
import Dialog.DialogManager;
import GameScene.GameScene;
import Misc.Log;
import Misc.TextureHelper;
import PurchaseIAB.purchaseIAB;
import Scene.BaseScene;
import Scene.EndGameScene;
import Scene.Garage.GarageScene;
import Scene.LevelPackageScene;
import Scene.LevelSelectorScene;
import Scene.MainMenuScene;
import Scene.PurchaseScene;
import Scene.SplashScene;
import heshmat.MainActivity;

public class SceneManager
{

	public static int WORLD_X = 800;
	public static int WORLD_Y = 480;

	public static int ENDLESS_STARS = 15;
	public static int VideoAward = 1000;

	public static int LVL_PACK_MAX_NUM = 2;
	public static int LVL_MAX_NUM = 12;
	public static int CAR_MAX_NUM = 20;
	public static int GUN_MAX_NUM = 6;

	public static int LVL_PACK_NUM = 2;
	public static int CAR_NUM = 5;
	public static int GUN_SLOT_NUM = 1;
	public static int GUN_NUM = 6;
	public static int ENEMY_NUM = 12;

	public MainActivity act;
	public DialogManager dialogManager;

	purchaseIAB.IABInterface purchaseHelper;

	ArrayList<Texture> disposableTextures = new ArrayList<Texture>();

	public SceneManager(MainActivity act, purchaseIAB.IABInterface p)
	{
		this.act = act;
		purchaseHelper = p;
		dialogManager = new DialogManager(act);

		createAndLoadGoldBar();
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
		PURCHASE_SCENE,
	}

	public SCENES currentScene;
	public BaseScene currentBaseScene;

	BaseScene blankScene;
	SplashScene splashScene;
	MainMenuScene mainMenuScene;
	LevelPackageScene levelPackageScene;
	LevelSelectorScene levelSelectorScene;

	public GarageScene garageScene;
	EndGameScene endGameScene;

	public GameScene gameScene;

	public SCENES getCurrentScene()
	{
		return currentScene;
	}

	public void setCurrentScene(SCENES currentScene, SizakCarModel carModel)
	{
		this.currentScene = currentScene;
		gameScene = null;
		switch (currentScene)
		{
			case BLANK:
				blankScene = new BaseScene(act, new ExtendViewport(WORLD_X, WORLD_Y));
				currentBaseScene = blankScene;
				break;

//			case END_GAME_SCENE:
//				endGameScene = new EndGameScene(this, new ExtendViewport(WORLD_X, WORLD_Y));
//				currentBaseScene = endGameScene;
//				break;

			case PURCHASE_SCENE:
				currentBaseScene = new PurchaseScene(this, new ExtendViewport(WORLD_X, WORLD_Y));
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

	Sprite goldSprite;
	public void createAndLoadGoldBar()
	{
		goldSprite = new Sprite(TextureHelper.loadTexture("gfx/goldbar.png", disposableTextures));
	}

	public void drawGoldSprite(Batch batch, float x, float y)
	{
		goldSprite.setPosition(x, y);
		goldSprite.draw(batch);
	}

	public void drawGoldSprite(Batch batch)
	{
		float DX = currentBaseScene.DX;
		float DY = currentBaseScene.DY;

		float x = DX + 28;
		float y = DY + 420;

		goldSprite.setPosition(x, y);
		goldSprite.draw(batch);

		float coinSize = 20;
		float fontSize = 22;
		float textSize = getDigitNum(act.getShowGold()) * fontSize / 2 + act.font22.getSpaceWidth() * (getDigitNum(act.getShowGold()) - 1);
		float imageWidth = goldSprite.getWidth() - coinSize;//minus the coin width


		act.font18.setColor(Color.BLACK);
		act.font18.draw(batch, "" + act.getShowGold(), x + (imageWidth - textSize) / 2 + coinSize, y + 25);

//		Log.e("SceneManager.java", "digitNum = " + getDigitNum(act.getShowGold()) + " textSize = " + textSize + " SpaceWidth = " + act.font22.getSpaceWidth());
	}

	public static long getDigitNum(long a)
	{
		if(a == 0)
			return 1;

		int ct = 0;
		while (a > 0)
		{
			a /= 10;
			ct++;
		}

		return ct;
	}

	public void dispose()
	{
		for (int i = 0;i < disposableTextures.size();i++)
			disposableTextures.get(i).dispose();
	}
}
