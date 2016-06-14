package Scene.Garage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import DataStore.CarStatData;
import DataStore.DataKeyStrings;
import Entity.Button;
import Misc.TextureHelper;
import Scene.BaseScene;
import SceneManager.SceneManager;
import Sorter.CarSorter;
import Sorter.GunSlotSorter;
import WeaponBase.BaseGun;

public class GarageScene extends BaseScene
{
	public SceneManager mSceneManager;
	public GarageScene(SceneManager sceneManager, Viewport v){super(sceneManager.act, v);mSceneManager = sceneManager;}


	public float DX;
	public float DY;
	String add = "gfx/scene/garage/";

	int selectedCar;
	public CarSelectEntity [] carSelectEntities = new CarSelectEntity[SceneManager.CAR_NUM + 1];//1base

	public BaseGun [] gunSlots = new BaseGun[SceneManager.GUN_SLOT_NUM + 1];//1Base

	public Stage carUpgradeHUD;

	InputMultiplexer inputMultiplexer = new InputMultiplexer();

	public static float CAR_SHOW_WIDTH = 300;
	public static float CAR_SHOW_PADDING = 500;

	@Override
	public void loadResources()
	{
		loadButtonTextures();
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;

		carUpgradeHUD = new Stage(new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));

		for(int i = 1;i <= SceneManager.CAR_NUM;i++)
		{
			carSelectEntities[i] = new CarSelectEntity(this, (CarStatData) act.saveManager.loadDataValue(DataKeyStrings.CarStatData[CarSorter.carPos[i]], CarStatData.class), i);
			carSelectEntities[i].setPosition(i * CAR_SHOW_WIDTH + (i - 1) * CAR_SHOW_PADDING, 200);
		}

		loadGunSlots();

		((OrthographicCamera)getCamera()).zoom = 1;

		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(HUD);
		inputMultiplexer.addProcessor(carUpgradeHUD);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void create()
	{
		selectedCar = act.selectorStatData.selectedCar;
		((OrthographicCamera)getCamera()).position.x = getCameraDistX();
		carSelectEntities[selectedCar].select();

		createHUD();
	}

	@Override
	public void run() 
	{
		super.run();

		carUpgradeHUD.act();
	}

	@Override
	public void draw()
	{
		moveCamera();
		super.draw();

		for(int i = 1;i <= SceneManager.CAR_NUM;i++)
			carSelectEntities[i].draw(getBatch());
		carUpgradeHUD.draw();
		HUD.draw();
	}

	public Texture GunSlotButtonTexture1, GunSlotButtonTexture2;
	public Texture EngineUpgradeButtonTexture1, EngineUpgradeButtonTexture2;
	public Texture HitPointUpgradeButtonTexture1, HitPointUpgradeButtonTexture2;
	public void loadButtonTextures()
	{
		GunSlotButtonTexture1 = TextureHelper.loadTexture("gfx/car/slots/slot.png", disposeTextureArray);
		GunSlotButtonTexture2 = TextureHelper.loadTexture("gfx/car/slots/slot.png", disposeTextureArray);

		EngineUpgradeButtonTexture1 = TextureHelper.loadTexture(add + "engineupgrade1.png", disposeTextureArray);
		EngineUpgradeButtonTexture2 = TextureHelper.loadTexture(add + "engineupgrade2.png", disposeTextureArray);

		HitPointUpgradeButtonTexture1 = TextureHelper.loadTexture(add + "hitpointupgrade1.png", disposeTextureArray);
		HitPointUpgradeButtonTexture2 = TextureHelper.loadTexture(add + "hitpointupgrade2.png", disposeTextureArray);
	}

	@Override
	public void createHUD()
	{
		Button nextCarButton = new Button(TextureHelper.loadTexture(add + "nextbutton1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "nextbutton2.png", disposeTextureArray));
		nextCarButton.setSize(50, 250);
		nextCarButton.setPosition(DX + SceneManager.WORLD_X - 50 - 10, DY + 120);

		Button prevCarButton = new Button(TextureHelper.loadTexture(add + "prevbutton1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "prevbutton2.png", disposeTextureArray));
		prevCarButton.setSize(50, 250);
		prevCarButton.setPosition(DX + 10, DY + 120);

		nextCarButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				selectedCar++;
				if(selectedCar > SceneManager.CAR_NUM)
					selectedCar = SceneManager.CAR_NUM;

				carSelectEntities[selectedCar].select();
			}
		});

		prevCarButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				selectedCar--;
				if(selectedCar < 1)
					selectedCar = 1;

				carSelectEntities[selectedCar].select();
			}
		});

		Button startGameButton = new Button(TextureHelper.loadTexture(add + "next1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "next2.png", disposeTextureArray));
		startGameButton.setSize(150, 50);
		startGameButton.setPosition(DX + 300, 400);

		startGameButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				act.selectorStatData.selectedCar = selectedCar;
				act.saveBeforeGameScene();
				mSceneManager.setCurrentScene(SceneManager.SCENES.GAME_SCENE, carSelectEntities[selectedCar].sizakCarModel);
				dispose();
			}
		});

		HUD.addActor(nextCarButton);
		HUD.addActor(prevCarButton);
		HUD.addActor(startGameButton);
	}

	public float getCameraDistX()
	{
		return selectedCar * CAR_SHOW_WIDTH + (selectedCar - 1) * CAR_SHOW_PADDING + carSelectEntities[selectedCar].sizakCarModel.sprites.get(0).getWidth() / 2;
	}

	private static float CAMERA_SPEED = 10;
	private void moveCamera()
	{
		float distX = getCameraDistX();

		OrthographicCamera camera = (OrthographicCamera)getCamera();

		if(Math.abs(camera.position.x - distX) >= CAMERA_SPEED)
		{
			if(camera.position.x > distX)
				camera.position.x -= CAMERA_SPEED;

			if(camera.position.x < distX)
				camera.position.x += CAMERA_SPEED;
		}

	}


	private void loadGunSlots()
	{
		for(int i = 1;i <= SceneManager.GUN_SLOT_NUM;i++)
			gunSlots[i] = GunSlotSorter.getGunSlot(act, i);
	}
}
