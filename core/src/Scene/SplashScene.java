package Scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.Viewport;

import Entity.Entity;
import Misc.Log;
import Misc.TextureHelper;
import SceneManager.SceneManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy;

public class SplashScene extends BaseScene
{
	SceneManager mSceneManager;

	public SplashScene(SceneManager pSceneManager, Viewport v){super(pSceneManager.act, v); mSceneManager = pSceneManager;}

	float DX;
	float DY;

	Entity logo1, logo2, back, sign;

	@Override
	public void loadResources()
	{
		DX = (getCamera().viewportWidth - SceneManager.WORLD_X) / 2;
		DY = (getCamera().viewportHeight - SceneManager.WORLD_Y) / 2;
		String add = "gfx/scene/splash/";

		logo1  = new Entity(TextureHelper.loadTexture(add + "logo1.png", disposeTextureArray));
		logo2  = new Entity(TextureHelper.loadTexture(add+"logo2.png", disposeTextureArray));
		logo1.setSize(127, 119);
		logo2.setSize(171, 171);
		back   = new Entity(TextureHelper.loadTexture(add+"back.jpg", disposeTextureArray));
		float bX = back.getWidth() - SceneManager.WORLD_X;
		float bY = back.getHeight() - SceneManager.WORLD_Y;
		back.setPosition(DX - bX / 2, DY - bY / 2);
		sign = new Entity(TextureHelper.loadTexture(add+"sign.png", disposeTextureArray));
		sign.setSize(172, 67);

		logo1.setOrigin(logo1.getWidth() / 2, logo1.getHeight() / 2);
		logo2.setOrigin(logo2.getWidth() / 2, logo2.getHeight() / 2);
		sign.setPosition(DX + 310, DY + 115);
		logo2.setPosition(DX + 310, DY + 192);
		logo1.setPosition(DX + 335, DY + 218);


		attachChild(back);
		attachChild(logo1);
		attachChild(logo2);
		attachChild(sign);

		logo1.setRotation(180);
		logo2.setRotation(180);
		logo1.addAction(parallel(rotateBy(-180, 1f)));
		logo2.addAction(parallel(rotateBy(+180, 1f)));

		sign.setColor(sign.getColor().r, sign.getColor().g, sign.getColor().b, 0);

		Timer.schedule(new Task() {

			@Override
			public void run()
			{
				sign.addAction(parallel(alpha(1, 1)));
			}
		}, 1, 1, 0);

	}


	float STOP_TIME = 1f;
	float FADE_TIME = 1.5f;
	@Override
	public void create()
	{
		Timer.schedule(new Task() {

			@Override
			public void run()
			{
				Color black = new Color();
				black.r = 0;
				black.g = 0;
				black.b = 0;

				logo1.addAction(parallel(color(black, FADE_TIME)));
				logo2.addAction(parallel(color(black, FADE_TIME)));
				sign.addAction(parallel(color(black, FADE_TIME)));
				back.addAction(parallel(color(black, FADE_TIME)));
			}
		}, 1.8f + STOP_TIME, 1, 0);

		Timer.schedule(new Task() {

			@Override
			public void run()
			{
				dispose();
				mSceneManager.setCurrentScene(SceneManager.SCENES.MAIN_MENU, null);
			}
		}, 1.8f + STOP_TIME + FADE_TIME, 1, 0);

		super.create();
	}

}