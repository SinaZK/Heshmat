package Scene;

import com.badlogic.gdx.utils.viewport.Viewport;

import Entity.Button;
import Entity.Entity;
import Misc.Log;
import Misc.TextureHelper;
import SceneManager.SceneManager;
import sun.net.www.content.image.png;

public class LevelPackageScene extends BaseScene
{
	SceneManager mSceneManager;
	public LevelPackageScene(SceneManager sceneManager, Viewport v){super(sceneManager.act, v);mSceneManager = sceneManager;}


	public float DX;
	public float DY;
	String add = "gfx/scene/lvlpackage/";

	Button [] packButtons;

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;

		createBack();

		packButtons = new Button[SceneManager.LVL_PACK_NUM + 1];

		for(int i = 1;i <= SceneManager.LVL_PACK_NUM;i++)
		{
			packButtons[i] = new Button(TextureHelper.loadTexture(add + "pack" + i + "1.png", disposeTextureArray),
					TextureHelper.loadTexture(add + "pack" + i + "2.png", disposeTextureArray));
			final int II = i;

			if(i == 1)
			{
				packButtons[i].setRunnable(act, new Runnable()
				{
					@Override
					public void run()
					{
						act.selectorStatData.selectedLevelPack = II;
						mSceneManager.setCurrentScene(SceneManager.SCENES.LEVEL_SELECTOR, null);
						dispose();
					}
				});
			}

			int width = 340;
			int height = 235;
			int paddingX = 70;
			int paddingY = 30;
			int firstX = (SceneManager.WORLD_X - SceneManager.LVL_PACK_NUM * width - paddingX * (SceneManager.LVL_PACK_NUM - 1)) / 2;
//			packButtons[i].setSize(width, height);
			packButtons[i].setPosition(DX + firstX + width * (i - 1) + paddingX * (i - 1), DY + (SceneManager.WORLD_Y - height) / 2 + paddingY * (i - 1));

//			Log.e("LevelPackageScene.java", "pos = " + packButtons[i].getX() + ", " + packButtons[i].getY());

			attachChild(packButtons[i]);
		}
	}

	@Override
	public void create()
	{
		super.create();

		addBackToMenuButton();
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

	public void addBackToMenuButton()
	{
		Button menuButton = new Button(TextureHelper.loadTexture(add+"menu1.png", disposeTextureArray),
				TextureHelper.loadTexture(add+"menu2.png", disposeTextureArray));
		menuButton.setPosition(DX + 28, DY + 28);
		menuButton.setRunnable(act, new Runnable()
		{

			@Override
			public void run()
			{
				dispose();
				mSceneManager.setCurrentScene(SceneManager.SCENES.MAIN_MENU, null);
			}
		});

		attachChild(menuButton);
	}
}
