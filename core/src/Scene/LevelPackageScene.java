package Scene;

import com.badlogic.gdx.utils.viewport.Viewport;

import Entity.Button;
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

		packButtons = new Button[SceneManager.LVL_PACK_NUM + 1];

		for(int i = 1;i <= SceneManager.LVL_PACK_NUM;i++)
		{
			packButtons[i] = new Button(TextureHelper.loadTexture(add + "pack" + i + "1.png", disposeTextureArray),
					TextureHelper.loadTexture(add + "pack" + i + "2.png", disposeTextureArray));
			final int II = i;

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

			int width = 300;
			int height = 400;
			int padding = 50;
			int firstX = (SceneManager.WORLD_X - SceneManager.LVL_PACK_NUM * width - padding * (SceneManager.LVL_PACK_NUM - 1)) / 2;
			packButtons[i].setSize(width, height);
			packButtons[i].setPosition(DX + firstX + width * (i - 1) + padding * (i - 1), DY + (SceneManager.WORLD_Y - height) / 2);

			Log.e("LevelPackageScene.java", "pos = " + packButtons[i].getX() + ", " + packButtons[i].getY());

			attachChild(packButtons[i]);
		}
	}

}
