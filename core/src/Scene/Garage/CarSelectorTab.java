package Scene.Garage;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import DataStore.CarStatData;
import DataStore.DataKeyStrings;
import Entity.Button;
import HUD.HUD;
import Misc.Log;
import Misc.TextureHelper;
import Scene.BaseScene;
import SceneManager.SceneManager;
import Sorter.CarSorter;
import Sorter.GunSlotSorter;
import WeaponBase.BaseGun;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/14/16.
 * 05:00
 */
public class CarSelectorTab extends BaseScene
{
	public static float CAR_SHOW_WIDTH = 300;
	public static float CAR_SHOW_PADDING = 500;

	GarageScene garageScene;
	SceneManager mSceneManager;
	public Stage carUpgradeHUD;

	int selectedCar;
	public CarSelectEntity [] carSelectEntities = new CarSelectEntity[SceneManager.CAR_NUM + 1];//1base
	public BaseGun[] gunSlots = new BaseGun[SceneManager.GUN_SLOT_NUM + 1];//1Base
	InputMultiplexer inputMultiplexer = new InputMultiplexer();

	float DX, DY;
	String add;
	public CarSelectorTab(GarageScene garageScene)
	{
		super(garageScene.act, garageScene.getViewport());

		DX = garageScene.DX;
		DY = garageScene.DY;
		add = garageScene.add;

		this.garageScene = garageScene;
		this.mSceneManager = garageScene.mSceneManager;
	}

	@Override
	public void loadResources()
	{
		super.loadResources();

		carUpgradeHUD = new Stage(new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));

		for(int i = 1;i <= SceneManager.CAR_NUM;i++)
		{
			carSelectEntities[i] = new CarSelectEntity(this,
					(CarStatData) act.saveManager.loadDataValue(DataKeyStrings.CarStatData[CarSorter.carPos[i]], CarStatData.class), i);
			carSelectEntities[i].setPosition(i * CAR_SHOW_WIDTH + (i - 1) * CAR_SHOW_PADDING, 200);

			for(int j = 0;j < carSelectEntities[i].sizakCarModel.slots.size();j++)
				Log.e("CarSelectorTab.java", "sz = " + carSelectEntities[i].sizakCarModel.slots.get(j).availableGunSlots.size());
		}

		loadGunSlots();
		createHUD();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(HUD);
		inputMultiplexer.addProcessor(carUpgradeHUD);

		create();
	}

	@Override
	public void create()
	{
		super.create();
		selectedCar = act.selectorStatData.selectedCar;
		((OrthographicCamera)getCamera()).position.x = getCameraDistX();
		carSelectEntities[selectedCar].select();
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

	@Override
	public void run()
	{

//		Log.e("CarSelectorTab.java", "camPos : " + carUpgradeHUD.getCamera().position);

		carUpgradeHUD.act();
		HUD.act();
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

	public float getCameraDistX()
	{
		return selectedCar * CAR_SHOW_WIDTH + (selectedCar - 1) * CAR_SHOW_PADDING + carSelectEntities[selectedCar].sizakCarModel.sprites.get(0).getWidth() / 2;
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

		HUD.addActor(nextCarButton);
		HUD.addActor(prevCarButton);
	}

	private void loadGunSlots()
	{
		for(int i = 1;i <= SceneManager.GUN_SLOT_NUM;i++)
			gunSlots[i] = GunSlotSorter.getGunSlot(act, i);
	}
}
