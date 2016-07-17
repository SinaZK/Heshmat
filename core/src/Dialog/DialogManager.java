package Dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import GameScene.GameScene;
import Misc.TextureHelper;
import Scene.Garage.GarageScene;
import SceneManager.SceneManager;
import heshmat.MainActivity;

/**
 * Created by sinazk on 6/10/16.
 * 02:50
 */
public class DialogManager
{
	public MainActivity activity;
	Stage dialogScene;

	ArrayList<Dialog> dialogs = new ArrayList<Dialog>();
	ArrayList<Texture> disposalTexture = new ArrayList<Texture>();

	public DialogManager(MainActivity act)
	{
		this.activity = act;
		dialogScene = new Stage(new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));
		loadResources();

		int a = 10;
	}

	String add = "gfx/scene/dialog/";
	public void loadResources()
	{
		loadTextures();
		loadDialogs();
	}

	public void draw()
	{
		dialogScene.getBatch().begin();

		for(int i = 0;i < dialogs.size();i++)
			dialogs.get(i).draw(dialogScene.getBatch());

		dialogScene.getBatch().end();
	}

	public void run()
	{
		for(int i = 0;i < dialogs.size();i++)
			dialogs.get(i).run();
	}

	public void addGunSlotSelectorDialog(GarageScene garageScene, int carID, int slotID, ArrayList<String> arrayList)
	{
		gunSlotSelectorDialog.create(garageScene, carID, slotID, arrayList);
		gunSlotSelectorDialog.setIsActive(true);
		dialogs.add(gunSlotSelectorDialog);
		gunSlotSelectorDialog.inputProcessorCPY = Gdx.input.getInputProcessor();

		Gdx.input.setInputProcessor(gunSlotSelectorDialog.scene);
	}

	public void addBuyDialog(float DX, float DY, long price)
	{
		buyDialog.create(DX, DY, price);
		buyDialog.setIsActive(true);
		dialogs.add(buyDialog);
		buyDialog.inputProcessorCPY = Gdx.input.getInputProcessor();

		Gdx.input.setInputProcessor(buyDialog.scene);
	}

	public void addPauseDialog()
	{
		pauseMenuDialog.setIsActive(true);
		dialogs.add(pauseMenuDialog);

		pauseMenuDialog.inputProcessorCPY = Gdx.input.getInputProcessor();

		Gdx.input.setInputProcessor(pauseMenuDialog.scene);
	}

	public void addInfoCardDialog(GameScene gameScene, Sprite infoCardSprite)
	{
		gameScene.pause(false);
		infoCarDialog.setIsActive(true);
		infoCarDialog.setSprite(infoCardSprite);
		dialogs.add(infoCarDialog);

		infoCarDialog.inputProcessorCPY = Gdx.input.getInputProcessor();

		Gdx.input.setInputProcessor(infoCarDialog.scene);
	}

	public void popQ()
	{
		Gdx.input.setInputProcessor(dialogs.get(dialogs.size() - 1).inputProcessorCPY);
		dialogs.remove(dialogs.size() - 1);
	}

	GunSlotSelectorDialog gunSlotSelectorDialog;
	public BuyDialog buyDialog;
	public PauseMenuDialog pauseMenuDialog;
	public InfoCarDialog infoCarDialog;
	private void loadDialogs()
	{
		gunSlotSelectorDialog = new GunSlotSelectorDialog(this);
		buyDialog = new BuyDialog(this);
	}

	public void loadGameSceneDialogs(GameScene gameScene)
	{
		loadPauseMenu(gameScene);
		loadInfoCardDialog(gameScene);
	}

	private void loadPauseMenu(GameScene gameScene)
	{
		pauseMenuDialog = new PauseMenuDialog(this, gameScene);
		pauseMenuDialog.create();
	}

	private void loadInfoCardDialog(GameScene gameScene)
	{
		infoCarDialog = new InfoCarDialog(this, gameScene);
		infoCarDialog.create();
	}

	Texture backGroundTexture;

	public Texture canBuybackTexture, cantBuyBackTexture;
	private void loadTextures()
	{
		backGroundTexture = TextureHelper.loadTexture(add + "back.png", disposalTexture);

		canBuybackTexture = TextureHelper.loadTexture(add + "/buy/can.png", disposalTexture);
		cantBuyBackTexture = TextureHelper.loadTexture(add + "/buy/cant.png", disposalTexture);
	}

	public void dispose()
	{
		for(int i = 0;i < disposalTexture.size();i++)
			disposalTexture.get(i).dispose();
	}
}
