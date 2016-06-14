package Dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import SceneManager.SceneManager;

/**
 * Created by sinazk on 6/10/16.
 * 02:49
 */
public class Dialog
{
	public DialogManager dialogManager;
	public Sprite backSprite;
	public Stage scene;

	boolean isActive;
	public int id;

	public Dialog(DialogManager dialogManager)
	{
		this.dialogManager = dialogManager;
		scene = new Stage(new ExtendViewport(SceneManager.WORLD_X, SceneManager.WORLD_Y));
	}

	public void loadResources()
	{

	}

	public void create()
	{

	}

	public void draw(Batch batch)
	{
		if(!isActive)
			return;

		scene.draw();
	}


	public void run()
	{
		scene.act();
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setIsActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public void attachChild(Actor a)
	{
		scene.addActor(a);
	}
}
