package Scene.Garage;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import Entity.Button;
import Misc.Log;
import Misc.TextureHelper;
import Scene.BaseScene;
import SceneManager.SceneManager;
import Sorter.GunSorter;
import WeaponBase.BaseGun;
import WeaponBase.GunModel;

/**
 * Created by sinazk on 6/14/16.
 * 5:38
 */
public class GunSelectorTab extends BaseScene
{

	public static float GUN_SHOW_WIDTH  = 300;
	public static float GUN_SHOW_HEIGHT = 300;
	public static float GUN_SHOW_PADDING = 500;

	public GarageScene garageScene;
	SceneManager mSceneManager;
	public Stage gunUpgradeHUD;

	public GunSelectEntity[] gunSelectEntities = new GunSelectEntity[SceneManager.GUN_NUM + 1];//1base

	public InputMultiplexer inputMultiplexer = new InputMultiplexer();

	int selectedGun;

	public float DX, DY;
	String add;
	public GunSelectorTab(GarageScene garageScene)
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

		gunUpgradeHUD = new Stage(new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));

		for(int i = 1;i <= SceneManager.GUN_NUM;i++)
		{
			gunSelectEntities[i] = new GunSelectEntity(this, act.gunStatDatas[GunSorter.gunPos[i]], i);
			gunSelectEntities[i].setPosition(i * GUN_SHOW_WIDTH + (i - 1) * GUN_SHOW_PADDING, 100);

			gunSelectEntities[i].setPosition(i * GUN_SHOW_WIDTH + (i - 1) * GUN_SHOW_PADDING, 100);
		}

		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(HUD);
		inputMultiplexer.addProcessor(gunUpgradeHUD);
		createHUD();
	}

	@Override
	public void create()
	{
		super.create();

		selectedGun = act.selectorStatData.selectedGun;
		if(gunSelectEntities[selectedGun] == null)
		{
			Log.e("GunSelectorTab.java", "NULL selected = " + selectedGun);
		}
		gunSelectEntities[selectedGun].select();
	}

	@Override
	public void draw()
	{
		moveCamera();
		super.draw();

		for(int i = 1;i <= SceneManager.GUN_NUM;i++)
			gunSelectEntities[i].draw(getBatch());

		HUD.draw();
		gunUpgradeHUD.draw();
	}

	@Override
	public void run()
	{
		super.run();

		HUD.act();
		gunUpgradeHUD.act();
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
		return selectedGun * GUN_SHOW_WIDTH + (selectedGun - 1) * GUN_SHOW_PADDING + gunSelectEntities[selectedGun].gunModel.showSprite.getWidth() / 2;
	}

	@Override
	public void createHUD()
	{
		Button nextCarButton = new Button(TextureHelper.loadTexture(add + "nextbutton1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "nextbutton2.png", disposeTextureArray));
		nextCarButton.setPosition(DX + SceneManager.WORLD_X - 190, DY + 120);

		Button prevCarButton = new Button(TextureHelper.loadTexture(add + "prevbutton1.png", disposeTextureArray),
				TextureHelper.loadTexture(add + "prevbutton2.png", disposeTextureArray));
		prevCarButton.setPosition(DX + 100, DY + 120);

		nextCarButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				selectedGun++;
				if(selectedGun > SceneManager.GUN_NUM)
					selectedGun = SceneManager.GUN_NUM;

				gunSelectEntities[selectedGun].select();
			}
		});

		prevCarButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				selectedGun--;
				if(selectedGun < 1)
					selectedGun = 1;

				gunSelectEntities[selectedGun].select();
			}
		});

		HUD.addActor(nextCarButton);
		HUD.addActor(prevCarButton);
	}
}
