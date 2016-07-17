package Entity;

import com.badlogic.gdx.graphics.g2d.Batch;

import Misc.Log;
import Misc.TextureHelper;
import heshmat.MainActivity;

/**
 * Created by sinazk on 7/17/16.
 * 23:08
 */
public class VideoButton extends Button
{
	public VideoButton(MainActivity activity1)
	{
		super(TextureHelper.loadTexture("gfx/scene/v1.png", activity1.sceneManager.currentBaseScene.disposeTextureArray),
				TextureHelper.loadTexture("gfx/scene/v2.png", activity1.sceneManager.currentBaseScene.disposeTextureArray));

		this.act = activity1;

		setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				act.showVDO();
			}
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(act.gameStatData.adsDisable)
		{
			setVisible(false);

			return;
		}

		act.checkForVDO();

		setVisible(true);
		super.draw(batch, 1);
	}
}
