package Entity;

import com.badlogic.gdx.Gdx;
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
		counter = 0;

		setRunnable(act, new Runnable()
		{
			@Override
			public void run()
			{
				act.showVDO();
			}
		});
	}

	public static float REQUEST_TIME = 5f;
	float counter = 0;
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if(act.gameStatData.adsDisable)
		{
			setVisible(false);

			return;
		}

		counter += Gdx.graphics.getDeltaTime();
		if(counter >= REQUEST_TIME)
		{
			counter = 0;

			act.checkForVDO();
		}

		setVisible(true);
		super.draw(batch, 1);
	}
}
