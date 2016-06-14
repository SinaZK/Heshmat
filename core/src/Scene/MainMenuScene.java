package Scene;

import com.badlogic.gdx.utils.viewport.Viewport;

import Entity.Button;
import Misc.TextureHelper;
import SceneManager.SceneManager;

public class MainMenuScene extends BaseScene 
{
	SceneManager mSceneManager;
	public MainMenuScene(SceneManager sceneManager, Viewport v){super(sceneManager.act, v);mSceneManager = sceneManager;}


	public float DX;
	public float DY;
	String add = "gfx/scene/main/";

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;

		Button nextButton = new Button(TextureHelper.loadTexture(add+"next1.png", disposeTextureArray), TextureHelper.loadTexture(add+"next2.png", disposeTextureArray));
		nextButton.setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				mSceneManager.setCurrentScene(SceneManager.SCENES.LEVEL_PACKAGE_SELECTOR, null);
				dispose();
			}
		});

		nextButton.setSize(50, 50);
		nextButton.setPosition(DX + 10, DX + 50);

		attachChild(nextButton);
	}

	@Override
	public void create()
	{
	}
	
	@Override
	public void run() 
	{
		super.run();
	}

	@Override
	public void dispose() 
	{
		super.dispose();
	}
}
