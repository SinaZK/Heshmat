package Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import ChangeAblePackage.PurchaseChangeAbleGestureListenre;
import ChangeAblePackage.PurchaseSelectorHorizentalChange;
import Countly.CountlyStrings;
import Entity.Button;
import Entity.Entity;
import Misc.Log;
import Misc.TextureHelper;
import PurchaseIAB.purchaseIAB;
import SceneManager.SceneManager;

public class PurchaseScene extends BaseScene
{

	public SceneManager mSceneManager;

	public PurchaseScene(SceneManager sceneManager, Viewport v)
	{
		super(sceneManager.act, v);
		mSceneManager = sceneManager;
	}

	static float changeX = 0;
	static float changeY = 10;

	public purchaseIAB.IABInterface purchaseHelper;
	boolean isPricesLoaded;
	ArrayList<Integer> prices;

	public SceneManager.SCENES prevScene;
	public BaseScene lastScene;
	public InputProcessor lastInput;

	Stage changeAbleStage;
	public PurchaseSelectorHorizentalChange change;

	public float DX;
	public float DY;
	String add = "gfx/scene/purchase/";

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;

		final Button exit = new Button(TextureHelper.loadTexture(add + "back1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "back2.png", disposeTextureArray));

		exit.setPosition(DX + 30, DY + 30);

//		exit.setPosition(DX + 30, DY + 300);

		exit.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
				exit();
			}
		});

		Entity back = new Entity(TextureHelper.loadTexture(add + "back.png", disposeTextureArray))
		{
			@Override
			public void draw(Batch batch, float parentAlpha)
			{
				float bX = getWidth() - SceneManager.WORLD_X;
				float bY = getHeight() - SceneManager.WORLD_Y;
				setPosition(DX - bX / 2 + getCamera().position.x - getCamera().viewportWidth / 2,
						DY - bY / 2 + getCamera().position.y - getCamera().viewportHeight / 2);
				super.draw(batch, parentAlpha);
			}
		};
		attachChild(back);

		HUD.addActor(exit);

		changeAbleStage = new Stage(getViewport());
	}

	public BitmapFont font24 = act.font24;

	public void exit()
	{
		font24.setColor(1, 1, 1, 1);
		act.font22.setColor(1, 1, 1, 1);
		dispose();

		mSceneManager.currentScene = prevScene;
		mSceneManager.currentBaseScene = lastScene;

		Gdx.input.setInputProcessor(lastInput);
	}

	@Override
	public void create()
	{
		font24.setColor(0, 0, 0, 1);
		if(Gdx.input.isKeyPressed(Keys.BACK))
		{
			exit();
			return;
		}
		act.googleServices.Countly(CountlyStrings.ScenePurchase);
		purchaseHelper = mSceneManager.act.purchaseHelper;

		change = new PurchaseSelectorHorizentalChange(mSceneManager.act, this, 0, 0, 5000, 300, (OrthographicCamera) changeAbleStage.getCamera());
		change.setATT(203, 196, 40, changeAbleStage);
		change.create();
		change.setCurrent(0);
		changeAbleStage.addActor(change);

		inputMultiplexer.addProcessor(HUD);

		Gdx.input.setInputProcessor(inputMultiplexer);

		change.run();
		changeAbleStage.getCamera().translate(-changeX, -changeY, 0);
		changeAbleStage.act();
		changeAbleStage.draw();

		setInput();
	}

	InputMultiplexer inputMultiplexer = new InputMultiplexer();


	@Override
	public void run()
	{
		super.run();

		change.run();
		changeAbleStage.getCamera().translate(-changeX, -changeY, 0);
		changeAbleStage.act();
		changeAbleStage.draw();

		if(purchaseHelper.isSthPurchased())
		{
			Log.e("Core PurchaseScene.java", "Something Purchased");
			doPurchase(purchaseHelper.getPurchasedID());
			purchaseHelper.givenPurchaseFlowDone();
		}

		HUD.draw();
		HUD.act();
	}

	public void setInput()
	{
		inputMultiplexer.addProcessor(new GestureDetector(new PurchaseChangeAbleGestureListenre(change, this)));
		inputMultiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	int[] coins = //age khasti taghir bedi purchaseSelectorHorzientalam coins[] ro taghir bede
			{
					6000, 15000, 25000, 45000, 80 * 1000, 130 * 1000, 180 * 1000, 250 * 1000, 450 * 1000, 900 * 1000, 2500 * 1000
			};

	public void doPurchase(int id)
	{
		act.googleServices.disableAds();
		act.addMoney(coins[id], true);
		act.gameStatData.adsDisable = true;
		act.saveGameStats();

		Log.e("Core PurchaseScene.java", "adding money : " + coins[id]);
	}

	int[] tapsellCoins =
			{
					0, 40000, 100000, 100000, 100000
			};

	private void doTapsell(int id)
	{
		Log.e("Tag", "Do tapsell, its empty!!!");
		if(id == -1)
			return;
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void draw()
	{
		super.draw();

		HUD.getBatch().begin();
		mSceneManager.drawGoldSprite(HUD.getBatch(), false);
		HUD.getBatch().end();
	}

	public void createBack()
	{
		Entity back = new Entity(TextureHelper.loadTexture(add + "back.png", disposeTextureArray));
		back.setSize(850, 500);
		float bX = back.getWidth() - SceneManager.WORLD_X;
		float bY = back.getHeight() - SceneManager.WORLD_Y;
		back.setPosition(DX - bX / 2, DY - bY / 2);

		attachChild(back);
	}
}
