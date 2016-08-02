package Scene.Garage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import Entity.Button;
import Entity.Entity;
import Enums.Enums;
import Misc.TextureHelper;
import Scene.BaseScene;
import Scene.MainMenuScene;
import Scene.PurchaseScene;
import SceneManager.SceneManager;
import Sorter.CarSorter;

public class GarageScene extends BaseScene
{
	public SceneManager mSceneManager;
	public GarageScene(SceneManager sceneManager, Viewport v){super(sceneManager.act, v);mSceneManager = sceneManager;}


	public float DX;
	public float DY;
	public String add = "gfx/scene/garage/";

	public CarSelectorTab carSelectorTab;
	public GunSelectorTab gunSelectorTab;

	public enum CurrentTab
	{
		CAR_SELECT, GUN_SELECT;
	}

	CurrentTab currentTab;

	InputMultiplexer inputMultiplexer = new InputMultiplexer();

	@Override
	public void loadResources()
	{
		loadCarButtonTextures();
		loadGunButtonTextures();
		loadUpgradeTextures();

		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;

		((OrthographicCamera)getCamera()).zoom = 1;

		carSelectorTab = new CarSelectorTab(this);
		carSelectorTab.loadResources();
		carSelectorTab.create();
		gunSelectorTab = new GunSelectorTab(this);
		gunSelectorTab.loadResources();
		gunSelectorTab.create();

		createBack();
		addBackToMenuButton();
		createHUD();

		act.enableAds();
	}

	@Override
	public void create()
	{
		selectTab(CurrentTab.CAR_SELECT);
	}

	@Override
	public void run() 
	{
		super.run();

		switch (currentTab)
		{
			case CAR_SELECT:
				carSelectorTab.run();
				break;

			case GUN_SELECT:
				gunSelectorTab.run();
				break;
		}

		HUD.act();

		Random r = new Random();
		int a = Math.abs(r.nextInt()) % 1000;

//		if(a < 50)
//			Log.e("GarageScene.java", "money = " + act.playerStatData.getMoney());

	}

	@Override
	public void draw()
	{
		super.draw();

		switch (currentTab)
		{
			case CAR_SELECT:
				carSelectorTab.draw();
				break;

			case GUN_SELECT:
				gunSelectorTab.draw();
				break;
		}

		HUD.draw();
		HUD.getBatch().begin();
		mSceneManager.drawGoldSprite(HUD.getBatch());
		HUD.getBatch().end();
	}

	float swipeVX = 5;
	public void selectTab(CurrentTab currentTab)
	{
		this.currentTab = currentTab;
		inputMultiplexer.clear();

		switch (currentTab)
		{
			case CAR_SELECT:
				inputMultiplexer.addProcessor(this);
				inputMultiplexer.addProcessor(HUD);
				inputMultiplexer.addProcessor(carSelectorTab.inputMultiplexer);
				carSelectorTab.getCamera().position.x = carSelectorTab.getCameraDistX();
				GestureDetector.GestureListener gestureListener = new GestureDetector.GestureListener()
				{
					@Override
					public boolean touchDown(float x, float y, int pointer, int button)
					{
						return false;
					}

					@Override
					public boolean tap(float x, float y, int count, int button)
					{
						return false;
					}

					@Override
					public boolean longPress(float x, float y)
					{
						return false;
					}

					@Override
					public boolean fling(float velocityX, float velocityY, int button)
					{
						if(velocityX < -swipeVX)
							carSelectorTab.next();

						if(velocityX > swipeVX)
							carSelectorTab.prev();
						return false;
					}

					@Override
					public boolean pan(float x, float y, float deltaX, float deltaY)
					{
						return false;
					}

					@Override
					public boolean panStop(float x, float y, int pointer, int button)
					{
						return false;
					}

					@Override
					public boolean zoom(float initialDistance, float distance)
					{
						return false;
					}

					@Override
					public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
					{
						return false;
					}
				};

				inputMultiplexer.addProcessor(new GestureDetector(gestureListener));
				break;

			case GUN_SELECT:
				inputMultiplexer.addProcessor(this);
				inputMultiplexer.addProcessor(HUD);
				inputMultiplexer.addProcessor(gunSelectorTab.inputMultiplexer);
				gunSelectorTab.getCamera().position.x = gunSelectorTab.getCameraDistX();
				gestureListener = new GestureDetector.GestureListener()
				{
					@Override
					public boolean touchDown(float x, float y, int pointer, int button)
					{
						return false;
					}

					@Override
					public boolean tap(float x, float y, int count, int button)
					{
						return false;
					}

					@Override
					public boolean longPress(float x, float y)
					{
						return false;
					}

					@Override
					public boolean fling(float velocityX, float velocityY, int button)
					{
						if(velocityX < -swipeVX)
							gunSelectorTab.next();

						if(velocityX > swipeVX)
							gunSelectorTab.prev();
						return false;
					}

					@Override
					public boolean pan(float x, float y, float deltaX, float deltaY)
					{
						return false;
					}

					@Override
					public boolean panStop(float x, float y, int pointer, int button)
					{
						return false;
					}

					@Override
					public boolean zoom(float initialDistance, float distance)
					{
						return false;
					}

					@Override
					public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
					{
						return false;
					}
				};

				inputMultiplexer.addProcessor(new GestureDetector(gestureListener));
				break;
		}

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	public Texture GunSlotButtonTexture1, GunSlotButtonTexture2;
	public Texture EngineUpgradeButtonTexture1, EngineUpgradeButtonTexture2;
	public Texture HitPointUpgradeButtonTexture1, HitPointUpgradeButtonTexture2;
	public Texture BuyButtonTexture1, BuyButtonTexture2;
	public void loadCarButtonTextures()//CarSelectorTab Textures
	{
		String add = this.add + "cartab/";

		GunSlotButtonTexture1 = TextureHelper.loadTexture("gfx/car/slots/slot.png", disposeTextureArray);
		GunSlotButtonTexture2 = TextureHelper.loadTexture("gfx/car/slots/slot.png", disposeTextureArray);

		EngineUpgradeButtonTexture1 = TextureHelper.loadTexture(add + "engineupgrade1.png", disposeTextureArray);
		EngineUpgradeButtonTexture2 = TextureHelper.loadTexture(add + "engineupgrade2.png", disposeTextureArray);

		HitPointUpgradeButtonTexture1 = TextureHelper.loadTexture(add + "hitpointupgrade1.png", disposeTextureArray);
		HitPointUpgradeButtonTexture2 = TextureHelper.loadTexture(add + "hitpointupgrade2.png", disposeTextureArray);

		BuyButtonTexture1 = TextureHelper.loadTexture(this.add + "buy1.png", disposeTextureArray);
		BuyButtonTexture2 = TextureHelper.loadTexture(this.add + "buy2.png", disposeTextureArray);
	}


	public Texture FireRateUpgradeButtonTexture1, FireRateUpgradeButtonTexture2;
	public void loadGunButtonTextures()//GunSelectorTab Textures
	{
		String add = this.add + "guntab/";
		FireRateUpgradeButtonTexture1 = TextureHelper.loadTexture(add + "firerateupgrade1.png", disposeTextureArray);
		FireRateUpgradeButtonTexture2 = TextureHelper.loadTexture(add + "firerateupgrade2.png", disposeTextureArray);
	}

	Texture gunTabTexture1, gunTabTexture2;
	Texture carTabTexture1, carTabTexture2;

	Button gunSelectButton, carSelectButton;
	@Override
	public void createHUD()
	{
		String add = this.add + "garagehud/";
		gunTabTexture1 = TextureHelper.loadTexture(add + "gunselect1.png", disposeTextureArray);
		gunTabTexture2 = TextureHelper.loadTexture(add + "gunselect2.png", disposeTextureArray);

		carTabTexture1 = TextureHelper.loadTexture(add + "carselect1.png", disposeTextureArray);
		carTabTexture2 = TextureHelper.loadTexture(add + "carselect2.png", disposeTextureArray);

		gunSelectButton = new Button(gunTabTexture1, gunTabTexture2);
		gunSelectButton.setPosition(DX + 15, DY + 254);

		carSelectButton = new Button(carTabTexture1, carTabTexture2);
		carSelectButton.setPosition(DX + 15, DY + 330);

		carSelectButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				gunSelectButton.isClicked = false;
				carSelectButton.isClicked = true;
				selectTab(CurrentTab.CAR_SELECT);
			}
		});

		gunSelectButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				gunSelectButton.isClicked = true;
				carSelectButton.isClicked = false;
				selectTab(CurrentTab.GUN_SELECT);
			}
		});

		Button startGameButton = new Button(TextureHelper.loadTexture(add + "start1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "start2.png", disposeTextureArray));
		startGameButton.setPosition(DX + 703, DY + 25);

		startGameButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				if(act.carStatDatas[CarSorter.carPos[carSelectorTab.selectedCar]].lockStat == Enums.LOCKSTAT.UNLOCK)
					act.selectorStatData.selectedCar = carSelectorTab.selectedCar;

				act.saveBeforeGameScene();
				mSceneManager.setCurrentScene(SceneManager.SCENES.GAME_SCENE, carSelectorTab.carSelectEntities[carSelectorTab.selectedCar].sizakCarModel);
				dispose();
			}
		});

		HUD.addActor(gunSelectButton);
		HUD.addActor(carSelectButton);
		HUD.addActor(startGameButton);
	}

	public void createBack()
	{
		Entity back = new Entity(TextureHelper.loadTexture(add + "back.png", disposeTextureArray));
		float bX = back.getWidth() - SceneManager.WORLD_X;
		float bY = back.getHeight() - SceneManager.WORLD_Y;
		back.setPosition(DX - bX / 2 + 20, DY - bY / 2);

		HUD.addActor(back);
	}

	public void addBackToMenuButton()
	{
		Button menuButton = new Button(TextureHelper.loadTexture(add + "menu1.png", disposeTextureArray),
				TextureHelper.loadTexture(add+"menu2.png", disposeTextureArray));
		menuButton.setPosition(DX + 5, DY + 28);
		menuButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
				dispose();
				mSceneManager.setCurrentScene(SceneManager.SCENES.MAIN_MENU, null);
			}
		});

		HUD.addActor(menuButton);

		Button backButton = new Button(TextureHelper.loadTexture(add + "back1.png", disposeTextureArray),
				TextureHelper.loadTexture(add+"back2.png", disposeTextureArray));
		backButton.setPosition(DX + 60, DY + 28);
		backButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
				dispose();
				mSceneManager.setCurrentScene(SceneManager.SCENES.LEVEL_SELECTOR, null);
			}
		});

		HUD.addActor(backButton);

		addPurchaseButtonToHUD();
	}

	public Sprite [] starSprites = new Sprite[6];
	public Sprite goldBackSprite;
	public Texture plusUpgrade1, plusUpgrade2;
	public void loadUpgradeTextures()
	{
		for(int i = 0;i <= 5;i++)
			starSprites[i] = new Sprite(TextureHelper.loadTexture(add + "star" + i + ".png", disposeTextureArray));

		goldBackSprite = new Sprite(TextureHelper.loadTexture(add + "gold.png", disposeTextureArray));

		plusUpgrade1 = TextureHelper.loadTexture(add + "add0.png", disposeTextureArray);
		plusUpgrade2 = TextureHelper.loadTexture(add + "add1.png", disposeTextureArray);
	}

	public void addPurchaseButtonToHUD()
	{
		Button purchaseButton = new Button(TextureHelper.loadTexture(add + "purchase1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "purchase2.png", disposeTextureArray));

		purchaseButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				InputProcessor inputProcessor = Gdx.input.getInputProcessor();
				mSceneManager.setCurrentScene(SceneManager.SCENES.PURCHASE_SCENE, null);

				PurchaseScene scene = (PurchaseScene) mSceneManager.currentBaseScene;
				scene.lastScene = GarageScene.this;
				scene.lastInput = inputProcessor;
			}
		});

		purchaseButton.setPosition(DX + 730, DY + 348);

		HUD.addActor(purchaseButton);
	}
}
