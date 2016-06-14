package Dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

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
	MainActivity activity;
	Stage dialogScene;

	ArrayList<Dialog> dialogs = new ArrayList<Dialog>();
	ArrayList<Texture> disposalTexture = new ArrayList<Texture>();

	private InputProcessor inputProcessorCPY;


	public DialogManager(MainActivity act)
	{
		this.activity = act;
		dialogScene = new Stage(new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));
		loadResources();
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
		inputProcessorCPY = Gdx.input.getInputProcessor();

		Gdx.input.setInputProcessor(gunSlotSelectorDialog.scene);
	}

	public void popQ()
	{
		dialogs.remove(dialogs.size() - 1);
		Gdx.input.setInputProcessor(inputProcessorCPY);
	}

	GunSlotSelectorDialog gunSlotSelectorDialog;
	private void loadDialogs()
	{
		gunSlotSelectorDialog = new GunSlotSelectorDialog(this);
	}

	Texture backGroundTexture;
	private void loadTextures()
	{
		backGroundTexture = TextureHelper.loadTexture(add + "back.png", disposalTexture);
	}

	public void dispose()
	{
		for(int i = 0;i < disposalTexture.size();i++)
			disposalTexture.get(i).dispose();
	}
}
