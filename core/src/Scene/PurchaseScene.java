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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import ChangeAblePackage.PurchaseChangeAbleGestureListenre;
import ChangeAblePackage.PurchaseSelectorHorizentalChange;
import Countly.CountlyStrings;
import Entity.Button;
import Entity.Entity;
import HUD.HUD;
import Misc.Log;
import Misc.TextureHelper;
import PurchaseIAB.purchaseIAB;
import SceneManager.SceneManager;

public class PurchaseScene extends BaseScene
{

	public SceneManager mSceneManager;
	public PurchaseScene(SceneManager sceneManager, Viewport v){super(sceneManager.act, v); mSceneManager = sceneManager;}

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

		final Button exit = new Button(TextureHelper.loadTexture(add+"back1.png", disposeTextureArray),
				TextureHelper.loadTexture(add+"back2.png", disposeTextureArray));
		
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

		Entity back = new Entity(TextureHelper.loadTexture(add+"back.png", disposeTextureArray))
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
		if(!purchaseHelper.isReadyForPurchase())
		{
			Log.e("Tag", "NOT READY! INITING");
			purchaseHelper.init();
		}
		//		purchaseHelper.init();

		
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
		
		if(act.googleServices.tapSellIsSthPurchased())
		{
			doTapsell(act.googleServices.tapSellGetPurchasedID());
			act.googleServices.tapSellGivenPurchaseFlowDone();
		}
		
		change.run();
		changeAbleStage.getCamera().translate(-changeX, -changeY, 0);
		changeAbleStage.act();
		changeAbleStage.draw();
		
		if(!isPricesLoaded && purchaseHelper.isReadyForPurchase())
		{
			prices = purchaseHelper.getPrices();

			for(int i = 0;i < prices.size();i++)
			{
				Log.e("Tag", "Prices = " + prices.get(i));
			}
			
			isPricesLoaded = true;
			setInput();
		}

		if(purchaseHelper.isSthPurchased())
		{
			Log.e("Core PurchaseScene.java", "Something Purchased");
			doPurchase(purchaseHelper.getPurchasedID());
			purchaseHelper.givenPurchaseFlowDone();
		}
		else
		{
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
	
	int [] coins = //age khasti taghir bedi purchaseSelectorHorzientalam coins[] ro taghir bede
		{
		//"100,000", "225,000", "600,000", "910,000", "1,400,000", "1,800,000", "2,400,000", "3,400,000", "4,500,000", "5,700,000"
			100000, 225000, 600000, 910000, 1400000, 1800000, 2400000, 3400000, 4500000, 5700000
		};
	public void doPurchase(int id)
	{
		act.googleServices.disableAds();
		act.addMoney(coins[id], true);
		act.gameStatData.adsDisable = true;
		act.saveGameStats();

		Log.e("Core PurchaseScene.java", "adding money : " + coins[id]);
	}
	
	int [] tapsellCoins = 
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
		mSceneManager.drawGoldSprite(HUD.getBatch());
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
